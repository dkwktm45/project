package com.example.project_2th.controller;

import com.example.project_2th.entity.Exercies;
import com.example.project_2th.entity.ExerciesVideo;
import com.example.project_2th.response.PoseResponse;
import com.example.project_2th.service.ExerciesService;
import com.example.project_2th.service.ExerciesVideoService;
import com.example.project_2th.service.PostruesService;
import com.example.project_2th.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
public class RestControllerApi {

    @Autowired
    private final ExerciesService exerciesService;

    @Autowired
    private final ExerciesVideoService exerciesVideoService;

    @Autowired
    private final PostruesService postruesService;

    @Autowired
    private final UserService userService;

    private final Logger logger = LoggerFactory.getLogger(RestControllerApi.class);


    @PostMapping(value = "/user/exercies-info")
    public String insertExURL(HttpServletRequest request) throws Exception {
        logger.info("insertExURL perfom");

        Exercies exercies = (Exercies) request.getSession().getAttribute("exinfo");
        exerciesVideoService.videoSave(request.getParameter("cnt")
                , exercies,
                request.getInputStream());

        logger.info("[insertExURL] end");
        return "user/main";
    }

    @PostMapping(value = "/user/pose")
    public ResponseEntity<List<PoseResponse>> getVideoinfo(@RequestBody ExerciesVideo exerciesVideo){
        logger.info("insertPose perfom ");

        if (exerciesVideo == null){
            logger.error("videoSeq : null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        List<PoseResponse> response = postruesService.selectVideoInfo(exerciesVideo);

        logger.info("[response] postures : {}"
                ,response);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping(value = "/user/pose-bad")
    public void insertBadImage(HttpServletRequest requestuest) throws Exception {
        logger.info("insertBadImage perfom");


        postruesService.badeImage(requestuest.getParameter("ai_comment")
                , Long.valueOf(requestuest.getParameter("ex_seq")));
        logger.info("insertBadImage void end");
    }


    @PatchMapping("/admin/month")
    public void updateMonth(HttpServletRequest request){
        logger.info("updateMonth perfom return void [params] userExpireDate : {} ,userId : {}"
        ,request.getParameter("userExpireDate"),request.getParameter("userId"));
        userService.updateMonth(request);
    }

}


















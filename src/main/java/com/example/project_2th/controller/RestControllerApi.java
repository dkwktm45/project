package com.example.project_2th.controller;

import com.example.project_2th.entity.Exercies;
import com.example.project_2th.entity.ExerciesVideo;
import com.example.project_2th.entity.Postures;
import com.example.project_2th.entity.User;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
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
    @Autowired
    private final EntityManager em;
    private final Logger logger = LoggerFactory.getLogger(RestControllerApi.class);


    @PostMapping(value = "/user/exercies-info")
    @Transactional
    public String insertExURL(HttpServletRequest request) throws Exception {
        logger.info("insertExURL perfom");

        ExerciesVideo video = (ExerciesVideo) request.getSession().getAttribute("video");
        exerciesVideoService.videoUpdate(request.getParameter("cnt")
                , video,
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

    @PostMapping(value = "/user/pose-bad",produces = { "application/json" })
    @Transactional
    public void insertBadImage(@RequestParam("aiComment") List<String> aiComment
            ,HttpServletRequest req
            ,@RequestParam("file") List<MultipartFile> files) throws Exception {
        logger.info("insertBadImage perfom");
        HttpSession session = req.getSession();
        ExerciesVideo video = (ExerciesVideo) session.getAttribute("video");
        postruesService.badeImage(video,aiComment,files);
        logger.info("insertBadImage void end");
    }

    @PatchMapping("/admin/month")
    public void updateMonth(HttpServletRequest request){
        logger.info("updateMonth perfom return void [params] userExpireDate : {} ,userId : {}"
        ,request.getParameter("userExpireDate"),request.getParameter("userId"));
        userService.updateMonth(request);
    }

}


















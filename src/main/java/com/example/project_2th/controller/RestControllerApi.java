package com.example.project_2th.controller;

import com.example.project_2th.entity.Calendar;
import com.example.project_2th.response.ExerciesResponse;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


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

    @PostMapping(value = "/user/calendarView")
    public ResponseEntity<List<ExerciesResponse>> calendarView(@RequestBody  Calendar calendar) throws Exception {
        logger.info("calendarView perfom");

        logger.info("user : " + calendar.getUser());
        logger.info("day : " + calendar.getExDay());
        List<ExerciesResponse> exinfoList = exerciesService.calendarExinfo(calendar);

        logger.info(" [response] exinfoList : {}",exinfoList);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(exinfoList);
    }

    @PostMapping(value = "/user/insertExURL")
    public String insertExURL(HttpServletRequest request) throws Exception {
        logger.info("insertExURL perfom");

        String cnt = request.getParameter("cnt");
        Long user_id = Long.valueOf(request.getParameter("userId"));
        Long ex_seq = Long.valueOf(request.getParameter("exSeq"));
        ServletInputStream inputStream = request.getInputStream();

        exerciesVideoService.videoSave(cnt, user_id, ex_seq, inputStream);

        logger.info("[createVideo] cnt : {},userId : {}, exSeq : {},stream : {}"
                ,cnt,user_id,ex_seq,inputStream);

        return "main";
    }

    @GetMapping(value = "/user/insertPose")
    public ResponseEntity<Map<String, Object>> getVideoinfo(HttpServletRequest request) throws Exception {
        logger.info("insertPose perfom ");

        if (request.getParameter("videoSeq") == null){
            logger.error("videoSeq : null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Long videoSeq = Long.valueOf(request.getParameter("videoSeq"));
        Map<String,Object> videoInfo = exerciesVideoService.selectVideoInfo(videoSeq);

        logger.info("[response] exinfo : {},postures : {}"
                ,videoInfo.get("exinfo")
                ,videoInfo.get("postures"));
        return ResponseEntity.ok().body(videoInfo);
    }

    @PutMapping(value = "/user/insertBadImage")
    public void insertBadImage(HttpServletRequest requestuest) throws Exception {
        logger.info("insertBadImage perfom");

        String ai_comment = requestuest.getParameter("ai_comment");
        Long ex_seq = Long.valueOf(requestuest.getParameter("ex_seq"));

        logger.info("void ai_comment : {} , ex_seq : {}",ai_comment,ex_seq);
        postruesService.badeImage(ai_comment, ex_seq);
    }


    @PatchMapping("/admin/updateMonth")
    public void updateMonth(HttpServletRequest request){
        logger.info("updateMonth perfom return void [params] userExpireDate : {} ,userId : {}"
        ,request.getParameter("userExpireDate"),request.getParameter("userId"));
        userService.updateMonth(request);
    }

}


















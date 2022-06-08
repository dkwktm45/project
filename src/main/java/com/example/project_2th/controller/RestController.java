package com.example.project_2th.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import com.example.project_2th.entity.*;
import com.example.project_2th.repository.PosturesRepository;
import com.example.project_2th.repository.ExinfoRepository;
import com.example.project_2th.repository.UserRepository;
import com.example.project_2th.repository.VideoRepository;
import com.example.project_2th.service.ExerciesService;
import com.example.project_2th.service.ExerciesVideoService;
import com.example.project_2th.service.PostruesService;
import com.example.project_2th.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@org.springframework.web.bind.annotation.RestController
@RequiredArgsConstructor
@Slf4j
public class RestController {

    @Autowired
    private final ExerciesService exerciesService;

    @Autowired
    private final ExerciesVideoService exerciesVideoService;

    @Autowired
    private final PostruesService postruesService;

    @Autowired
    private final UserService userService;

    @GetMapping(value = "/calendarView")
    public List<Exercies> calendarView(@ModelAttribute Calendar calendar, HttpServletRequest req, HttpServletResponse res) throws Exception {
        List<Exercies> exinfoList = exerciesService.calendarExinfo(calendar);
        return exinfoList;
    }

    @PostMapping(value = "insertExURL")
    public String insertExURL(HttpServletRequest req) throws Exception {
        System.out.println("controller 진입");
        String cnt = req.getParameter("cnt");
        Long user_id = Long.valueOf(req.getParameter("userId"));
        Long ex_seq = Long.valueOf(req.getParameter("exSeq"));
        ServletInputStream inputStream = req.getInputStream();

        exerciesVideoService.videoSave(cnt, user_id, ex_seq, inputStream);

        return "main";
    }

    @GetMapping(value = "/insertPose")
    public Map<String, Object> getVideoinfo(HttpServletRequest req) throws Exception {
        Long videoSeq = Long.valueOf(req.getParameter("videoSeq"));

        return exerciesVideoService.selectVideoInfo(videoSeq);
    }

    @RequestMapping(value = "/insertBadImage.do", method = {RequestMethod.GET, RequestMethod.POST})
    public void insertBadImage(HttpServletRequest request) throws Exception {

        System.out.println(request.getParameter("ai_comment"));

        String ai_comment = request.getParameter("ai_comment");

        Long ex_seq = Long.valueOf(request.getParameter("ex_seq"));

        postruesService.badeImage(ai_comment, ex_seq);

    }


    @PostMapping("/updateMonth")
    public void updateMonth(HttpServletRequest req , HttpSession session){
        userService.updateMonth(req);
    }

}


















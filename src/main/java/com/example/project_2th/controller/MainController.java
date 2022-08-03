package com.example.project_2th.controller;

import com.example.project_2th.entity.ExerciesVideo;
import com.example.project_2th.entity.User;
import com.example.project_2th.entity.Exercies;
import com.example.project_2th.response.ExerciesResponse;
import com.example.project_2th.response.VideoResponse;
import com.example.project_2th.service.ExerciesService;
import com.example.project_2th.service.ExerciesVideoService;
import com.example.project_2th.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
public class MainController {

    @Autowired
    private final UserService userService;

    @Autowired
    private final ExerciesService exerciesService;

    @Autowired
    private final ExerciesVideoService exerciesVideoService;

    private final Logger logger = LoggerFactory.getLogger(MainController.class);
    
    // 기록페이지 re
    @GetMapping("/exinfo")
    public String goRecord(HttpServletRequest req,@AuthenticationPrincipal User user) throws UnsupportedEncodingException {
        logger.info("exinfo [get] perform");

        HttpSession session = req.getSession();
        List<VideoResponse> responses = exerciesVideoService.infoVideo(user);
        session.setAttribute("videoList", responses);
        req.setCharacterEncoding("utf-8");
        logger.info("[session] : videoList {}"
                ,responses);
        return "redirect:/user/record";
    }
    // 기록페이지
    @GetMapping("/record")
    public String recordPage() {
        return "record";
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "exception",required = false)String exception,
                        @RequestParam(value = "error",required = false)String error,
                        Model model) {
        model.addAttribute("exception",exception);
        model.addAttribute("error",error);
        return "login";
    }


    @GetMapping("/main")
    public String mainPage() {
        return "main";
    }

    @GetMapping("/error")
    public String errorPage() {
        return "error";
    }
    // calender
    @GetMapping("/calendar-exinfo")
    public String testPage() {
        return "calendar-exinfo";
    }

    // gocalender
    @GetMapping(value = "/calendar")
    public String infoCalender(HttpServletRequest req,@AuthenticationPrincipal User user) {
        logger.info("calendar perform");

        HttpSession session = req.getSession();
        List<ExerciesResponse> responses = exerciesService.calendarResponse(user);
        session.setAttribute("exerciesInfo",responses);

        logger.info("[session] : exerciesInfo {} ",responses);
        return "redirect:/user/calendar-exinfo";
    }

    @PostMapping(value = "/exinfo")
    public String insertEx(@ModelAttribute("user_exercies") Exercies exercies,HttpServletRequest req) throws Exception {
        logger.info("exinfo [post] perform");

        HttpSession session = req.getSession();
        ExerciesResponse exinfo = exerciesService.exerciesInfo(exercies);
        ExerciesVideo video = exerciesVideoService.Video(exercies);
        if (exinfo== null){
            log.error("운동 정보가 담겨 있지 않습니다.");
            return "redirect:/user/main";
        }
        session.setAttribute("video",video);
        session.setAttribute("exinfo",exinfo);

        logger.info("[session] : exinfo {} ",exinfo.toString());
        return "redirect:/user/cam";
    }

    @RequestMapping("/cam")
    public String cam() {
        return "cam";
    }

}

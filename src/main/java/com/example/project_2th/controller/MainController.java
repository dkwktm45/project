package com.example.project_2th.controller;

import com.example.project_2th.entity.User;
import com.example.project_2th.entity.Exercies;
import com.example.project_2th.response.CalendarResponse;
import com.example.project_2th.response.ExerciesResponse;
import com.example.project_2th.security.service.UserContext;
import com.example.project_2th.service.ExerciesService;
import com.example.project_2th.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    private final Logger logger = LoggerFactory.getLogger(MainController.class);
    
    // 기록페이지 re
    @GetMapping("/exinfo")
    public String goRecord(HttpServletRequest req,@AuthenticationPrincipal User user) {
        logger.info("exinfo [get] perform");

        HttpSession session = req.getSession();

        Map<String,Object> map = userService.infoRecord(user);
        session.setAttribute("exinfoList", map.get("exinfoList"));
        session.setAttribute("videoList", map.get("videoList"));

        logger.info("[session] : exinfoList {} , videoList {}"
                ,map.get("exinfoList")
                ,map.get("videoList"));
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
    @GetMapping("/test")
    public String testPage() {
        return "test";
    }

    // gocalender
    @GetMapping(value = "/calendar")
    public String infoCalender(HttpServletRequest req,@AuthenticationPrincipal User user) {
        logger.info("calendar perform");

        HttpSession session = req.getSession();
        List<CalendarResponse> responses = userService.infoCalendar(user);
        session.setAttribute("calendarInfo",responses);

        logger.info("[session] : calendarInfo {} ",responses);
        return "redirect:/user/test";
    }

    @PostMapping(value = "/exinfo")
    public String insertEx(@ModelAttribute("user_exercies") Exercies exercies,HttpServletRequest req) throws Exception {
        logger.info("exinfo [post] perform");

        HttpSession session = req.getSession();
        ExerciesResponse exinfo = exerciesService.exerciesInfo(exercies);
        if (exinfo== null){
            log.error("운동 정보가 담겨 있지 않습니다.");
            return "redirect:/main";
        }
        session.setAttribute("exinfo",exinfo);

        logger.info("[session] : exinfo {} ",exinfo);
        return "redirect:/user/cam";
    }

    @RequestMapping("/cam")
    public String cam() {
        return "cam";
    }

}

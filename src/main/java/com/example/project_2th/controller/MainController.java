package com.example.project_2th.controller;

import com.example.project_2th.entity.User;
import com.example.project_2th.entity.Exercies;
import com.example.project_2th.response.ExerciesResponse;
import com.example.project_2th.service.ExerciesService;
import com.example.project_2th.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    @GetMapping("/goRecord")
    public String goRecord(HttpServletRequest req) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        Map<String,Object> map = userService.infoRecord(user);
        session.setAttribute("exinfoList", map.get("exinfoList"));
        session.setAttribute("videoList", map.get("videoList"));
        return "redirect:/user/record";
    }

    // 기록페이지
    @GetMapping("/record")
    public String record() {
        return "record";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @GetMapping("/main")
    public String main() {
        return "main";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }
    // calender
    @GetMapping("/test")
    public String test() {
        return "test";
    }

    // gocalender
    @GetMapping(value = "/infoCalender")
    public String infoCalender(HttpServletRequest req) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        session.setAttribute("calendarInfo",userService.infoCalendar(user));

        return "redirect:/user/test";
    }

    @PostMapping(value = "/insertEx")
    public String insertEx(@ModelAttribute("user_exercies") Exercies exercies,HttpServletRequest req) throws Exception {
        HttpSession session = req.getSession();
        ExerciesResponse exinfo = exerciesService.exerciesInfo(exercies);
        if (exinfo== null){
            log.error("운동 정보가 담겨 있지 않습니다.");
            return "redirect:/main";
        }
        session.setAttribute("exinfo",exinfo);
        return "redirect:/user/cam";
    }

    @RequestMapping("/cam")
    public String cam() {
        return "cam";
    }

}

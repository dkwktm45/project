package com.example.project_2th.controller;

import com.example.project_2th.entity.Calendar;
import com.example.project_2th.entity.User;
import com.example.project_2th.entity.ExerciesVideo;
import com.example.project_2th.entity.Exercies;
import com.example.project_2th.repository.ExinfoRepository;
import com.example.project_2th.repository.UserRepository;
import com.example.project_2th.repository.VideoRepository;
import com.example.project_2th.service.ExerciesService;
import com.example.project_2th.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {

    @Autowired
    private final UserService userService;

    @Autowired
    private final ExerciesService exerciesService;


    // 기록페이지 re
    @GetMapping("/goRecord")
    public String goRecord(HttpServletRequest req) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        Map<String,Object> map = userService.infoRecord(user);


        session.setAttribute("exinfoList", map.get("exinfoList"));
        session.setAttribute("videoList", map.get("videoList"));

        return "redirect:/record";
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

    // login -> main or admin
    @PostMapping(value = "/loginInsert")
    public String memberLogin(@ModelAttribute User user, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession(true);
        User loginUser = userService.login(user.getUserPhone(), user.getUserGym());
        return userService.filterLogin(loginUser,session);
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/main")
    public String main() {
        return "main";
    }

    // calender
    @GetMapping("/test")
    public String test() {
        return "test";
    }

    // gocalender
    @GetMapping(value = "/infoCalender")
    public String infoCalender(HttpServletRequest req ,Model model) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        session.setAttribute("exinfo",userService.infoCalendar(user));

        return "redirect:test";
    }

    @PostMapping(value = "/insertEx")
    public String insertEx(@ModelAttribute("user_exercies") Exercies exercies,HttpServletRequest req) throws Exception {
        HttpSession session = req.getSession();
        session.setAttribute("exinfo",exerciesService.exerciesInfo(exercies));

        return "redirect:cam.do";

    }

    @RequestMapping("/cam.do")
    public String cam() {
        return "cam";
    }


//

//
//    @RequestMapping(value="/insertJoin.do", method= {RequestMethod.GET, RequestMethod.POST})
//    public String insertJoin(guest memberVO ) {
//
//
//        return "redirect:/join.do";
//    }
}

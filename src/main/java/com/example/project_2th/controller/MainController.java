package com.example.project_2th.controller;

import com.example.project_2th.entity.User;
import com.example.project_2th.entity.Exercies;
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
public class MainController {

    @Autowired
    private final UserService userService;

    @Autowired
    private final ExerciesService exerciesService;

    private final Logger logger = LoggerFactory.getLogger("MainController 의 로그");
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
    public String memberLogin(HttpServletRequest request,HttpSession session ) throws Exception {
        session = request.getSession(true);
        Map<String ,Object> list = userService.filterLogin(request.getParameter("userPhone")
                ,request.getParameter("userGym")
                ,session);
        if (list.size() ==2){
            logger.info("admin page");
            session.setAttribute("userList",list.get("userList"));
            session.setAttribute("user",list.get("user"));
            return "redirect:/admin";
        }else if(list.size()==1){
            logger.info("user page");
            session.setAttribute("user",list.get("user"));
            return "redirect:/main";
        }
        logger.info("로그인 실패");
        return "redirect:/login";
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
    public String infoCalender(HttpServletRequest req) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        session.setAttribute("exinfo",userService.infoCalendar(user));

        return "redirect:test";
    }

    @PostMapping(value = "/insertEx")
    public String insertEx(@ModelAttribute("user_exercies") Exercies exercies,HttpServletRequest req) throws Exception {
        HttpSession session = req.getSession();
        Exercies exinfo = exerciesService.exerciesInfo(exercies);
        if (exinfo== null){
            log.error("운동 정보가 담겨 있지 않습니다.");
            return "redirect:/main";
        }
        session.setAttribute("exinfo",exinfo);
        return "redirect:cam.do";

    }

    @RequestMapping("/cam.do")
    public String cam() {
        return "cam";
    }

}

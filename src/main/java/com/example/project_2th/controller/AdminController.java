package com.example.project_2th.controller;

import com.example.project_2th.entity.User;
import com.example.project_2th.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private final UserService userService;

    private final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @GetMapping({"/",""})
    public String admin() {
        return "/admin";
    }


    @GetMapping("/video")
    public String vidoeMember() {
        return "redirect:/admin";
    }

    @GetMapping("/Join")
    public String goJoin() {
        return "/join";
    }

    @GetMapping("/Member")
    public String aminMember(HttpServletRequest req , HttpSession session) {
        session =req.getSession();
        User loginUser = (User) session.getAttribute("user");
        session.setAttribute("userList",userService.reLoadMember(loginUser));
        return "/adminMember";
    }

    @PostMapping(value = "/joinMember")
    public String joinMember(@RequestBody User user){
        logger.info("name : " +user.getUserName());
        logger.info("videoYn : " +String.valueOf(user.getVideoYn()));
        logger.info("gym : " +user.getUserGym());
        logger.info("birthdate : " +String.valueOf(user.getUserBirthdate()));
        logger.info("expiredDate : " +String.valueOf(user.getUserExpireDate()));
        logger.info("phone : " +user.getUserPhone());

        userService.join(user);
        return "redirect:/admin/Join";
    }

}

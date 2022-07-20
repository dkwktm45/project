package com.example.project_2th.controller;

import com.example.project_2th.entity.User;
import com.example.project_2th.response.UserResponse;
import com.example.project_2th.response.VideoResponse;
import com.example.project_2th.security.service.UserContext;
import com.example.project_2th.service.ExerciesVideoService;
import com.example.project_2th.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private final UserService userService;

    @Autowired
    private final ExerciesVideoService exerciesVideoService;

    private final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @GetMapping({"/",""})
    public String adminPage(HttpServletRequest request
            ,@AuthenticationPrincipal User user) {
        logger.info("admin : loadUser perform");

        HttpSession session = request.getSession();
        List<VideoResponse> videoResponses = exerciesVideoService.loadUser(user);
        session.setAttribute("userList",videoResponses);

        logger.info("admin : loadUser end {}",videoResponses);
        return "/admin";
    }


    @GetMapping("/video")
    public String vidoeMember() {
        return "redirect:/admin";
    }

    @GetMapping("/register")
    public String goJoin() {
        return "/join";
    }

    @GetMapping("/member")
    public String aminMember(HttpServletRequest req , HttpSession session) {
        logger.info("aminMember : reLoadMember perform");

        session =req.getSession();
        User loginUser = (User) session.getAttribute("user");
        List<UserResponse> responseList = userService.reLoadMember(loginUser);
        session.setAttribute("userList",responseList);

        logger.info("aminMember : reLoadMember end {}",responseList);
        return "/admin-member";
    }

    @PutMapping(value = "/join-member")
    public String insertMember(@RequestBody User user){
        logger.info("join-member : join perform");

        logger.info("name : " +user.getUserName());
        logger.info("videoYn : " +String.valueOf(user.getVideoYn()));
        logger.info("gym : " +user.getUserGym());
        logger.info("birthdate : " +String.valueOf(user.getUserBirthdate()));
        logger.info("expiredDate : " +String.valueOf(user.getUserExpireDate()));
        logger.info("phone : " +user.getUserPhone());

        userService.join(user);
        return "redirect:/admin/register";
    }
}

package com.example.project_2th.controller;

import com.example.project_2th.entity.User;
import com.example.project_2th.response.VideoResponse;
import com.example.project_2th.service.ExerciesVideoService;
import com.example.project_2th.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String aminMember(
            @AuthenticationPrincipal User user
            , HttpSession session) {
        SecurityContextHolder.getContext();
        logger.info("aminMember : reLoadMember perform");

        session.setAttribute("userList",userService.reLoadMember(user));

        logger.info("aminMember : reLoadMember end");
        return "admin-member";
    }
    @PreAuthorize("haseRole('ROLE_ADMIN')")
    @PutMapping(value = "/join-member")
    public String insertMember(@RequestBody User user){
        logger.info("join-member : join perform");

        userService.join(user);
        return "redirect:/admin/register";
    }
}

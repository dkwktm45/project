package com.example.project_2th.controller;

import com.example.project_2th.entity.User;
import com.example.project_2th.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private final UserService userService;

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
        userService.reLoadMember(req, session);
        return "/adminMember";
    }

    @PostMapping("/joinMember")
    public String joinMember(@ModelAttribute User user){
        log.info(user.getUserName());
        log.info(String.valueOf(user.getVideoYn()));
        log.info(user.getUserGym());
        log.info(String.valueOf(user.getUserBirthdate()));
        log.info(String.valueOf(user.getUserExpireDate()));
        userService.join(user);
        return "redirect:/admin/goJoin";
    }
    @PostMapping("/updateMonth")
    public void updateMonth(HttpServletRequest req , HttpSession session){
        //userService.updateMonth(req);
    }
//
//
//
//    @RequestMapping(value="/insertExURL.do", method= {RequestMethod.GET, RequestMethod.POST})
//    public String insertURL(HttpServletRequest request) throws Exception {
//
//
//        return "main.do";
//
//    }
//
//    @RequestMapping(value="/insertBadImage.do", method= {RequestMethod.GET, RequestMethod.POST})
//    public void insertBadImage(HttpServletRequest request) throws Exception {
//
//
//        //return "main.do";
//
//    }
//
//    @RequestMapping(value="/memberExinfo.do", method= {RequestMethod.GET, RequestMethod.POST})
//    public List<exinfo> memberExinfo(String user_id , HttpServletRequest req) throws Exception {
//
//        return memberVideo;
//
//    }
//
//    @RequestMapping(value="/memberName.do", method= {RequestMethod.GET, RequestMethod.POST}, produces = "text/html; charset=utf-8")
//    public String memberName(int video_seq , HttpServletRequest req, HttpServletResponse res) throws Exception {
//
//        return memberVideo.getUser_name();
//
//    }
//
//    @RequestMapping(value="/calendarView.do" , method= {RequestMethod.GET, RequestMethod.POST})
//    public List<exinfo> calendarView(String user_id , String ex_date , HttpServletRequest req, HttpServletResponse res) throws Exception {
//
//        return memberVideo;
//
//    }
//
//    @RequestMapping(value="/dateVideo.do", method= {RequestMethod.GET, RequestMethod.POST})
//    public String dateVideo(String user_id ,String video_date, HttpServletRequest req) throws Exception {
//
//
//        return "record.do";
//    }
//
//    @RequestMapping(value="/insertPose.do", method= {RequestMethod.GET, RequestMethod.POST})
//    public List<deepPostures> insertPose(Model model , int video_seq , String video_date, HttpServletRequest req) throws Exception {
//
//
//        return List;
//    }
}

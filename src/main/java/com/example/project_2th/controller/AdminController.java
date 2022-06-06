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

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private final UserService userService;

    @GetMapping({"/",""})
    public String admin() {
        return "admin";
    }

    @GetMapping("/goJoin")
    public String join() {
        return "join";
    }

    @GetMapping("/goAdminMember")
    public String aminMember() {
        return "adminMember";
    }

    @PostMapping("/joinMember")
    public String joinMember(@ModelAttribute User user){
        userService.join(user);
        return "redirect:/admin/goJoin";
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

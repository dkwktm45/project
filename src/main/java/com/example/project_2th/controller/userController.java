package com.example.project_2th.controller;

import com.example.project_2th.entity.UserCalendar;
import com.example.project_2th.entity.User;
import com.example.project_2th.repository.GuestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class userController {

    @Autowired
    private final GuestRepository guestRepository;


//
//    @Autowired
//    private mainMapper mapper;
//
//    @RequestMapping("/admin.do")
//    public String admin(HttpServletRequest req) {
//
//    }
//
//    @RequestMapping("/admin_member.do")
//    public String admin_member(Model session) {
//
//    }
//
//    @RequestMapping("/extensionMember.do")
//    public String extensionMember(guest memberVO,Model session) {
//
//    }
//
//    @RequestMapping("/test.do")
//    public String test() {
//        return "test";
//    }
//    @RequestMapping("/calender.do")
//    public String calender() {
//        return "calender";
//    }
//
//
//
//    @RequestMapping("/main.do")
//    public String main() {
//        return "main";
//    }
//
    // 기록페이지 re
    @GetMapping("/goRecord")
    public String goRecord(HttpServletRequest req) {
        HttpSession session = req.getSession(true);

        return "redirect:/record";
    }

    // 기록페이지
    @GetMapping("/record")
    public String record() {
        return "record";
    }

//
//    @RequestMapping("/join.do")
//    public String join() {
//        return "join";
//    }
//
//


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // login -> main
    @PostMapping(value="/loginInsert")
    public String memberLogin(@ModelAttribute User user, HttpSession session) throws Exception {
        log.info("id : {},gym : {}", user.getUserPhone() , user.getUserGym());

        User loginUser = guestRepository.findByUserIdAndUserGym(user.getUserPhone(),user.getUserGym());

        if (loginUser != null) {
            log.info("로그인 성공");
            session.setAttribute("user",loginUser);
            return "main";
        }
        log.info("로그인 실패?");
        return "redirect:/login";
    }

    @GetMapping("/main")
    public String main(){
        return "main";
    }

    // calender
    @GetMapping("/test")
    public String test() {
        return "test";
    }

    // gocalender
    @GetMapping(value="/infoCalender")
    public String infoCalender(HttpServletRequest req){

        HttpSession session = req.getSession(true);
        User user = (User) session.getAttribute("user");

        List<UserCalendar> exinfo = guestRepository
                .findByUserId(user.getUserId())
                .getCalendarList();

        exinfo.forEach(System.out::println);
        session.setAttribute("exinfo",exinfo);

        return "redirect:/test";
    }


////    @RequestMapping("/cam.do")
////    public String cam() {
////        return "cam";
////    }
////
////    @RequestMapping("/warmingup.do")
////    public String warmingup() {
////        return "warmingup";
////    }
//
//    //안 쓰는 것
//    @RequestMapping(value="/insertEx.do")
//    public String insertEx(exinfo memberVO , HttpServletRequest req,  RedirectAttributes rttr) throws Exception {
//
//        return "redirect:/cam.do";
//
//    }
//



//

//
//    @RequestMapping(value="/insertJoin.do", method= {RequestMethod.GET, RequestMethod.POST})
//    public String insertJoin(guest memberVO ) {
//
//
//        return "redirect:/join.do";
//    }
}

package com.example.project_2th.controller;

import com.example.project_2th.entity.user;
import com.example.project_2th.repository.guestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class userController {

    @Autowired
    private final guestRepository guestRepository;

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
//    public String admin_member(Model model) {
//
//    }
//
//    @RequestMapping("/extensionMember.do")
//    public String extensionMember(guest memberVO,Model model) {
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
//    @RequestMapping("/record.do")
//    public String record(String user_id, HttpServletRequest req) {
//
//
//        return "record";
//    }
//
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

    @PostMapping(value="/loginInsert")
    public String memberLogin(@ModelAttribute user user) throws Exception {
        log.info("id : {},gym : {}", user.getUserPhone() , user.getUserGym());

        user loginUser = guestRepository.findByUserIdAndUserGym(user.getUserPhone(),user.getUserGym());

        if (loginUser != null) {

            log.info("로그인 성공");
            return "redirect:/main";
        }
        return "redirect:/login";
    }

    @GetMapping("/main")
    public String main(){
        return "main";
    }

    @GetMapping("/test")
    public String test() {

        return "test";
    }
//    @RequestMapping("/cam.do")
//    public String cam() {
//        return "cam";
//    }
//
//    @RequestMapping("/warmingup.do")
//    public String warmingup() {
//        return "warmingup";
//    }
//
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
//    @RequestMapping(value="/infoCalender.do")
//    public String infoCalender(String user_id ,HttpServletRequest req){
//
//        return "redirect:/test.do";
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

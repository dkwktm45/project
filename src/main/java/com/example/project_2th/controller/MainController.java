package com.example.project_2th.controller;

import com.example.project_2th.entity.Calendar;
import com.example.project_2th.entity.User;
import com.example.project_2th.entity.ExerciesVideo;
import com.example.project_2th.entity.Exercies;
import com.example.project_2th.repository.ExinfoRepository;
import com.example.project_2th.repository.UserRepository;
import com.example.project_2th.repository.VideoRepository;
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

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final ExinfoRepository exinfoRepository;

    @Autowired
    private final VideoRepository videoRepository;

    @Autowired
    private final EntityManager em;

    // 기록페이지 re
    @GetMapping("/goRecord")
    public String goRecord(HttpServletRequest req) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        Long id = user.getUserId();
        List<ExerciesVideo> videoList = userRepository.findByUserId(id).getExercieVideosList();
        List<Exercies> exerciesList = new ArrayList<>();
        for (int i = 0; i < videoList.size(); i++) {
            exerciesList.add(videoList.get(i).getExercies());
        }
        session.setAttribute("exinfoList", exerciesList);
        session.setAttribute("videoList", videoList);

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
        log.info("id : {},gym : {}", user.getUserPhone(), user.getUserGym());
        HttpSession session = request.getSession(true);

        User loginUser = userRepository.findByUserIdAndUserGym(user.getUserPhone(), user.getUserGym());

        if (loginUser == null) {
            log.info("로그인 실패");
            return "redirect:/login";
        } else {
            if (loginUser.getManagerYn() == 1) {
                System.out.println("admin 로그인 성공");
                session.setAttribute("user", loginUser);
                return "redirect:/admin";

            } else {
                System.out.println("user 로그인 성공");
                session.setAttribute("user", loginUser);
                return "redirect:/main";
            }
        }
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

    @Transactional(readOnly = true)
    @GetMapping(value = "/infoCalender")
    public String infoCalender(HttpServletRequest req ,Model model) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");


        ModelAndView mav = new ModelAndView();

        List<User> users = userRepository.findAllByFetchJoin();
        List<Calendar> exinfo = users.get(Math.toIntExact(user.getUserId())-1).getCalendarList();


        session.setAttribute("exinfo",exinfo);

        return "redirect:test";
    }

    @PostMapping(value = "/insertEx")
    public String insertEx(HttpServletRequest req) throws Exception {

        ExerciesVideo exerciesVideo = new ExerciesVideo();
        Exercies exercies = new Exercies();
        HttpSession session = req.getSession(true);
        User user = (User) session.getAttribute("user");
        System.out.println(user.getUserId());


        exercies.setUser(user);
        exercies.setUserSet(req.getParameter("userSet"));
        exercies.setExCount(req.getParameter("exCount"));
        exercies.setExName(req.getParameter("exName"));
        exercies.setExKinds(req.getParameter("exKinds"));
        exercies.setExDay(Date.valueOf(LocalDate.now()));

        exinfoRepository.save(exercies);

        Exercies exinfo = exinfoRepository.findByOne(user.getUserId(), req.getParameter("exName"));

        session.setAttribute("exinfo", exinfo);

        return "redirect:/cam.do";

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

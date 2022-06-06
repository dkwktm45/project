package com.example.project_2th.service;

import com.example.project_2th.entity.Calendar;
import com.example.project_2th.entity.Exercies;
import com.example.project_2th.entity.ExerciesVideo;
import com.example.project_2th.entity.User;
import com.example.project_2th.repository.UserRepository;
import com.example.project_2th.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final VideoRepository videoRepository;

    public User login(String phone, String gym){
        User loginUser = userRepository.findByUserIdAndUserGym(phone,gym);
        return loginUser;
    }

    public String filterLogin(User loginUser, HttpSession session ){
        if (loginUser == null) {
            log.info("로그인 실패");
            return "redirect:/login";
        } else {
            if (loginUser.getManagerYn() == 1) {
                System.out.println("admin 로그인 성공");
                List<User> userList = userRepository.findByUserGymAndManagerYn(loginUser.getUserGym(),loginUser.getManagerYn()-1);

                session.setAttribute("userList",userList);
                session.setAttribute("user", loginUser);
                return "redirect:/admin";

            } else {
                System.out.println("user 로그인 성공");
                session.setAttribute("user", loginUser);
                return "redirect:/main";
            }
        }
    }

    public List<Calendar> infoCalendar(User user){
        List<User> users = userRepository.findAllByFetchJoin();
        List<Calendar> exinfo = users.get(Math.toIntExact(user.getUserId() - 1)).getCalendarList();
        return exinfo;
    }

    public Map<String, Object> infoRecord(User user){
        List<User> users = userRepository.findAllByFetchJoin();
        List<ExerciesVideo> videoList = users.get(Math.toIntExact(user.getUserId() - 1)).getExercieVideosList();
        List<Exercies> exerciesList = new ArrayList<>();
        for (int i = 0; i < videoList.size(); i++) {
            exerciesList.add(videoList.get(i).getExercies());
        }
        Map<String ,Object> map = new HashMap<>();
        System.out.println(videoList.get(0).getFileName());
        map.put("videoList",videoList);
        map.put("exerciesList",exerciesList);

        return map;
    }
    public void join(User user){
        userRepository.save(user);
    }
}

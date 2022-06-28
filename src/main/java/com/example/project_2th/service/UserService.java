package com.example.project_2th.service;

import com.example.project_2th.adapter.PostNotFound;
import com.example.project_2th.controller.MainController;
import com.example.project_2th.entity.Calendar;
import com.example.project_2th.entity.Exercies;
import com.example.project_2th.entity.ExerciesVideo;
import com.example.project_2th.entity.User;
import com.example.project_2th.repository.UserRepository;
import com.example.project_2th.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

    @Autowired
    private final UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    public Map<String,Object> filterLogin(String number, String gym){
        User loginUser = userRepository.findByLoginNumberAndUserGym(number,gym).orElseThrow(PostNotFound::new);
        Map<String,Object> list = new HashMap<>();
        if (loginUser == null) {
            log.info("로그인 실패");
            return null;
        } else {
            if (loginUser.getManagerYn() == 1) {
                // 중복 구간 수정 필요!
                List<User> userList = userRepository.findByUserGymAndManagerYn(loginUser.getUserGym(),loginUser.getManagerYn()-1);
                list.put("user",loginUser);
                list.put("userList",userList);
                return list;
            } else {
                log.info("session : "+loginUser);
                list.put("user",loginUser);
                return list;
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
        map.put("videoList",videoList);
        map.put("exinfoList",exerciesList);

        return map;
    }
    public void join(User user){
        User resultUser= User.builder().loginNumber(user.getUserPhone()).userName(user.getUserName()).userPhone(user.getUserPhone())
                .userBirthdate(user.getUserBirthdate()).userExpireDate(user.getUserExpireDate())
                .managerYn(user.getManagerYn()).videoYn(user.getVideoYn()).userGym(user.getUserGym()).build();
        logger.info("join perform : {}",resultUser);

        validateDuplicateMember(resultUser);
        userRepository.save(resultUser);
    }

    private void validateDuplicateMember(User user) {
        logger.info("user validation");

        User findMember = userRepository.findByUserIdAndUserGym(user.getUserPhone(),user.getUserGym()).orElseThrow(PostNotFound::new);
        if(!(findMember == null)){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
    public List<User> reLoadMember(User user){
        return userRepository.findByUserGymAndManagerYn(user.getUserGym(),user.getManagerYn()-1);
    }

    public void updateMonth(HttpServletRequest req){
        Date expiredDate= Date.valueOf(req.getParameter("userExpireDate"));
        Long id = Long.valueOf(req.getParameter("userId"));
        User user = userRepository.findByUserId(id).orElseThrow(PostNotFound::new);
        user.setUserExpireDate(expiredDate);
        userRepository.save(user);
    }
    public String collectPage(Map<String ,Object> list,HttpSession session){
        if (list.size() ==2){
            logger.info("admin page");
            logger.info("users : " + list.get("userList"));
            logger.info("manager : " + list.get("user"));
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
}

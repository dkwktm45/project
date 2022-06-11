package com.example.project_2th.service;

import com.example.project_2th.entity.Calendar;
import com.example.project_2th.entity.Exercies;
import com.example.project_2th.entity.ExerciesVideo;
import com.example.project_2th.entity.User;
import com.example.project_2th.repository.UserRepository;
import com.example.project_2th.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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


    public Map<String,Object> filterLogin(String phone, String gym, HttpSession session ){
        User loginUser = userRepository.findByUserPhoneAndUserGym(phone,gym);
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
        System.out.println(videoList.get(0).getFileName());
        map.put("videoList",videoList);
        map.put("exerciesList",exerciesList);

        return map;
    }
    public void join(User user){
        String phone = user.getUserPhone();
        String login = phone.substring(9);
        User resultUser= User.builder().loginNumber(login).userName(user.getUserName()).userPhone(user.getUserPhone())
                .userBirthdate(user.getUserBirthdate()).userExpireDate(user.getUserExpireDate())
                .managerYn(user.getManagerYn()).videoYn(user.getVideoYn()).userGym(user.getUserGym()).build();

        validateDuplicateMember(resultUser);
        log.info(resultUser.getUserName() + "회원가입 성공!");
        userRepository.save(resultUser);
    }

    private void validateDuplicateMember(User user) {
        /*User findMember = userRepository.findByUserIdAndUserGym(user.getUserPhone(),user.getUserGym());
        if(!(findMember == null)){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }*/
    }
    public void reLoadMember(HttpServletRequest req,HttpSession session){
        session =req.getSession();
        User loginUser = (User) session.getAttribute("user");
        List<User> userList = userRepository.findByUserGymAndManagerYn(loginUser.getUserGym(),loginUser.getManagerYn()-1);
        session.setAttribute("userList",userList);
    }
    public void updateMonth(HttpServletRequest req){
        Date expiredDate= Date.valueOf(req.getParameter("userExpireDate"));
        Long id = Long.valueOf(req.getParameter("userId"));
        User user = userRepository.findByUserId(id);
        user.setUserExpireDate(expiredDate);
        userRepository.save(user);
        //return "redirect:/admin/Member";
    }

}

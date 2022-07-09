package com.example.project_2th.service;

import com.example.project_2th.exception.PostNotFound;
import com.example.project_2th.entity.*;
import com.example.project_2th.repository.UserRepository;
import com.example.project_2th.response.CalendarResponse;
import com.example.project_2th.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder encoder;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    public Map<String, Object> filterLogin(String number, String gym) {
        User loginUser = userRepository.findByLoginNumberAndUserGym(number, gym).orElseThrow(PostNotFound::new);
        UserResponse userResponse = new UserResponse(loginUser);

        Map<String, Object> list = new HashMap<>();
        if (userResponse.getManagerYn() == 1) {
            List<UserResponse> userList = userRepository.findByUserGymAndManagerYn(userResponse.getUserGym(), userResponse.getManagerYn() - 1)
                    .stream().map(UserResponse::new).collect(Collectors.toList());
            list.put("user", userResponse);
            list.put("userList", userList);
            return list;
        } else if(userResponse.getManagerYn() == 0){
            list.put("user", loginUser);
            return list;
        }
        return null;
    }

    public List<CalendarResponse> infoCalendar(User user) {
        List<CalendarResponse> users = userRepository.findAllByFetchJoin()
                .get(Math.toIntExact(user.getUserId() - 1))
                .getCalendarList().stream().map(CalendarResponse::new)
                .collect(Collectors.toList());
        return users;
    }

    public Map<String, Object> infoRecord(User user) {
        List<ExerciesVideo> videoList = userRepository.findAllByFetchJoin().
                get(Math.toIntExact(user.getUserId() - 1)).getExercieVideosList();
        List<Exercies> exerciesList = videoList.stream().map(
                video -> video.getExercies()
        ).collect(Collectors.toList());
        Map<String, Object> map = new HashMap<>();
        map.put("videoList", videoList);
        map.put("exinfoList", exerciesList);

        return map;
    }

    public void join(User user) {

        UserEditor.UserEditorBuilder editorBuilder = user.toEditor();
        String phone = user.getUserPhone().substring(9);
        String test = encoder.encode(phone);
        UserEditor userEditor = editorBuilder.userPhone(test).build();
        user.editor(userEditor);

        logger.info("join perform : {}", user);

        logger.info("user validation");
        Optional<User> vaildUser = userRepository.findByUserIdAndUserGym(
                user.getUserPhone()
                , user.getUserGym());
        Object vaild = (vaildUser == null) ? userRepository.save(user) : vaildUser.get().valid();
    }

    public List<UserResponse> reLoadMember(User user) {
        List<UserResponse> result = userRepository.findByUserGymAndManagerYn(user.getUserGym(), user.getManagerYn() - 1)
                .stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
        return result;
    }

    public void updateMonth(HttpServletRequest req) {
        LocalDate expiredDate = LocalDate.parse((req.getParameter("userExpireDate")));
        Long id = Long.valueOf(req.getParameter("userId"));
        User user = userRepository.findByUserId(id).orElseThrow(PostNotFound::new);

        logger.info("update perform service , expired : {}",expiredDate);
        UserEditor.UserEditorBuilder editorBuilder = user.toEditor();
        UserEditor userEditor = editorBuilder.userExpireDate(expiredDate).build();
        user.editor(userEditor);
    }

    public String collectPage(Map<String, Object> list, HttpSession session) {

        if (list.size() == 2) {
            logger.info("admin page");
            logger.info("users : " + list.get("userList"));
            logger.info("manager : " + list.get("user"));
            session.setAttribute("userList", list.get("userList"));
            session.setAttribute("user", list.get("user"));
            return "redirect:/admin";
        } else if (list.size() == 1) {
            logger.info("user page");
            session.setAttribute("user", list.get("user"));
            return "redirect:/main";
        }
        logger.info("로그인 실패");
        return "redirect:/login";
    }
}

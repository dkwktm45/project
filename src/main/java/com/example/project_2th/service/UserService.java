package com.example.project_2th.service;

import com.example.project_2th.exception.PostNotFound;
import com.example.project_2th.entity.*;
import com.example.project_2th.repository.UserRepository;
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
        if (vaildUser == null) {
            userRepository.save(user);
        }else{
            throw new IllegalStateException("존재하는 회원입니다.");
        }
    }

    public List<UserResponse> reLoadMember(User user) {
        return userRepository.findByUserGymAndManagerYn(user.getUserGym(), user.getManagerYn() - 1)
                .orElseThrow(PostNotFound::new).stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    public void updateMonth(HttpServletRequest req) {
        LocalDate expiredDate = LocalDate.parse((req.getParameter("userExpireDate")));
        Long id = Long.valueOf(req.getParameter("userId"));
        User user = userRepository.findById(id).orElseThrow(PostNotFound::new);

        logger.info("update perform service , expired : {}",expiredDate);
        UserEditor.UserEditorBuilder editorBuilder = user.toEditor();
        UserEditor userEditor = editorBuilder.userExpireDate(expiredDate).build();
        user.editor(userEditor);
    }
}

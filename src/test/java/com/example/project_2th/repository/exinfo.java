package com.example.project_2th.repository;

import com.example.project_2th.entity.User;
import com.example.project_2th.entity.UserExercies;
import groovy.util.logging.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Slf4j
public class exinfo{

    @Autowired
    GuestRepository guestRepository;

    @Autowired
    UserCalendarRepositroy userCalendarRepositroy;

    @Autowired
    ExinfoRepository exinfoRepository;
    @Test
    @Transactional
    public void insertEx(){
        User user = guestRepository.findByUserId(1L);
        UserExercies userExercies = new UserExercies();

        System.out.println(Date.valueOf(LocalDate.now()));

        System.out.println("user : " + user.getUserId());
        userExercies.setUser(user);
        userExercies.setUserSet("1");
        userExercies.setExCount("12");
        userExercies.setExName("체스트 플라이");
        userExercies.setExKinds("가슴");
        System.out.println("save 간다!!!!");

        exinfoRepository.save(userExercies);
        List<UserExercies> result = guestRepository.findByUserId(1L).getUserExerciesList();
        result.forEach(System.out::println);
    }
    @Test
    public void insertExTest(){
        User user = guestRepository.findByUserId(1L);
        UserExercies userExercies = new UserExercies();

        List<UserExercies> result = guestRepository.findByUserId(1L).getUserExerciesList();

        result.forEach(System.out::println);
    }
}

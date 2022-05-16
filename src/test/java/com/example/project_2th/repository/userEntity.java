package com.example.project_2th.repository;


import com.example.project_2th.entity.User;
import com.example.project_2th.entity.UserCalendar;
import com.example.project_2th.entity.UserExercies;
import groovy.util.logging.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@Transactional
@Slf4j
public class userEntity {

    @Autowired
    GuestRepository guestRepository;

    @Autowired
    UserCalendarRepositroy userCalendarRepositroy;

    @Autowired
    ExinfoRepository exinfoRepository;

    @Test
    void login(){
        User user = new User();
        user.setUserGym("해운대");
        user.setUserPhone("49034");

        guestRepository.save(user);

        User user2 = guestRepository.findByUserPhoneAndUserGym("49034","해운대");

        System.out.println(user2.getUserId());

        System.out.println(user2.getUserGym());
    }

    @Test
    void calender(){
        User user = new User();
        user.setUserGym("해운대");
        user.setUserPhone("49034");

        guestRepository.save(user);

        User userInfo = guestRepository.findByUserId(1L);

        List<UserCalendar> result = guestRepository
                .findByUserId(userInfo.getUserId())
                .getCalendarList();


        result.forEach(System.out::println);
    }

    @Test
    void exinfo() {
        Date timestamp = Date.valueOf("2022-05-04");

        List<UserExercies> result = guestRepository
                .findByUserId(1L)
                .getUserExerciesList();

        result.forEach(System.out::println);


        List<UserExercies> exinfoList = null;
        Date day = null;
        for(int i = 0; i < result.size(); i++){
            day = result.get(i).getExDay();
            System.out.println(day);
            if (day.equals(timestamp)) {
                System.out.println(day);
                day = timestamp;
                break;
            }
        }

        exinfoList = exinfoRepository.findByExDay(day);
        exinfoList.forEach(System.out::println);
    }



}

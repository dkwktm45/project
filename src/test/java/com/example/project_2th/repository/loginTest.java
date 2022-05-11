package com.example.project_2th.repository;

import com.example.project_2th.entity.UserCalendar;
import com.example.project_2th.entity.user;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@Transactional
public class loginTest {

    @Autowired
    guestRepository guestRepository;


    @Test
    void login(){
        user user = new user();
        user.setUserGym("해운대");
        user.setUserPhone("49034");

        guestRepository.save(user);

        user user2 = guestRepository.findByUserPhoneAndUserGym("49034","해운대");

        System.out.println(user2.getUserId());

        System.out.println(user2.getUserGym());
    }

    @Test
    void calender(){
        user user = new user();
        user.setUserGym("해운대");
        user.setUserPhone("49034");

        guestRepository.save(user);

        user userInfo = guestRepository.findByUserId(1L);

        List<UserCalendar> result = guestRepository
                .findByUserId(userInfo.getUserId())
                .getCalendarList();

        result.forEach(System.out::println);
    }


}

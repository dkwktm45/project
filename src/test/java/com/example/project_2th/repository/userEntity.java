package com.example.project_2th.repository;


import com.example.project_2th.entity.User;
import com.example.project_2th.entity.Calendar;
import com.example.project_2th.entity.Exercies;
import groovy.util.logging.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@SpringBootTest
@Transactional
@Slf4j
public class userEntity {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CalendarRepositroy calendarRepositroy;

    @Autowired
    ExinfoRepository exinfoRepository;

    @Autowired
    EntityManager em;

    @Test
    void login(){
        User user = User.builder().userName("김화순").userGym("해운대").userPhone("010-4903-4073").loginNumber("4073").build();


        userRepository.save(user);

        User user2 = userRepository.findByUserPhoneAndUserGym("49034","해운대");

        System.out.println(user2.getUserId());

        System.out.println(user2.getUserGym());
    }

    @Test
    void calender(){

        List<User> users = userRepository.findAllByFetchJoin();
        List<Calendar> calendars = users.get(0).getCalendarList();


        System.out.println(calendars.size());
        calendars.forEach(System.out::println);
        assertNotNull("Object is null",calendars );
    }
}

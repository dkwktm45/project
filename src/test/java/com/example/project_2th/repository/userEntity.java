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
        User user = new User();
        user.setUserGym("해운대");
        user.setUserPhone("49034");

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

    @Test
    void exinfo() {
        Date timestamp = Date.valueOf("2022-05-04");

        List<Exercies> result = userRepository
                .findByUserId(1L)
                .getExerciesList();

        result.forEach(System.out::println);


        List<Exercies> exinfoList = null;
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

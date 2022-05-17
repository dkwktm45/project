package com.example.project_2th.repository;

import com.example.project_2th.entity.User;
import com.example.project_2th.entity.UserExercies;
import com.fasterxml.jackson.databind.ObjectMapper;
import groovy.util.logging.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.io.DataInput;
import java.io.IOException;
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

    @DisplayName("기존 유저의 운동 정보에서 Cnt 값을 수정")
    @Test
    public void updateCnt() throws IOException, ParseException {
        UserExercies result = exinfoRepository.findByOne(1L,"체스트 플라이");

        result.setCnt("10");
        exinfoRepository.save(result);
    }

    @Test
    public void
}

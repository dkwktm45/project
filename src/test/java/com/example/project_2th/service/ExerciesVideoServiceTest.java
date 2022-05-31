package com.example.project_2th.service;


import com.example.project_2th.entity.ExerciesVideo;
import com.example.project_2th.entity.User;
import com.example.project_2th.repository.UserRepository;
import groovy.util.logging.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@Slf4j
public class ExerciesVideoServiceTest {

    @Autowired
    private ExerciesVideoService exerciesVideoService;

    @Autowired
    private UserRepository userRepository;

    @DisplayName("map 형태로 비디오 정보들을 담는다.(운동 기록, 및 자세)")
    @Transactional
    @Test
    void test1(){
        Long id = userRepository.findByUserId(1L).getExercieVideosList().get(0).getVideoSeq();
        Map<String , Object> map = exerciesVideoService.selectVideoInfo(id);
        System.out.println(map.size());
        assertEquals(2,map.size());
    }

    @DisplayName("user 정보와 date가 있은 것을 가져오시오")
    @Transactional
    @Test
    void test2() throws ParseException {
        User user =userRepository.findByUserId(1L);
        String from = "2022-05-16";
        List<ExerciesVideo> videoList = exerciesVideoService.dateList(user, Date.valueOf(from));
        videoList.forEach(System.out::println);
        assertNotNull(videoList);
    }
}

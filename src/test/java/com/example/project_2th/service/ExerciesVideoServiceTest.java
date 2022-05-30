package com.example.project_2th.service;


import com.example.project_2th.repository.UserRepository;
import groovy.util.logging.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.junit.Assert.assertEquals;

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
    }
}

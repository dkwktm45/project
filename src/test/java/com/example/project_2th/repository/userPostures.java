package com.example.project_2th.repository;

import groovy.util.logging.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
@Slf4j
public class userPostures {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExinfoRepository exinfoRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private PosturesRepository posturesRepository;

    @DisplayName("해당 번호에 해당하는 자세 정보를 가져온다.")
    @Test
    @Transactional
    public void insertEx(){

    }
}

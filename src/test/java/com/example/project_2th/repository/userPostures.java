package com.example.project_2th.repository;

import com.example.project_2th.entity.User;
import com.example.project_2th.entity.UserExercies;
import groovy.util.logging.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Slf4j
public class userPostures {

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private ExinfoRepository exinfoRepository;

    @Autowired
    private UserVideoRepository userVideoRepository;

    @Autowired
    private DeepPosturesRepository deepPosturesRepository;

    @DisplayName("해당 번호에 해당하는 자세 정보를 가져온다.")
    @Test
    @Transactional
    public void insertEx(){

    }
}

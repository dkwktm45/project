package com.example.project_2th.repository;

import com.example.project_2th.entity.UserExercieVideos;
import com.example.project_2th.entity.UserExercies;
import groovy.util.logging.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class userVideo {
    @Autowired
    public ExinfoRepository exinfoRepository;

    @Autowired
    public UserVideoRepository userVideoRepository;

    @DisplayName("exinfo 정보를 통해 video_seq를 가져온다.")
    @Test
    public void seq(){
        UserExercies userExercies= exinfoRepository.findByExSeq(10L);

        UserExercieVideos result = userVideoRepository.findByUserExercies(userExercies);
    }
}

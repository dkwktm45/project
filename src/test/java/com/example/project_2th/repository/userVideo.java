package com.example.project_2th.repository;

import com.example.project_2th.entity.UserExercieVideos;
import com.example.project_2th.entity.UserExercies;
import groovy.util.logging.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@Slf4j
public class userVideo {
    @Autowired
    public ExinfoRepository exinfoRepository;

    @Autowired
    public UserVideoRepository userVideoRepository;

    @Autowired
    public GuestRepository guestRepository;

    @DisplayName("exinfo 정보를 통해 video_seq를 가져온다.")
    @Test
    public void seq(){
        UserExercies userExercies= exinfoRepository.findByExSeq(10L);

        UserExercieVideos result = userVideoRepository.findByUserExercies(userExercies);
    }

    @DisplayName("유저의 videoList")
    @Test
    @Transactional
    public void videoList(){
        List<UserExercieVideos> videoList = guestRepository.findByUserId(1L).getExercieVideosList();

        videoList.forEach(System.out::println);
    }
}

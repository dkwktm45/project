package com.example.project_2th.repository;

import com.example.project_2th.entity.UserExercieVideos;
import com.example.project_2th.entity.UserExercies;
import com.example.project_2th.entity.UserPostures;
import groovy.util.logging.Slf4j;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.*;

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
    @DisplayName("해당 유저의 videoList의 운동 정보")
    @Test
    @Transactional
    public void videoListExinfo(){
        List<UserExercieVideos> videoList = guestRepository.findByUserId(1L).getExercieVideosList();
        List<UserExercies> userExerciesList = new ArrayList<>();
        for(int i = 0; i<videoList.size(); i++){
            userExerciesList.add(videoList.get(i).getUserExercies());
        }
        userExerciesList.forEach(System.out::println);
    }

    @DisplayName("video에 해당하는 자세교정 받은 정보를 가져온다.")
    @Test
    @Transactional
    public void videoPostures(){
        UserExercies userExercies = userVideoRepository.findByVideoSeq(81L).getUserExercies();
        List<UserPostures> userPosturesList = userVideoRepository.findByVideoSeq(81L).getUserPostures();
        assertTrue(userPosturesList.isEmpty());
        assertNotNull(userExercies);
    }


}

package com.example.project_2th.repository;

import com.example.project_2th.entity.ExerciesVideo;
import com.example.project_2th.entity.Exercies;
import com.example.project_2th.entity.Postures;
import groovy.util.logging.Slf4j;
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
    public VideoRepository videoRepository;

    @Autowired
    public UserRepository userRepository;

    @DisplayName("exinfo 정보를 통해 video_seq를 가져온다.")
    @Test
    public void seq(){
        Exercies exercies = exinfoRepository.findByExSeq(10L);

        ExerciesVideo result = videoRepository.findByExercies(exercies);


    }

    @DisplayName("유저의 videoList")
    @Test
    @Transactional
    public void videoList(){
        List<ExerciesVideo> videoList = userRepository.findByUserId(1L).getExercieVideosList();

        videoList.forEach(System.out::println);
    }
    @DisplayName("해당 유저의 videoList의 운동 정보")
    @Test
    @Transactional
    public void videoListExinfo(){
        List<ExerciesVideo> videoList = userRepository.findByUserId(1L).getExercieVideosList();
        List<Exercies> exerciesList = new ArrayList<>();
        for(int i = 0; i<videoList.size(); i++){
            exerciesList.add(videoList.get(i).getExercies());
        }
        exerciesList.forEach(System.out::println);
    }

    @DisplayName("video에 해당하는 자세교정 받은 정보를 가져온다.")
    @Test
    @Transactional
    public void videoPostures(){
        Exercies exercies = videoRepository.findByVideoSeq(81L).getExercies();
        List<Postures> posturesList = videoRepository.findByVideoSeq(81L).getPostures();
        assertTrue(posturesList.isEmpty());
        assertNotNull(exercies);
    }


}

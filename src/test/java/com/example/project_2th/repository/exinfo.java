package com.example.project_2th.repository;

import com.example.project_2th.entity.User;
import com.example.project_2th.entity.ExerciesVideo;
import com.example.project_2th.entity.Exercies;
import groovy.util.logging.Slf4j;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Slf4j
public class exinfo{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExinfoRepository exinfoRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private PosturesRepository posturesRepository;
    @Test
    @Transactional
    public void insertEx(){
        User user = userRepository.findByUserId(1L);
        Exercies exercies = new Exercies();

        System.out.println(Date.valueOf(LocalDate.now()));

        System.out.println("user : " + user.getUserId());
        exercies.setUser(user);
        exercies.setUserSet("1");
        exercies.setExCount("12");
        exercies.setExName("체스트 플라이");
        exercies.setExKinds("가슴");
        System.out.println("save 간다!!!!");

        exinfoRepository.save(exercies);
        List<Exercies> result = userRepository.findByUserId(1L).getExerciesList();
        result.forEach(System.out::println);
    }
    @Test
    public void insertExTest(){
        User user = userRepository.findByUserId(1L);
        Exercies exercies = new Exercies();

        List<Exercies> result = userRepository.findByUserId(1L).getExerciesList();

        result.forEach(System.out::println);
    }

    @DisplayName("기존 유저의 운동 정보에서 Cnt 값을 수정")
    @Test
    public void updateCnt() throws IOException, ParseException {
        Exercies result = exinfoRepository.findByOne(1L,"체스트 플라이");

        result.setCnt("10");
        exinfoRepository.save(result);
    }

    @DisplayName("기존 유저의 운동 정보에서 Cnt 값을 수정 and videofile 저장")
    @Test
    public void updateCntAndvideo() throws IOException, ParseException {
        User user = userRepository.findByUserId(1L);
        Exercies exercies = exinfoRepository.findByExSeq(67L);

        double randomValue = Math.random();
        String file_name = Double.toString((randomValue * 100) + 1);

        String cnt = "0";
        // video 파일 저장
        ExerciesVideo exerciesVideo = new ExerciesVideo();
        exerciesVideo.setUser(user);
        exerciesVideo.setFileName(file_name);
        exerciesVideo.setExercies(exercies);
        exercies.setCnt(cnt);

        videoRepository.save(exerciesVideo);
        // cnt 데이터 update
        exinfoRepository.save(exercies);

    }

    @DisplayName("기존 유저의 운동 정보에서 Cnt 값을 수정 and videofile 저장")
    @Test
    public void join(){
        User.builder().userName("이진영").userPhone("010-4903-4073").userGym("해운대").managerYn(0)
                .videoYn(1)
    }

}

package com.example.project_2th.repository;

import com.example.project_2th.entity.User;
import com.example.project_2th.entity.UserExercieVideos;
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
    private GuestRepository guestRepository;

    @Autowired
    private ExinfoRepository exinfoRepository;

    @Autowired
    private UserVideoRepository userVideoRepository;

    @Autowired
    private DeepPosturesRepository deepPosturesRepository;
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

    @DisplayName("기존 유저의 운동 정보에서 Cnt 값을 수정 and videofile 저장")
    @Test
    public void updateCntAndvideo() throws IOException, ParseException {
        User user = guestRepository.findByUserId(1L);
        UserExercies userExercies = exinfoRepository.findByExSeq(67L);

        double randomValue = Math.random();
        String file_name = Double.toString((randomValue * 100) + 1);

        String cnt = "0";
        // video 파일 저장
        UserExercieVideos userExercieVideos = new UserExercieVideos();
        userExercieVideos.setUser(user);
        userExercieVideos.setFileName(file_name);
        userExercieVideos.setUserExercies(userExercies);
        userExercies.setCnt(cnt);

        userVideoRepository.save(userExercieVideos);
        // cnt 데이터 update
        exinfoRepository.save(userExercies);

    }

}

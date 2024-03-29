package com.example.project_2th.controller.helper;

import com.example.project_2th.entity.*;
import lombok.RequiredArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserHelper {

    Long id = 0L;
    public static User makeUser(){
        return User.builder().userName("김화순").loginNumber("1234").userPhone("010-2345-1234")
                .userBirthdate(LocalDate.parse("1963-07-16")).userExpireDate(LocalDate.parse("2022-08-20"))
                .managerYn(0).videoYn(1).userGym("해운대").role("USER").build();
    }

    public User userCalendar(){
        return User.builder().exercieVideosList(makeVideos()).exerciesList(makeExinfos()).userName("김화순").loginNumber("1234").userPhone("010-2345-1234")
                .userBirthdate(LocalDate.parse("1963-07-16")).userExpireDate(LocalDate.parse("2022-08-20"))
                .managerYn(0).videoYn(1).userGym("해운대").build();
    }


    public List<User> makeUsers(){
        List<User> users = new ArrayList<>();
        users.add(User.builder().userName("김화순").userPhone("9696")
                .userBirthdate(LocalDate.parse("1963-07-16")).userExpireDate(LocalDate.parse("2022-08-20"))
                .managerYn(0).exercieVideosList(makeVideos()).videoYn(1).userGym("해운대").build());

        users.add(User.builder().userId(makeId()).userName("김화순").userPhone("9696")
                .userBirthdate(LocalDate.parse("1963-07-16")).userExpireDate(LocalDate.parse("2022-08-20"))
                .managerYn(0).videoYn(1).userGym("해운대").build());

        users.add(User.builder().userId(makeId()).userName("김화순").userPhone("9696")
                .userBirthdate(LocalDate.parse("1963-07-16")).userExpireDate(LocalDate.parse("2022-08-20"))
                .managerYn(0).videoYn(1).userGym("해운대").build());
        return users;
    }
    public Exercies makeExercies(){
        return Exercies.builder().exSeq(makeId()).exName("체스트 플라이").exCount("12")
                .userSet("4").exKinds("가슴").user(makeUser()).cnt("10").build();
    }

    public ExerciesVideo makeVideo(){
        return ExerciesVideo.builder().videoSeq(1L).videoSeq(makeId()).videoDate(LocalDate.parse("2022-10-10"))
                .user(makeUser()).exercies(makeExercies()).postures(makePose()).fileName("test").build();
    }

    public List<Postures> makePose(){
        List<Postures> postures = new ArrayList<>();
        postures.add(Postures.builder().poseResult("굿").aiComment("잘못된 자세 x")
                .build());
        postures.add(Postures.builder().poseResult("굿").aiComment("잘못된 자세 x")
                .build());
        postures.add(Postures.builder().poseResult("굿").aiComment("잘못된 자세 x")
                .build());
        return postures;
    }

    public Map<String ,Object> mapToObject(User user){
        Map<String ,Object> objectMap = new HashMap<>();
        objectMap.put("user",user);
        return objectMap;
    }

    public Map<String , Object> makeAdmin(){
        User user =  User.builder().userName("김화순").loginNumber("9696").userPhone("010-1234-5678")
                .userBirthdate(LocalDate.parse("1963-07-16")).userExpireDate(LocalDate.parse("2022-08-20"))
                .managerYn(1).exerciesList(makeExinfos()).exercieVideosList(makeVideos())
                .role("ROLE_ADMIN").videoYn(1).userGym("해운대").build();

        List<User> users = new ArrayList<>();

        users.add(user);
        users.add(user);
        users.add(user);
        users.add(user);

        Map<String ,Object> objectMap = new HashMap<>();
        objectMap.put("user",user);
        objectMap.put("userList",users);

        return objectMap;
    }

    public List<ExerciesVideo> makeVideos(){
        Exercies exinfo = Exercies.builder().exDay(LocalDate.parse("2022-06-15")).exName("체스트 플라이").exCount("12")
                .userSet("4").exKinds("가슴").user(makeUser()).cnt("10").build();

        ExerciesVideo video = ExerciesVideo.builder().exercies(makeExercies()).fileName("test").videoDate(LocalDate.parse("2022-06-15"))
                .user(makeUser()).exercies(exinfo).build();
        List<ExerciesVideo> videoList = new ArrayList<>();
        videoList.add(video);
        videoList.add(video);
        videoList.add(video);
        return videoList;
    }
    public List<Exercies> makeExinfos(){
        Exercies exinfo = Exercies.builder().exDay(LocalDate.parse("2022-06-15")).exName("체스트 플라이").exCount("12")
                .userSet("4").exKinds("가슴").user(makeUser()).cnt("10").build();
        List<Exercies> exercies = new ArrayList<>();
        exercies.add(exinfo);
        exercies.add(exinfo);
        exercies.add(exinfo);
        return exercies;
    }
    public Long makeId(){
        id++;
        return id;
    }


}

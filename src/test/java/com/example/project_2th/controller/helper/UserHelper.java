package com.example.project_2th.controller.helper;

import com.example.project_2th.entity.*;
import lombok.RequiredArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserHelper {


    public static User makeUser(){
        return User.builder().userName("김화순").loginNumber("1234").userPhone("010-2345-1234")
                .userBirthdate(java.sql.Date.valueOf("1963-07-16")).userExpireDate(java.sql.Date.valueOf("2022-08-20"))
                .managerYn(0).videoYn(1).userGym("해운대").build();
    }

    public static List<User> makeUsers(){
        List<User> users = new ArrayList<>();
        users.add(User.builder().userId(1L).userName("김화순").userPhone("9696")
                .userBirthdate(java.sql.Date.valueOf("1963-07-16")).userExpireDate(java.sql.Date.valueOf("2022-08-20"))
                .managerYn(0).calendarList(makeCalendars()).exercieVideosList(makeVideos(makeUser())).videoYn(1).userGym("해운대").build());

        users.add(User.builder().userId(2L).userName("김화순").userPhone("9696")
                .userBirthdate(java.sql.Date.valueOf("1963-07-16")).userExpireDate(java.sql.Date.valueOf("2022-08-20"))
                .managerYn(0).calendarList(makeCalendars()).videoYn(1).userGym("해운대").build());

        users.add(User.builder().userId(3L).userName("김화순").userPhone("9696")
                .userBirthdate(java.sql.Date.valueOf("1963-07-16")).userExpireDate(java.sql.Date.valueOf("2022-08-20"))
                .managerYn(0).calendarList(makeCalendars()).videoYn(1).userGym("해운대").build());
        return users;
    }
    public static Exercies makeExercies(){
        return Exercies.builder().exSeq(1L).exName("체스트 플라이").exCount("12")
                .userSet("4").exKinds("가슴").user(makeUser()).cnt("10").build();
    }
    public static Calendar makeCalendar(){
        return Calendar.builder().userWeight("80").exDay(Date.valueOf("2022-10-11"))
                .timeDiff("30").user(makeUser()).build();
    }

    public static List<Calendar> makeCalendars(){
        List<Calendar> calendarList = new ArrayList<>();
        calendarList.add(makeCalendar());
        calendarList.add(makeCalendar());
        calendarList.add(makeCalendar());
        calendarList.add(makeCalendar());
        return calendarList;
    }

    public ExerciesVideo makeVideo(){
        return ExerciesVideo.builder().videoSeq(1L).videoDate(Date.valueOf("2022-10-10"))
                .user(makeUser()).exercies(makeExercies()).postures(makePose()).fileName("test").build();
    }

    public List<Postures> makePose(){
        List<Postures> postures = new ArrayList<>();
        postures.add(Postures.builder().postureSeq(1L).videoTime(10).poseResult("굿").aiComment("잘못된 자세 x")
                .build());
        postures.add(Postures.builder().postureSeq(2L).videoTime(10).poseResult("굿").aiComment("잘못된 자세 x")
                .build());
        postures.add(Postures.builder().postureSeq(3L).videoTime(10).poseResult("굿").aiComment("잘못된 자세 x")
                .build());
        return postures;
    }

    public static Map<String ,Object> mapToObject(User user){
        Map<String ,Object> objectMap = new HashMap<>();
        objectMap.put("user",user);
        return objectMap;
    }

    public static Map<String , Object> makeAdmin(){
        User user =  User.builder().userName("김화순").userPhone("9696")
                .userBirthdate(java.sql.Date.valueOf("1963-07-16")).userExpireDate(java.sql.Date.valueOf("2022-08-20"))
                .managerYn(1).videoYn(1).userGym("해운대").build();

        List<User> users = new ArrayList<>();

        users.add(makeUser());
        users.add(makeUser());
        users.add(makeUser());
        users.add(makeUser());

        Map<String ,Object> objectMap = new HashMap<>();
        objectMap.put("user",user);
        objectMap.put("userList",users);

        return objectMap;
    }

    public static List<ExerciesVideo> makeVideos(User user){
        Exercies exinfo = Exercies.builder().exDay(Date.valueOf("2022-06-15")).exName("체스트 플라이").exCount("12")
                .userSet("4").exKinds("가슴").user(user).cnt("10").build();

        ExerciesVideo video = ExerciesVideo.builder().exercies(makeExercies()).fileName("test").videoDate(Date.valueOf("2022-06-15"))
                .user(user).exercies(exinfo).build();
        List<ExerciesVideo> videoList = new ArrayList<>();
        videoList.add(video);
        videoList.add(video);
        videoList.add(video);
        return videoList;
    }
    public static List<Exercies> makeExinfos(User user){
        Exercies exinfo = Exercies.builder().exDay(Date.valueOf("2022-06-15")).exName("체스트 플라이").exCount("12")
                .userSet("4").exKinds("가슴").user(user).cnt("10").build();
        List<Exercies> exercies = new ArrayList<>();
        exercies.add(exinfo);
        exercies.add(exinfo);
        exercies.add(exinfo);
        return exercies;
    }

}

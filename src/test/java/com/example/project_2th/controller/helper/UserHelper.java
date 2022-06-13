package com.example.project_2th.controller.helper;

import com.example.project_2th.entity.Exercies;
import com.example.project_2th.entity.ExerciesVideo;
import com.example.project_2th.entity.User;
import lombok.RequiredArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class UserHelper {

    private final User user;

    public static User makeUser(){
        return User.builder().userId(1L).userName("김화순").userPhone("9696")
                .userBirthdate(java.sql.Date.valueOf("1963-07-16")).userExpireDate(java.sql.Date.valueOf("2022-08-20"))
                .managerYn(0).videoYn(1).userGym("해운대").build();
    }
    public static Exercies makeExercies(){
        return Exercies.builder().exName("체스트 플라이").exCount("12")
                .userSet("4").exKinds("가슴").user(makeUser()).cnt("10").build();
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

        ExerciesVideo video = ExerciesVideo.builder().fileName("test").videoDate(Date.valueOf("2022-06-15"))
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

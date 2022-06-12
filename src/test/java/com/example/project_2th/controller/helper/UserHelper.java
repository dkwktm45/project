package com.example.project_2th.controller.helper;

import com.example.project_2th.entity.User;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class UserHelper {

    public User makeUser(){
        return User.builder().userName("김화순").userPhone("9696")
                .userBirthdate(java.sql.Date.valueOf("1963-07-16")).userExpireDate(java.sql.Date.valueOf("2022-08-20"))
                .managerYn(0).videoYn(1).userGym("해운대").build();
    }

    public Map<String ,Object> mapToObject(User user){
        Map<String ,Object> objectMap = new HashMap<>();
        objectMap.put("user",user);
        return objectMap;
    }

    public Map<String , Object> makeAdmin(){
        User admin =  User.builder().userName("김화순").userPhone("9696")
                .userBirthdate(java.sql.Date.valueOf("1963-07-16")).userExpireDate(java.sql.Date.valueOf("2022-08-20"))
                .managerYn(1).videoYn(1).userGym("해운대").build();

        List<User> users = new ArrayList<>();

        users.add(makeUser());
        users.add(makeUser());
        users.add(makeUser());
        users.add(makeUser());

        Map<String ,Object> objectMap = new HashMap<>();
        objectMap.put("user",admin);
        objectMap.put("userList",users);

        return objectMap;
    }
}

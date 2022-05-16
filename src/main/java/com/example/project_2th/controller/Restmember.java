package com.example.project_2th.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.project_2th.entity.UserExercies;
import com.example.project_2th.repository.ExinfoRepository;
import com.example.project_2th.repository.GuestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j
public class Restmember {
    @Autowired
    private final GuestRepository guestRepository;

    @Autowired
    private final ExinfoRepository exinfoRepository;

    Date day;

    @GetMapping(value = "/calendarView")
    public List<UserExercies> calendarView(String userId, Date exDay, HttpServletRequest req, HttpServletResponse res) throws Exception {

        List<UserExercies> result = guestRepository
                .findByUserId(Long.valueOf(userId))
                .getUserExerciesList();

        List<UserExercies> exinfoList = null;
        Date day = null;
        for(int i = 0; i < result.size(); i++){
            day = result.get(i).getExDay();
            if (day.equals(exDay)) {
                System.out.println(day);
                day = exDay;
                break;
            }
        }

        exinfoList = exinfoRepository.findByExDay(day);


        return exinfoList;

    }
}



















package com.example.project_2th.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.project_2th.entity.UserExercies;
import com.example.project_2th.repository.ExinfoRepository;
import com.example.project_2th.repository.GuestRepository;
import com.example.project_2th.repository.UserVideoRepository;
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
    @GetMapping(value = "insertExURL")
    public String inertURL(HttpServletRequest req)throws Exception {
        System.out.println("저장할려는중");
        //System.out.println(request.getParameter("cnt"));
        String cnt = req.getParameter("cnt");
        String user_id = req.getParameter("userId");

        int ex_seq = Integer.parseInt(req.getParameter("exSeq"));

        //System.out.println(ex_seq);
        //System.out.println(user_id);
        ServletInputStream input = req.getInputStream();


        double randomValue = Math.random();
        String file_name = Double.toString((randomValue * 100) + 1);
        FileOutputStream out = new FileOutputStream(new File("C:\\user\\projectVideo\\" + file_name + ".webm"));


        byte[] charBuffer = new byte[128];

        int bytesRead = -1;
        while ((bytesRead = input.read(charBuffer)) > 0) {
            //System.out.println("저장중");
            out.write(charBuffer, 0, bytesRead);
        }

        input.close();
        out.close();

        System.out.println("저장 끝");
        //exinfoRepository.inertCNT(cnt , ex_seq);
        //UserVideoRepository.insertURL(user_id,ex_seq,file_name);
        return "main.do";
    }
}



















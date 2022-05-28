package com.example.project_2th.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import com.example.project_2th.entity.*;
import com.example.project_2th.repository.PosturesRepository;
import com.example.project_2th.repository.ExinfoRepository;
import com.example.project_2th.repository.UserRepository;
import com.example.project_2th.repository.VideoRepository;
import com.example.project_2th.service.ExerciesService;
import com.example.project_2th.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@org.springframework.web.bind.annotation.RestController
@RequiredArgsConstructor
@Slf4j
public class RestController {

    @Autowired
    private final ExerciesService exerciesService;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final ExinfoRepository exinfoRepository;

    @Autowired
    private final VideoRepository videoRepository;

    @Autowired
    private final PosturesRepository posturesRepository;

    @Autowired
    private final UserService userService;

    @GetMapping(value = "/calendarView")
    public List<Exercies> calendarView(@ModelAttribute Calendar calendar, HttpServletRequest req, HttpServletResponse res) throws Exception {
        List<Exercies> exinfoList = exerciesService.calendarExinfo(calendar);

        return exinfoList;
    }
    /*
    @GetMapping(value = "/infoCalender")
    public List<UserCalendar> infoCalender(HttpServletRequest req , Model model) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");


        ModelAndView mav = new ModelAndView();

        List<User> users = guestRepository.findAllByFetchJoin();
        List<UserCalendar> exinfo = users.get(Math.toIntExact(user.getUserId())-1).getCalendarList();


        return exinfo;
    }

*/
    @Transactional
    @PostMapping(value = "insertExURL")
    public String insertExURL(HttpServletRequest req) throws Exception {
        System.out.println("저장할려는중");
        //System.out.println(request.getParameter("cnt"));
        String cnt = req.getParameter("cnt");
        Long user_id = Long.valueOf(req.getParameter("userId"));
        Long ex_seq = Long.valueOf(req.getParameter("exSeq"));

        User user = userRepository.findByUserId(user_id);
        Exercies exercies = exinfoRepository.findByExSeq(ex_seq);

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

        // video 파일 저장
        ExerciesVideo exerciesVideo = new ExerciesVideo();
        exerciesVideo.setUser(user);
        exerciesVideo.setFileName(file_name);
        exerciesVideo.setExercies(exercies);

        videoRepository.save(exerciesVideo);

        // cnt 데이터 update
        exercies.setCnt(cnt);
        exinfoRepository.save(exercies);

        return "main";
    }

    @GetMapping(value = "/insertPose")
    @ResponseBody
    @Transactional
    public Map<String, Object> getVideoinfo(HttpServletRequest req) throws Exception {

        try{
            Long Longseq = Long.valueOf(req.getParameter("videoSeq"));
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("exinfo", videoRepository.findByVideoSeq(Longseq).getExercies());
            map.put("postures", videoRepository.findByVideoSeq(Longseq).getPostures());
            return map;
        }catch (IllegalStateException e ){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @RequestMapping(value = "/insertBadImage.do", method = {RequestMethod.GET, RequestMethod.POST})
    public void insertBadImage(HttpServletRequest request) throws Exception {

        //String fileName = file.getOriginalFilename();
        //System.out.print(request.getParameter("data"));
        System.out.println(request.getParameter("ai_comment"));

        String ai_comment = request.getParameter("ai_comment");

        Long ex_seq = Long.valueOf(request.getParameter("ex_seq"));

        Exercies exercies = exinfoRepository.findByExSeq(ex_seq);

        ExerciesVideo result = videoRepository.findByExercies(exercies);
        //ServletInputStream input = request.getInputStream();


        double randomValue = Math.random();
        String pose_result = Double.toString((randomValue * 100) + 1);
        FileOutputStream out = new FileOutputStream(new File("C:\\user\\badImage\\" + pose_result + ".jpg"));

        byte[] charBuffer = new byte[128];

        int bytesRead = 0;
        while ((bytesRead = System.in.read()) != -1) {
            //System.out.println("저장중");
            out.write(bytesRead);
        }

        Postures postures = new Postures();
        postures.setPoseResult(pose_result);
        postures.setAiComment(ai_comment);
        postures.setExerciesVideo(result);

        posturesRepository.save(postures);
    }


}



















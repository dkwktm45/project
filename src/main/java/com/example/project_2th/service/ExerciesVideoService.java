package com.example.project_2th.service;


import com.example.project_2th.entity.Exercies;
import com.example.project_2th.entity.ExerciesVideo;
import com.example.project_2th.entity.Postures;
import com.example.project_2th.entity.User;
import com.example.project_2th.repository.ExinfoRepository;
import com.example.project_2th.repository.PosturesRepository;
import com.example.project_2th.repository.UserRepository;
import com.example.project_2th.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.sql.Date;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ExerciesVideoService {

    private final UserRepository userRepository;

    private final ExinfoRepository exinfoRepository;

    private final VideoRepository videoRepository;

    public void videoSave(String cnt, Long user_id,Long ex_seq, ServletInputStream input) throws IOException {
        User user = userRepository.findByUserId(user_id);
        Exercies exercies = exinfoRepository.findByExSeq(ex_seq);

        UUID uuid = UUID.randomUUID();
        String file_name = uuid.toString() + "_" +exercies.getExName();
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
    }

    public Map<String, Object> selectVideoInfo(Long videoSeq){
        try{
            ExerciesVideo videoInfo = videoRepository.findByVideoSeq(videoSeq);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("exinfo", videoInfo.getExercies());
            map.put("postures", videoInfo.getPostures());

            return map;

        }catch (IllegalStateException e ){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<ExerciesVideo> dateList(User user, Date videoDate){
        return videoRepository.findByVideoDate(user.getUserId(),
                videoDate);
    }
}

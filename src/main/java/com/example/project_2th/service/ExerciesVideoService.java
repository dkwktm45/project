package com.example.project_2th.service;


import com.example.project_2th.exception.PostNotFound;
import com.example.project_2th.entity.Exercies;
import com.example.project_2th.entity.ExerciesVideo;
import com.example.project_2th.entity.User;
import com.example.project_2th.repository.ExinfoRepository;
import com.example.project_2th.repository.UserRepository;
import com.example.project_2th.repository.VideoRepository;
import com.example.project_2th.response.UserResponse;
import com.example.project_2th.response.VideoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.sql.Date;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ExerciesVideoService {

    private final UserRepository userRepository;

    private final ExinfoRepository exinfoRepository;

    private final VideoRepository videoRepository;
    private final Logger logger = LoggerFactory.getLogger(ExerciesVideoService.class);
    // 주의!!
    public List<VideoResponse> loadUser(User user){
        String role = "ROLE_USER";
        return videoRepository.findUserVideos(user.getUserGym(), role)
                .orElseThrow(PostNotFound::new)
                .stream()
                .map(VideoResponse::new).collect(Collectors.toList());
    }

    public void videoSave(String cnt, Long user_id, Long ex_seq, ServletInputStream input) throws IOException {
        logger.info("videoSave perform");
        Optional<User> user = userRepository.findByUserId(user_id);
        Exercies exercies = exinfoRepository.findByExSeq(ex_seq);

        UUID uuid = UUID.randomUUID();
        String file_name = uuid.toString() + "_" + exercies.getExName();
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
        ExerciesVideo exerciesVideo = ExerciesVideo.builder()
                .user(user.get())
                .fileName(file_name)
                .exercies(exercies)
                .videoDate(Date.valueOf(LocalDate.now()))
                .build();
        logger.info("videoSave info : {}", exerciesVideo);



        logger.info("cnt update : {}", cnt);
        // cnt 데이터 update
        exercies.setCnt(cnt);

    }

    public Map<String, Object> selectVideoInfo(Long videoSeq) {
        VideoResponse result = new VideoResponse(videoRepository.findByVideoSeq(videoSeq).orElseThrow(PostNotFound::new));
        Map<String, Object> map = Map.of(
                "exinfo",result.getExercies(),
                "postures",result.getPostures()
        );
        return map;
    }


}

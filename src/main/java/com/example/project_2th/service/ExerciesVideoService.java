package com.example.project_2th.service;


import com.example.project_2th.entity.Exercies;
import com.example.project_2th.entity.ExerciesVideo;
import com.example.project_2th.entity.User;
import com.example.project_2th.exception.PostNotFound;
import com.example.project_2th.repository.ExinfoRepository;
import com.example.project_2th.repository.PosturesRepository;
import com.example.project_2th.repository.UserRepository;
import com.example.project_2th.repository.VideoRepository;
import com.example.project_2th.response.ExerciesResponse;
import com.example.project_2th.response.VideoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.servlet.ServletInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ExerciesVideoService {
    private final PosturesRepository posturesRepository;
    private final VideoRepository videoRepository;
    private final Logger logger = LoggerFactory.getLogger(ExerciesVideoService.class);
    // 주의!!
    public List<VideoResponse> loadUser(User user){
        logger.info("loadUser perform");
        String role = "ROLE_USER";
        return videoRepository.findUserVideos(user.getUserGym(), role)
                .orElseThrow(PostNotFound::new)
                .stream()
                .map(VideoResponse::new).collect(Collectors.toList());
    }

    public List<VideoResponse> infoVideo(User user) {
        logger.info("infoVideo perform");
        return videoRepository.findByUser(user).orElseThrow(PostNotFound::new)
                .stream().map(VideoResponse::new).collect(Collectors.toList());
    }
    public ExerciesVideo Video(Exercies exercies) {
        logger.info("infoVideo perform");
        return ExerciesVideo.builder()
                .user(exercies.getUser())
                .exercies(exercies)
                .videoDate(LocalDate.now())
                .build();
    }
    public void videoUpdate(String cnt,ExerciesVideo exerciesVideo, ServletInputStream input) throws IOException {
        logger.info("videoUpdate perform");

        UUID uuid = UUID.randomUUID();
        String file_name = uuid.toString() + "_" + changeVideoName(exerciesVideo.getExercies().getExName());
        byte[] charBuffer = null;
        int bytesRead = -1;
        try(FileOutputStream out = new FileOutputStream(new File("C:\\user\\projectVideo\\" + file_name + ".webm"));){

            charBuffer = new byte[128];
            while ((bytesRead = input.read(charBuffer)) > 0) {
                out.write(charBuffer, 0, bytesRead);
            }

            input.close();
        }catch (Exception e){
            logger.warn(e.getMessage());
        }

        // video 파일 저장
        exerciesVideo.setFileName(file_name);
        exerciesVideo.getExercies().setCnt(cnt);
        videoRepository.save(exerciesVideo);
        posturesRepository.saveAll(exerciesVideo.getPostures());
        logger.info("cnt update : {}", cnt);
    }

    public String changeVideoName(String value){
        if (value.equals("체스트 플라이")){
            return "chestFlyer";
        }else if(value.equals("시티드 로우")){
            return "seated row";
        }else if(value.equals("덤벨 숄더프레스")){
            return "shoulderPress";
        }
        throw new NullPointerException("값이 담겨있지 않습니다.");
    }


}

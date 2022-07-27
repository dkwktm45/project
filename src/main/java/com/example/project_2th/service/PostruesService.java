package com.example.project_2th.service;


import com.example.project_2th.entity.Exercies;
import com.example.project_2th.entity.ExerciesVideo;
import com.example.project_2th.entity.Postures;
import com.example.project_2th.exception.PostNotFound;
import com.example.project_2th.repository.ExinfoRepository;
import com.example.project_2th.repository.PosturesRepository;
import com.example.project_2th.repository.VideoRepository;
import com.example.project_2th.response.PoseResponse;
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
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PostruesService {

    private final ExinfoRepository exinfoRepository;

    private final VideoRepository videoRepository;

    private final PosturesRepository posturesRepository;
    private final Logger logger = LoggerFactory.getLogger(PostruesService.class);

    public List<PoseResponse> selectVideoInfo(ExerciesVideo exerciesVideo) {
        logger.info("selectVideo perform");
        return posturesRepository.findByExerciesVideo(exerciesVideo)
                .orElseThrow(PostNotFound::new).stream().map(PoseResponse::new)
                .collect(Collectors.toList());
    }

    public void badeImage(String ai_comment, Long ex_seq) throws IOException {
        Exercies exercies = exinfoRepository.findByExSeq(ex_seq);

        ExerciesVideo result = videoRepository.findByExercies(exercies);

        String pose_result = uploadFile(exercies.getExName());

        ServletInputStream input = null;

        byte[] charBuffer = null;
        int bytesRead = -1;
        try(FileOutputStream out = new FileOutputStream(new File("C:\\user\\badImage\\" + pose_result + ".jpg"));){


            charBuffer = new byte[128];

            while ((bytesRead = input.read(charBuffer)) > 0) {
                out.write(charBuffer, 0, bytesRead);
            }

            input.close();
        }catch (Exception e){
            logger.warn(e.getMessage());
        }

        Postures postures = new Postures();
        postures.setPoseResult(pose_result);
        postures.setAiComment(ai_comment);
        postures.setExerciesVideo(result);

        posturesRepository.save(postures);
    }

    private String uploadFile(String exName) {
        UUID uuid = UUID.randomUUID();
        String saveName = uuid.toString() + "_" + exName;
        return saveName;
    }
}

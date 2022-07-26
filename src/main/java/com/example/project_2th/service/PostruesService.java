package com.example.project_2th.service;


import com.example.project_2th.entity.Exercies;
import com.example.project_2th.entity.ExerciesVideo;
import com.example.project_2th.entity.Postures;
import com.example.project_2th.exception.PostNotFound;
import com.example.project_2th.repository.ExinfoRepository;
import com.example.project_2th.repository.PosturesRepository;
import com.example.project_2th.repository.VideoRepository;
import com.example.project_2th.response.PoseResponse;
import com.example.project_2th.response.VideoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
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

    public List<PoseResponse> selectVideoInfo(ExerciesVideo exerciesVideo) {
        List<PoseResponse> result= posturesRepository.findByExerciesVideo(exerciesVideo)
                .orElseThrow(PostNotFound::new).stream().map(PoseResponse::new)
                .collect(Collectors.toList());
        return result;
    }

    public void badeImage(String ai_comment, Long ex_seq) throws IOException {
        Exercies exercies = exinfoRepository.findByExSeq(ex_seq);

        ExerciesVideo result = videoRepository.findByExercies(exercies);

        String pose_result = uploadFile(exercies.getExName());
        FileOutputStream out = new FileOutputStream(new File("C:\\user\\badImage\\" + pose_result + ".jpg"));

        byte[] charBuffer = new byte[128];

        int bytesRead = 0;
        while ((bytesRead = System.in.read()) != -1) {
            log.info("파일 저장중");
            out.write(bytesRead);
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

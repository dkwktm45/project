package com.example.project_2th.service;


import com.example.project_2th.entity.Exercies;
import com.example.project_2th.entity.ExerciesVideo;
import com.example.project_2th.entity.Postures;
import com.example.project_2th.entity.User;
import com.example.project_2th.exception.PostNotFound;
import com.example.project_2th.repository.ExinfoRepository;
import com.example.project_2th.repository.PosturesRepository;
import com.example.project_2th.repository.VideoRepository;
import com.example.project_2th.response.ExerciesResponse;
import com.example.project_2th.response.PoseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    public void badeImage(ExerciesVideo video, List<String> aiComment, List<MultipartFile> files) throws IOException {

        videoRepository.save(video);
        List<Postures> posturesList = new ArrayList<>();
        UUID uuid = UUID.randomUUID();
        for (int i = 0; i< files.size(); i++) {
            String file_name = uuid.toString();
            File dest = new File("C:\\user\\projectVideo\\" + file_name +".webp");
            files.get(i).transferTo(dest);
            posturesList.add(
                    Postures.builder().poseResult(file_name).aiComment(aiComment.get(i)).build()
            );
        }

        posturesRepository.saveAll(posturesList);
    }

    private String uploadFile(String exName) {
        UUID uuid = UUID.randomUUID();
        String saveName = uuid.toString() + "_" + exName;
        return saveName;
    }
}

package com.example.project_2th.repository;

import com.example.project_2th.entity.ExerciesVideo;
import com.example.project_2th.entity.Exercies;
import com.example.project_2th.entity.Postures;
import com.example.project_2th.entity.User;
import com.example.project_2th.service.UserService;
import groovy.util.logging.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.*;

@SpringBootTest
@Slf4j
public class userVideo {
    @Autowired
    public ExinfoRepository exinfoRepository;

    @Autowired
    public VideoRepository videoRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    public UserService userService;

    @DisplayName("exinfo 정보를 통해 video_seq를 가져온다.")
    @Test
    public void seq(){
        Exercies exercies = exinfoRepository.findByExSeq(10L);

        ExerciesVideo result = videoRepository.findByExercies(exercies);


    }

    @DisplayName("유저의 videoList")
    @Test
    @Transactional
    public void videoList(){
        List<ExerciesVideo> videoList = userRepository.findByUserId(1L).getExercieVideosList();

        videoList.forEach(System.out::println);
    }
    @DisplayName("해당 유저의 videoList의 운동 정보")
    @Test
    @Transactional
    public void videoListExinfo(){
        List<ExerciesVideo> videoList = userRepository.findByUserId(1L).getExercieVideosList();
        List<Exercies> exerciesList = new ArrayList<>();
        for(int i = 0; i<videoList.size(); i++){
            exerciesList.add(videoList.get(i).getExercies());
        }
        exerciesList.forEach(System.out::println);
    }

    @DisplayName("video에 해당하는 자세교정 받은 정보를 가져온다.")
    @Test
    @Transactional
    public void videoPostures(){

        ExerciesVideo exercies = videoRepository.findByVideoSeq(81L);

        assertNotNull(exercies.getExercies());
        try {
            assertNotNull(exercies.getPostures());
        }catch (Exception e){
            System.out.println(e.getMessage() + " : null 일 수도 있습니다.");
        }
    }

    @DisplayName("비디오 파일을 저장한다.")
    @Test
    @Transactional
    public void test1(){
        User user = User.builder().userName("김화순").userPhone("9696")
                .userBirthdate(java.sql.Date.valueOf("1963-07-16")).userExpireDate(java.sql.Date.valueOf("2022-08-20"))
                .managerYn(0).videoYn(1).userGym("해운대").build();

        Exercies exercies = new Exercies();
        exercies.setExCount("12");
        exercies.setExKinds("가슴");
        exercies.setExName("체스트 플라이");
        exercies.setUserSet("4");
        exercies.setUser(user);
        UUID uuid = UUID.randomUUID();
        String file_name = uuid.toString() + "_" +exercies.getExName();

        em.persist(exercies);

        // video 파일 저장
        ExerciesVideo exerciesVideo = new ExerciesVideo();
        exerciesVideo.setUser(user);
        exerciesVideo.setFileName(file_name);
        exerciesVideo.setExercies(exercies);

        em.persist(exerciesVideo);

        System.out.println(exinfoRepository.findByExSeq(86L));
        em.close();

    }


}

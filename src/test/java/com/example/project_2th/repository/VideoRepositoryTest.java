package com.example.project_2th.repository;

import com.example.project_2th.config.TestDatasourceConfig;
import com.example.project_2th.controller.helper.UserHelper;
import com.example.project_2th.entity.Exercies;
import com.example.project_2th.entity.ExerciesVideo;
import com.example.project_2th.entity.Postures;
import com.example.project_2th.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestDatasourceConfig.class)
class VideoRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private VideoRepository videoRepository;

    protected UserHelper userHelper;
    private Logger logger = LoggerFactory.getLogger(VideoRepositoryTest.class);

    private User user;

    private Exercies exercies;

    private List<Postures> postures;

    @BeforeEach
    void setUp() {
        userHelper = new UserHelper();
        user = userHelper.userCalendar();
        testEntityManager.persist(user);
        user = testEntityManager.find(User.class,1L);
        exercies = Exercies.builder().exDay(Date.valueOf("2022-06-15")).exName("체스트 플라이").exCount("12")
                .userSet("4").exKinds("가슴").user(user).cnt("10").build();
        testEntityManager.persist(exercies);
    }

    @Test
    void findByExercies() {
        logger.info("given");
        exercies = testEntityManager.find(Exercies.class,1L);
        ExerciesVideo exerciesVideo = ExerciesVideo.builder().exercies(exercies).fileName("test").videoDate(Date.valueOf("2022-06-15"))
                .user(user).build();
        testEntityManager.persist(exerciesVideo);

        logger.info("when");
        ExerciesVideo result = videoRepository.findByExercies(exercies);

        logger.info("then");
        assertEquals(exercies,result.getExercies());
    }

    @Test
    void findByVideoSeq() {
        logger.info("given");
        exercies = testEntityManager.find(Exercies.class,1L);
        postures = userHelper.makePose();

        ExerciesVideo exerciesVideo = ExerciesVideo.builder().postures(postures).exercies(exercies).fileName("test").videoDate(Date.valueOf("2022-06-15"))
                .user(user).build();
        testEntityManager.persist(exerciesVideo);

        logger.info("when");
        ExerciesVideo result = videoRepository.findByVideoSeq(1L);

        logger.info("then");
        assertNotNull(result.getExercies());
        assertNotNull(result.getPostures());
    }

    @Test
    void findAll() {
        logger.info("given");
        testEntityManager.persist(ExerciesVideo.builder().fileName("test1").videoDate(Date.valueOf("2022-06-15"))
                .build());
        testEntityManager.persist(ExerciesVideo.builder().fileName("test2").videoDate(Date.valueOf("2022-06-15"))
                .build());
        testEntityManager.persist(ExerciesVideo.builder().fileName("test3").videoDate(Date.valueOf("2022-06-15"))
                .build());

        logger.info("when");
        List<ExerciesVideo> result = videoRepository.findAll();

        logger.info("then");
        assertEquals(3,result.size());
    }
}
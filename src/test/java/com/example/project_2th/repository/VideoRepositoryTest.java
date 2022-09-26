package com.example.project_2th.repository;

import com.example.project_2th.exception.PostNotFound;
import com.example.project_2th.controller.helper.UserHelper;
import com.example.project_2th.entity.Exercies;
import com.example.project_2th.entity.ExerciesVideo;
import com.example.project_2th.entity.Postures;
import com.example.project_2th.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class VideoRepositoryTest {

    @Autowired
    private EntityManager em;
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
        em.persist(user);
        user = em.find(User.class,1L);
        exercies = Exercies.builder().exDay(LocalDate.parse("2022-06-15")).exName("체스트 플라이").exCount("12")
                .userSet("4").exKinds("가슴").user(user).cnt("10").build();
        em.persist(exercies);

    }

    @Test
    void findByExercies() {
        logger.info("given");
        exercies = em.find(Exercies.class,1L);
        ExerciesVideo exerciesVideo = ExerciesVideo.builder().exercies(exercies).fileName("test").videoDate(LocalDate.parse("2022-06-15"))
                .user(user).build();
        em.persist(exerciesVideo);

        logger.info("when");
        Optional<ExerciesVideo> result = videoRepository.findByExercies(exercies);

        logger.info("then");
        assertEquals(exercies,result.get().getExercies());
    }

    @Test
    void findByVideoSeq() {
        logger.info("given");
        exercies = em.find(Exercies.class,1L);
        postures = userHelper.makePose();

        postures.forEach(info -> em.persist(info));

        ExerciesVideo exerciesVideo = ExerciesVideo.builder().postures(postures).exercies(exercies).fileName("test").videoDate(LocalDate.parse("2022-06-15"))
                .user(user).build();
        em.persist(exerciesVideo);

        logger.info("when");
        ExerciesVideo result = videoRepository.findByVideoSeq(1L).orElseThrow(PostNotFound::new);

        logger.info("then");
        assertNotNull(result.getExercies());
        assertNotNull(result.getPostures());
    }
    @Test
    void findByUser() {
        logger.info("given");
        exercies = em.find(Exercies.class,1L);
        postures = userHelper.makePose();
        postures.forEach(info -> em.persist(info));

        ExerciesVideo exerciesVideo = ExerciesVideo.builder().postures(postures).exercies(exercies).fileName("test").videoDate(LocalDate.parse("2022-06-15"))
                .user(user).build();
        em.persist(exerciesVideo);

        logger.info("when");
        List<ExerciesVideo> result = videoRepository.findByUser(user).orElseThrow(PostNotFound::new);

        logger.info("then");
        assertNotNull(result);
    }
    @Test
    void findAll() {
        logger.info("given");
        em.persist(ExerciesVideo.builder().fileName("test1").videoDate(LocalDate.parse("2022-06-15"))
                .build());
        em.persist(ExerciesVideo.builder().fileName("test2").videoDate(LocalDate.parse("2022-06-15"))
                .build());
        em.persist(ExerciesVideo.builder().fileName("test3").videoDate(LocalDate.parse("2022-06-15"))
                .build());

        logger.info("when");
        List<ExerciesVideo> result = videoRepository.findAll();

        logger.info("then");
        assertEquals(3,result.size());
    }
    @AfterEach
    void afterUp(){
        em.clear();
        this.em
                .createNativeQuery("ALTER TABLE user ALTER COLUMN `user_id` RESTART WITH 1")
                .executeUpdate();
        this.em
                .createNativeQuery("ALTER TABLE user_exercies ALTER COLUMN `ex_seq` RESTART WITH 1")
                .executeUpdate();
        this.em
                .createNativeQuery("ALTER TABLE user_postures ALTER COLUMN `posture_seq` RESTART WITH 1")
                .executeUpdate();
        this.em
                .createNativeQuery("ALTER TABLE user_exercies_videos ALTER COLUMN `video_seq` RESTART WITH 1")
                .executeUpdate();

    }
}
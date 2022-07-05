package com.example.project_2th.repository;

import com.example.project_2th.exception.PostNotFound;
import com.example.project_2th.controller.helper.UserHelper;
import com.example.project_2th.entity.Exercies;
import com.example.project_2th.entity.User;
import org.junit.jupiter.api.*;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class ExinfoRepositoryTest {


    @Autowired
    private EntityManager em;
    @Autowired
    private ExinfoRepository exinfoRepository;
    @Autowired
    private UserRepository userRepository;

    protected UserHelper userHelper = new UserHelper();

    private Logger logger = LoggerFactory.getLogger(ExinfoRepositoryTest.class);

    private User user;

    @BeforeEach
    void setUp(){
        user = userHelper.userCalendar();
        em.persist(user);
    }


    @Test
    void findExDay() {
        logger.info("given");
        user =userRepository.findByUserId(1L).orElseThrow(PostNotFound::new);

        Exercies exercies = Exercies.builder().exDay(Date.valueOf("2022-10-10")).exName("체스트 플라이").exCount("12")
                .userSet("4").exKinds("가슴").user(user).cnt("10").build();
        em.persist(exercies);

        logger.info("when");
        List<Exercies> result = exinfoRepository.findExDay(
                exercies.getUser().getUserId()
                ,exercies.getExDay());
        System.out.println("result : " + result);
        logger.info("then");
        assertEquals(exercies.getExDay(),result.get(0).getExDay());
    }

    @Test
    void findByOne() {
        logger.info("given");
        user =userRepository.findByUserId(1L).orElseThrow(PostNotFound::new);

        Exercies exercies = Exercies.builder().exDay(Date.valueOf("2022-10-10")).exName("체스트 플라이").exCount("12")
                .userSet("4").exKinds("가슴").user(user).cnt("10").build();
        em.persist(exercies);


        logger.info("when");
        Exercies result = exinfoRepository.findByOne(
                exercies.getUser().getUserId()
                ,exercies.getExName());

        logger.info("then");
        assertEquals(exercies.getExName(),result.getExName());
    }

    @Test
    void findByExSeq() {
        logger.info("given");
        user =userRepository.findByUserId(1L).orElseThrow(PostNotFound::new);

        Exercies exercies = Exercies.builder().exDay(Date.valueOf("2022-10-10")).exName("체스트 플라이").exCount("12")
                .userSet("4").exKinds("가슴").user(user).cnt("10").build();
        em.persist(exercies);
        exercies = em.find(Exercies.class,1L);

        logger.info("when");
        Exercies result = exinfoRepository.findByExSeq(exercies.getExSeq());

        logger.info("then");
        assertEquals(exercies.getExSeq(),result.getExSeq());
    }

    @Test
    void findByExKinds() {
        logger.info("given");
        user =userRepository.findByUserId(1L).orElseThrow(PostNotFound::new);
        Exercies exercies = Exercies.builder().exDay(Date.valueOf("2022-10-10")).exName("체스트 플라이").exCount("12")
                .userSet("4").exKinds("가슴").user(user).cnt("10").build();
        em.persist(exercies);
        em.persist(exercies);
        em.persist(exercies);

        logger.info("when");
        //List<Exercies>
        logger.info("then");
        //아직 사용하는 곳이 없다.
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

    }

}
package com.example.project_2th.repository;

import com.example.project_2th.config.TestDatasourceConfig;
import com.example.project_2th.controller.helper.UserHelper;
import com.example.project_2th.entity.Exercies;
import com.example.project_2th.entity.User;
import org.junit.Before;
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
class ExinfoRepositoryTest {


    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private ExinfoRepository exinfoRepository;

    protected UserHelper userHelper;
    private Logger logger = LoggerFactory.getLogger(ExinfoRepositoryTest.class);

    private User user;
    @BeforeEach
    void test1(){
        userHelper = new UserHelper();
        user = userHelper.userCalendar();
        testEntityManager.persist(user);
    }

    @Test
    void findExDay() {
        logger.info("given");
        user = testEntityManager.find(User.class,1L);
        Exercies exercies = Exercies.builder().exDay(Date.valueOf("2022-10-10")).exName("체스트 플라이").exCount("12")
                .userSet("4").exKinds("가슴").user(user).cnt("10").build();
        testEntityManager.persist(exercies);


        logger.info("when");
        List<Exercies> result = exinfoRepository.findExDay(
                exercies.getUser().getUserId()
                ,exercies.getExDay());

        logger.info("then");
        assertEquals(exercies.getExDay(),result.get(0).getExDay());
    }

    @Test
    void findByOne() {
        logger.info("given");
        user = testEntityManager.find(User.class,1L);
        Exercies exercies = Exercies.builder().exDay(Date.valueOf("2022-10-10")).exName("체스트 플라이").exCount("12")
                .userSet("4").exKinds("가슴").user(user).cnt("10").build();
        testEntityManager.persist(exercies);


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
        user = testEntityManager.find(User.class,1L);
        Exercies exercies = Exercies.builder().exDay(Date.valueOf("2022-10-10")).exName("체스트 플라이").exCount("12")
                .userSet("4").exKinds("가슴").user(user).cnt("10").build();
        testEntityManager.persist(exercies);
        exercies = testEntityManager.find(Exercies.class,1L);

        logger.info("when");
        Exercies result = exinfoRepository.findByExSeq(exercies.getExSeq());

        logger.info("then");
        assertEquals(1L,result.getExSeq());
    }

    @Test
    void findByExKinds() {
        logger.info("given");
        user = testEntityManager.find(User.class,1L);
        Exercies exercies = Exercies.builder().exDay(Date.valueOf("2022-10-10")).exName("체스트 플라이").exCount("12")
                .userSet("4").exKinds("가슴").user(user).cnt("10").build();
        testEntityManager.persist(exercies);
        testEntityManager.persist(exercies);
        testEntityManager.persist(exercies);

        logger.info("when");
        //List<Exercies>
        logger.info("then");
        //아직 사용하는 곳이 없다.
    }
}
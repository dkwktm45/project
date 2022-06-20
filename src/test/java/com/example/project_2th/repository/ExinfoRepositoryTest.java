package com.example.project_2th.repository;

import com.example.project_2th.controller.helper.UserHelper;
import com.example.project_2th.entity.Exercies;
import com.example.project_2th.entity.User;
import org.junit.BeforeClass;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
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
    private EntityManager testEntityManager;
    @Autowired
    private ExinfoRepository exinfoRepository;
    @Autowired
    private UserRepository userRepository;

    protected UserHelper userHelper = new UserHelper();

    private Logger logger = LoggerFactory.getLogger(ExinfoRepositoryTest.class);

    private User user;

    @BeforeEach
    void setUp(){
        //user = userHelper.userCalendar();
        //testEntityManager.persist(user);
        //this.user = userRepository.findByUserId(1L);
    }

    @Order(4)
    @Test
    void findExDay() {
        logger.info("given");
        user =createUser(idPlus());

        Exercies exercies = Exercies.builder().exDay(Date.valueOf("2022-10-10")).exName("체스트 플라이").exCount("12")
                .userSet("4").exKinds("가슴").user(this.user).cnt("10").build();
        testEntityManager.persist(exercies);
        idPlus();

        logger.info("when");
        List<Exercies> result = exinfoRepository.findExDay(
                exercies.getUser().getUserId()
                ,exercies.getExDay());

        logger.info("then");
        assertEquals(exercies.getExDay(),result.get(0).getExDay());
    }

    @Order(3)
    @Test
    void findByOne() {
        logger.info("given");
        user =createUser(idPlus());



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

    @Order(2)
    @Test
    void findByExSeq() {
        logger.info("given");
        user =createUser(idPlus());

        Exercies exercies = Exercies.builder().exDay(Date.valueOf("2022-10-10")).exName("체스트 플라이").exCount("12")
                .userSet("4").exKinds("가슴").user(user).cnt("10").build();
        testEntityManager.persist(exercies);
        exercies = testEntityManager.find(Exercies.class,4L);

        logger.info("when");
        Exercies result = exinfoRepository.findByExSeq(exercies.getExSeq());

        logger.info("then");
        assertEquals(exercies.getExSeq(),result.getExSeq());
    }

    @Order(1)
    @Test
    void findByExKinds() {
        logger.info("given");
        user =createUser(idPlus());


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
    public User createUser(Long id){
        testEntityManager.clear();
        user = userHelper.userCalendar();
        testEntityManager.persist(user);
        user = userRepository.findByUserId(id);
        return user;
    }
    public Long idPlus(){
        Long id =0L;
        Long result = id+1L;
        id = result;
        return result;
    }
}
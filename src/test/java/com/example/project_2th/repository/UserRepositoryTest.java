package com.example.project_2th.repository;

import com.example.project_2th.exception.PostNotFound;
import com.example.project_2th.controller.helper.UserHelper;
import com.example.project_2th.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private UserRepository userRepository;
    protected UserHelper userHelper;
    private Logger logger = LoggerFactory.getLogger(UserRepositoryTest.class);

    @DisplayName("loginNumber and gym findUser")
    @Test
    void findByLoginNumberAndUserGym() {
        //given
        userHelper = new UserHelper();
        User user = userHelper.makeUser();

        logger.info("저장 시작");
        testEntityManager.persist(user);
        logger.info("저장 끝");

        //when
        logger.info("find 시작");
        User result = userRepository.findByLoginNumberAndUserGym(user.getLoginNumber(), user.getUserGym()).orElseThrow(PostNotFound::new);
        logger.info("find 끝");

        //then
        assertEquals(user.getUserId(), result.getUserId());
    }

    @DisplayName("phone and gym findUser")
    @Test
    void findByUserIdAndUserGym() {
        //given
        userHelper = new UserHelper();
        User user1 = userHelper.makeUser();

        logger.info("저장 시작");
        testEntityManager.persist(user1);
        logger.info("저장 끝");

        //when
        logger.info("find 시작");
        User result = userRepository.findByUserIdAndUserGym(user1.getUserPhone(), user1.getUserGym()).orElseThrow(PostNotFound::new);
        logger.info("find 끝");

        //then
        assertEquals(user1.getUserId(), result.getUserId());
    }


    @Test
    void findByUserId() {
        //given
        userHelper = new UserHelper();
        User user = userHelper.makeUser();

        logger.info("저장 시작");
        userRepository.save(user);
        logger.info("저장 끝");

        //when
        logger.info("find 시작");
        User result = userRepository.findByUserId(user.getUserId()).orElseThrow(PostNotFound::new);
        logger.info("find 끝");

        //then
        assertEquals(user.getUserId(), result.getUserId());

    }

    @DisplayName("calendarList를 같이 가져오는 fetchJoin")
    @Test
    void findAllByFetchJoin() {
        //given
        userHelper = new UserHelper();
        User user = userHelper.userCalendar();

        logger.info("저장 시작");
        testEntityManager.persist(user);
        logger.info("저장 끝");

        //when
        List<User> users= userRepository.findAllByFetchJoin().orElseThrow(PostNotFound::new);

        //then
        assertEquals(4,users.get(0).getCalendarList().size());
    }

    @DisplayName("gym 과 관리자 여부에 따른 회원조회")
    @Test
    void findByUserGymAndManagerYn() {
        //given
        userHelper = new UserHelper();
        List<User> users = new ArrayList<>();
        users.add(userHelper.userCalendar());
        users.add(userHelper.userCalendar());
        users.add(userHelper.userCalendar());
        logger.info("저장 시작");
        testEntityManager.persist(users.get(0));
        testEntityManager.persist(users.get(1));
        testEntityManager.persist(users.get(2));
        logger.info("저장 끝");

        List<User> result = userRepository.findByUserGymAndManagerYn("해운대",0).orElseThrow(PostNotFound::new);

        assertEquals(users.size(),result.size());
    }

}

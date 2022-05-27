package com.example.project_2th.service;

import com.example.project_2th.controller.MainController;
import com.example.project_2th.entity.Exercies;
import com.example.project_2th.entity.ExerciesVideo;
import com.example.project_2th.entity.User;
import com.example.project_2th.repository.ExinfoRepository;
import groovy.util.logging.Slf4j;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@Slf4j
public class ExerciesServiceTest {

    @Autowired
    private ExerciesService exerciesService;

    @Autowired
    private UserService userService;

    @Autowired
    private ExinfoRepository exinfoRepository;


    @Autowired
    private EntityManager em;

    @Transactional
    @Test
    @DisplayName("운동의 대한 정보를 가져온다.")
    void test1() {
        User user = userService.login("4903", "해운대");

        Exercies exercies = new Exercies();
        exercies.setExCount("12");
        exercies.setExKinds("가슴");
        exercies.setExName("체스트 플라이");
        exercies.setUserSet("4");
        exercies.setUser(user);

        ExerciesVideo exerciesVideo = new ExerciesVideo();

        em.persist(exercies);

        Exercies exinfo = exinfoRepository.findByOne(user.getUserId(), exercies.getExName());
        System.out.println(exinfo);

        em.close();
    }
}

package com.example.project_2th.controller;

import com.example.project_2th.entity.User;
import com.example.project_2th.repository.UserRepository;
import com.example.project_2th.service.ExerciesService;
import com.example.project_2th.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.MockitoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MainController.class)
@RunWith(SpringRunner.class)
public class MainControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private ExerciesService exerciesService;


    @Autowired
    private MockMvc mockMvc;

    private MockHttpSession session;

    @Test
    public void test3() {
        User user = User.builder().userName("김화순").userPhone("9696")
                .userBirthdate(java.sql.Date.valueOf("1963-07-16")).userExpireDate(java.sql.Date.valueOf("2022-08-20"))
                .managerYn(0).videoYn(1).userGym("해운대").build();
    }

    @DisplayName("login page")
    @Test
    public void test1() throws Exception {
        mockMvc.perform(get("/login"))
                .andDo(print())
                //정상 처리 되는지 확인
                .andExpect(status().isOk());
        //담당 컨트롤러가 BoardController인지 확인
    }

    @DisplayName("login -> main page")
    @Test
    public void test2() throws Exception {
        User user = User.builder().userName("김화순").userPhone("9696")
                .userBirthdate(java.sql.Date.valueOf("1963-07-16")).userExpireDate(java.sql.Date.valueOf("2022-08-20"))
                .managerYn(0).videoYn(1).userGym("해운대").build();
        given(this.userService.login(user.getUserPhone(),user.getUserGym())) // this.postService.getJobList 메소드를 실행하면
                .willReturn(user); // Arrays.asList(jobs) 를 리턴해줘라.
        String redirect = "redirect:/main";

        given(this.userService.filterLogin(user, session))
                .willReturn(redirect);


        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();

        info.add("userGym", "해운대");
        info.add("userPhone", "9696");

        mockMvc.perform(post("/loginInsert").params(info))
                .andDo(print())
                //정상 처리 되는지 확인
                .andExpect(status().isOk());
    }

    @After
    public void clean(){
        //session.clearAttributes();
    }
}

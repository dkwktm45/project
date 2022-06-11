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
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MainController.class)
@RunWith(SpringRunner.class)
public class MainControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private ExerciesService exerciesService;


    @Autowired
    private MockMvc mockMvc;

    private User user;
    @Before
    public void test3() {
        user = User.builder().userName("김화순").userPhone("9696")
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
        MockHttpSession session = new MockHttpSession();

        Map<String,Object> list = new HashMap<>();
        list.put("user",user);
        given(this.userService.filterLogin(user.getUserPhone(), user.getUserGym(), session)).willReturn(list);


        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();

        info.add("userGym", "해운대");
        info.add("userPhone", "9696");

        mockMvc.perform(post("/loginInsert").params(info).session(session))
                .andDo(print())
                .andExpect(status().is3xxRedirection());

        assertNotNull(session.getAttribute("user"));
        verify(userService).filterLogin(user.getUserPhone(), user.getUserGym(), session);
    }

    @DisplayName("login -> admin page")
    @Test
    public void test4() throws Exception {
        User user = User.builder().userName("김화순").userPhone("9696")
                .userBirthdate(Date.valueOf("1963-07-16")).userExpireDate(Date.valueOf("2022-08-20"))
                .managerYn(1).videoYn(1).userGym("해운대").build();
        MockHttpSession session = new MockHttpSession();

        Map<String,Object> list = new HashMap<>();
        List<User> userList = new ArrayList<>();
        userList.add(user);
        userList.add(user);
        userList.add(user);
        list.put("user",user);
        list.put("userList",userList);
        given(this.userService.filterLogin(user.getUserPhone(), user.getUserGym(), session)).willReturn(list);


        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();

        info.add("userGym", "해운대");
        info.add("userPhone", "9696");

        mockMvc.perform(post("/loginInsert").params(info).session(session))
                .andDo(print())
                .andExpect(status().is3xxRedirection());

        assertNotNull(session.getAttribute("user"));
        verify(userService).filterLogin(user.getUserPhone(), user.getUserGym(), session);
    }
    @After
    public void clean(){
        //session.clearAttributes();
    }
}

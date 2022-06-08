package com.example.project_2th.controller;

import com.example.project_2th.service.ExerciesService;
import com.example.project_2th.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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

    @Before
    public void setup() {
    }

    @DisplayName("login page")
    @Test
    public void updateMonth() throws Exception {
        mockMvc.perform(get("/login"))
                .andDo(print())
                //정상 처리 되는지 확인
                .andExpect(status().isOk());
                //담당 컨트롤러가 BoardController인지 확인
    }

}

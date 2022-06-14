package com.example.project_2th.controller;

import com.example.project_2th.controller.helper.UserHelper;
import com.example.project_2th.entity.Calendar;
import com.example.project_2th.entity.Exercies;
import com.example.project_2th.service.ExerciesService;
import com.example.project_2th.service.ExerciesVideoService;
import com.example.project_2th.service.PostruesService;
import com.example.project_2th.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestController.class)
@RunWith(SpringRunner.class)
class RestControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private ExerciesService exerciesService;

    @MockBean
    private PostruesService postruesService;

    @MockBean
    private ExerciesVideoService exerciesVideoService;
    @Autowired
    private MockMvc mockMvc;

    protected UserHelper userHelper;
    @Test
    void calendarView() throws Exception {

        Calendar calendar = this.userHelper.makeCalendar();
        List<Exercies> exerciesList = new ArrayList<>();
        exerciesList.add(this.userHelper.makeExercies());
        exerciesList.add(this.userHelper.makeExercies());
        exerciesList.add(this.userHelper.makeExercies());

        given(this.exerciesService.calendarExinfo(this.userHelper.makeCalendar()))
                .willReturn(exerciesList);

        mockMvc.perform(get("/calendarView")
                        .flashAttr("user_calendar",calendar)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(RestController.class))
                .andDo(print());
    }

    @Test
    void insertExURL() {
    }

    @Test
    void getVideoinfo() {
    }

    @Test
    void insertBadImage() {
    }

    @Test
    void updateMonth() {
    }
}
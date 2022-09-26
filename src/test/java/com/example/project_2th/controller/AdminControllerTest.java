package com.example.project_2th.controller;

import com.example.project_2th.controller.helper.GsonLocalDateTimeAdapter;
import com.example.project_2th.controller.helper.UserHelper;
import com.example.project_2th.entity.User;
import com.example.project_2th.response.UserResponse;
import com.example.project_2th.security.config.SecurityConfig;
import com.example.project_2th.security.mock.WithMockCustomUser;
import com.example.project_2th.service.ExerciesVideoService;
import com.example.project_2th.service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = AdminController.class,
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
    })
@WithMockCustomUser(username = "test")
public class AdminControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private ExerciesVideoService exerciesVideoService;

    @Autowired
    private MockMvc mockMvc;

    private UserHelper userHelper;

    private Map<String, Object> map;

    private User user;

    MockHttpSession session;


    @BeforeEach
    public void beforeClassSetUp(){
        userHelper = new UserHelper();
        map = userHelper.makeAdmin();
        user = (User) map.get("user");
        session = new MockHttpSession();
        session.setAttribute("user",user);
    }

    @DisplayName("admin페이지로 관리자와 회원 정보를 가져온다.")
    @Test
    public void test1() throws Exception {
        //given
        UserResponse response = new UserResponse(this.user);
        List<UserResponse> responses = new ArrayList<>();
        responses.add(response);

        // when
        given(this.exerciesVideoService.loadUser(any(User.class))).willReturn(responses.get(0).getExercieVideosList());

        // then
        mockMvc.perform(get("/admin").session(session))
                .andExpect(status().isOk())
                .andExpect(request().sessionAttribute("userList", responses.get(0).getExercieVideosList()))
                .andExpect(handler().handlerType(AdminController.class))
                .andDo(print());
    }
    @DisplayName("회원의 정보가 reload 된다.")
    @Test
    public void test2() throws Exception {
        //given
        List<UserResponse> userList = (List<UserResponse>) this.userHelper.makeAdmin().get("userList");
        // when
        given(this.userService.reLoadMember(any(User.class))).willReturn(userList);

        // then
        mockMvc.perform(get("/admin/member").session(session))
                .andExpect(status().isOk())
                .andExpect(request().sessionAttribute("userList", userList))
                .andExpect(handler().handlerType(AdminController.class))
                .andDo(print());
    }

    @DisplayName("회원가입")
    @Test
    public void test3() throws Exception {
        //given
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new GsonLocalDateTimeAdapter())
                .create();
        User req = userHelper.makeUser();
        String json = gson.toJson(req);

        // when
        mockMvc.perform(put("/admin/join-member").with(csrf())
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().handlerType(AdminController.class))
                .andDo(print());
    }

    @AfterEach
    public void afterClassSetUp(){
        session.clearAttributes();
    }
}

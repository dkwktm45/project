package com.example.project_2th.controller;

import com.example.project_2th.controller.helper.GsonLocalDateTimeAdapter;
import com.example.project_2th.controller.helper.UserHelper;
import com.example.project_2th.entity.User;
import com.example.project_2th.response.UserResponse;
import com.example.project_2th.security.mock.WithMockCustomUser;
import com.example.project_2th.security.service.CustomUserDetailsServiceTest;
import com.example.project_2th.security.service.UserContext;
import com.example.project_2th.service.ExerciesVideoService;
import com.example.project_2th.service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AdminController.class})
@WebAppConfiguration
@AutoConfigureMockMvc
public class AdminControllerTest {
    @MockBean
    private UserService userService;

    @MockBean
    private ExerciesVideoService exerciesVideoService;

    @Autowired
    private MockMvc mockMvc;

    private static UserHelper userHelper;

    private static Map<String, Object> map;

    private static User user;

    private static MockHttpSession session;


    private final CustomUserDetailsServiceTest customUserDetailsService = new CustomUserDetailsServiceTest();

    @MockBean
    private UserContext userContext;

    @BeforeClass
    public static void beforeClassSetUp(){
        userHelper = new UserHelper();
        map = userHelper.makeAdmin();
        user = (User) map.get("user");

       session = new MockHttpSession();
    }
    @BeforeEach
    void eachSetUp(){
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
        userContext = (UserContext) customUserDetailsService.loadUserByUsername("010-1234-5678");


        // then
        mockMvc.perform(get("/admin").with(user(userContext)).session(new MockHttpSession()))
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
        given(this.userService.reLoadMember(any(User.class))).willReturn(any(userList.getClass()));
        userContext = (UserContext) customUserDetailsService.loadUserByUsername("010-1234-5678");

        // then
        mockMvc.perform(get("/admin/member").with(user(userContext)))
                .andExpect(status().isOk())
                .andExpect(request().sessionAttribute("userList", null))
                .andExpect(handler().handlerType(AdminController.class))
                .andDo(print());
    }

    @WithMockUser(username = "user1", value = "User", password = "pwd", roles = "ADMIN")
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
        mockMvc.perform(put("/admin/join-member")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().handlerType(AdminController.class))
                .andDo(print());

    }
}

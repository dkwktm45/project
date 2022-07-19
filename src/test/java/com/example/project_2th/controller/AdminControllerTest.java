package com.example.project_2th.controller;

import com.example.project_2th.controller.helper.GsonLocalDateTimeAdapter;
import com.example.project_2th.controller.helper.UserHelper;
import com.example.project_2th.entity.User;
import com.example.project_2th.response.UserResponse;
import com.example.project_2th.security.config.SecurityConfig;
import com.example.project_2th.security.service.CustomUserDetailsService;
import com.example.project_2th.security.service.CustomUserDetailsServiceTest;
import com.example.project_2th.security.service.UserContext;
import com.example.project_2th.service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "user1", value = "User", password = "pwd", roles = "ADMIN")
public class AdminControllerTest {

    @MockBean
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;

    MockHttpSession session;

    protected UserHelper userHelper = new UserHelper();
    private Map<String, Object> map;

    protected User user;
    private final CustomUserDetailsServiceTest customUserDetailsService = new CustomUserDetailsServiceTest();

    @MockBean
    private UserContext userContext;
    @Autowired
    private WebApplicationContext context;

    @DisplayName("admin페이지로 관리자와 회원 정보를 가져온다.")
    @Test
    public void test1() throws Exception {
        map = this.userHelper.makeAdmin();
        this.user = (User) map.get("user");
        UserResponse response = new UserResponse(this.user);
        List<UserResponse> responses = new ArrayList<>();
        responses.add(response);
        given(this.userService.loadUser(any(User.class))).willReturn(responses);

        userContext = (UserContext) customUserDetailsService.loadUserByUsername("010-1234-5678");



        mockMvc.perform(get("/admin").with(user(userContext)).session(new MockHttpSession()))
                .andExpect(status().isOk())
                .andExpect(request().sessionAttribute("userList", responses))
                .andExpect(handler().handlerType(AdminController.class))
                .andDo(print());
    }
    @DisplayName("회원의 정보가 reload 된다.")
    @Test
    public void test2() throws Exception {
        session = new MockHttpSession();
        user = (User) this.userHelper.makeAdmin().get("user");
        session.setAttribute("user", user);
        List<UserResponse> userList = (List<UserResponse>) this.userHelper.makeAdmin().get("userList");

        given(this.userService.reLoadMember(user)).willReturn(userList);

        mockMvc.perform(get("/admin/member").session(session))
                .andExpect(status().isOk())
                .andExpect(request().sessionAttribute("userList", userList))
                .andExpect(handler().handlerType(AdminController.class))
                .andDo(print());
    }

    @DisplayName("회원가입")
    @Test
    public void test3() throws Exception {
        User user = userHelper.makeUser();
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new GsonLocalDateTimeAdapter())
                .create();
        String json = gson.toJson(user);
        mockMvc.perform(put("/admin/join-member")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().handlerType(AdminController.class))
                .andDo(print());
    }
}

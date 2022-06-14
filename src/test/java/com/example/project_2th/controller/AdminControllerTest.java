package com.example.project_2th.controller;

import com.example.project_2th.controller.helper.UserHelper;
import com.example.project_2th.entity.User;
import com.example.project_2th.service.UserService;
import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.GsonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
@RunWith(SpringRunner.class)
public class AdminControllerTest {

    @MockBean
    private UserService userService;

    MockHttpSession session;
    MockHttpServletRequest request;

    protected UserHelper userHelper;
    private Map<String,Object> map;

    protected User user;
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
    }

    @DisplayName("admin페이지로 관리자와 회원 정보를 가져온다.")
    @Test
    public void test1() throws Exception {
        session = new MockHttpSession();

        map = this.userHelper.makeAdmin();
        session.setAttribute("user",map.get("user"));
        session.setAttribute("userList",map.get("userList"));


        mockMvc.perform(get("/admin/").session(session))
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(AdminController.class))
                .andDo(print());
    }

    @DisplayName("회원의 정보가 reload 된다.")
    @Test
    public void test2() throws Exception{
        session = new MockHttpSession();
        user = (User) this.userHelper.makeAdmin().get("user");
        session.setAttribute("user",user);
        List<User> userList = (List<User>) this.userHelper.makeAdmin().get("userList");

        given(this.userService.reLoadMember(user)).willReturn(userList);

        mockMvc.perform(get("/admin/Member").session(session))
                .andExpect(status().isOk())
                .andExpect(request().sessionAttribute("userList",userList))
                .andExpect(handler().handlerType(AdminController.class))
                .andDo(print());
    }
    @DisplayName("회원가입")
    @Test
    public void test3() throws Exception{

        user = userHelper.makeUser();
        mockMvc.perform(post("/admin/joinMember")
                        .flashAttr("user",user)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().handlerType(AdminController.class))
                .andDo(print());
    }
}

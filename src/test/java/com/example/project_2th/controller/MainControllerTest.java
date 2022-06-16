package com.example.project_2th.controller;

import com.example.project_2th.entity.Calendar;
import com.example.project_2th.entity.Exercies;
import com.example.project_2th.entity.ExerciesVideo;
import com.example.project_2th.entity.User;
import com.example.project_2th.controller.helper.UserHelper;
import com.example.project_2th.service.ExerciesService;
import com.example.project_2th.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.sql.Date;
import java.util.*;

import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.boot.autoconfigure.condition.ConditionOutcome.match;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MainController.class)
public class MainControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private ExerciesService exerciesService;

    @Autowired
    private MockMvc mockMvc;


    MockHttpSession session;

    protected UserHelper userHelper;
    protected User user;
    @Test
    @DisplayName("로그인 페이지")
    public void test1() throws Exception {
        mockMvc.perform(get("/login"))
                .andDo(print())
                //정상 처리 되는지 확인
                .andExpect(status().isOk());
    }

    @DisplayName("form 데이터를 loginInsert로 이동(user)")
    @Test
    public void test2() throws Exception {
        MockHttpSession session = new MockHttpSession();

        Map<String,Object> list = this.userHelper.mapToObject(this.userHelper.makeUser());
        User user = (User) list.get("user");
        given(this.userService.filterLogin(user.getUserPhone(), user.getUserGym())).willReturn(list);


        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();
        info.add("userGym", "해운대");
        info.add("userPhone", "9696");

        mockMvc.perform(post("/loginInsert").params(info).session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(request().sessionAttribute("user",user))
                .andExpect(redirectedUrl("/main"))
                .andDo(print())
        ;

        verify(userService).filterLogin(user.getUserPhone(), user.getUserGym());
    }

    @DisplayName("form 데이터를 loginInsert로 이동(admin)")
    @Test
    public void test4() throws Exception {
        session = new MockHttpSession();
        Map<String,Object> list =userHelper.makeAdmin();
        User user = (User) list.get("user");

        given(this.userService.filterLogin(user.getUserPhone(), user.getUserGym())).willReturn(list);

        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();

        info.add("userGym", "해운대");
        info.add("userPhone", "9696");

        mockMvc.perform(post("/loginInsert").params(info).session(session))
                .andExpect(redirectedUrl("/admin"))
                .andExpect(request().sessionAttribute("user",user))
                .andExpect(request().sessionAttribute("userList",session.getAttribute("userList")))
                .andExpect(status().is3xxRedirection())
                .andDo(print());

        verify(userService).filterLogin(user.getUserPhone(), user.getUserGym());
    }


    @DisplayName("calendar 페이지로 유저에 대한 운동 정보가 담겨서 이동한다.")
    @Test
    public void test6() throws Exception {
        session = new MockHttpSession();
        session.setAttribute("user",userHelper.makeUser());
        user = (User) session.getAttribute("user");

        Calendar calendar = userHelper.makeCalendar();

        List<Calendar> calendarList = new ArrayList<>();
        calendarList.add(calendar);
        calendarList.add(calendar);
        calendarList.add(calendar);

        given(this.userService.infoCalendar(user)).willReturn(calendarList);

        mockMvc.perform(get("/infoCalender").session(session))
                .andExpect(redirectedUrl("/test"))
                .andExpect(request().sessionAttribute("calendarInfo",calendarList))
                .andExpect(status().is3xxRedirection())
                .andDo(print());

        verify(userService).infoCalendar(user);
    }
    @DisplayName("/goRecord session에는 exinfoList, videoList가 담긴채로 이동한다.")
    @Test
    public void test7() throws Exception {
        session = new MockHttpSession();
        session.setAttribute("user",userHelper.makeUser());
        user = (User) session.getAttribute("user");

        Map<String,Object> map = new HashMap<>();
        List<Exercies> exinfoList = this.userHelper.makeExinfos(user);
        List<ExerciesVideo> videoList = this.userHelper.makeVideos(user);
        map.put("videoList",videoList);
        map.put("exinfoList",exinfoList);

        given(this.userService.infoRecord(user)).willReturn(map);

        mockMvc.perform(get("/goRecord").session(session))
                .andExpect(redirectedUrl("/record"))
                .andExpect(status().is3xxRedirection())
                .andExpect(request().sessionAttribute("exinfoList", map.get("exinfoList")))
                .andExpect(request().sessionAttribute("videoList", map.get("videoList")))
                .andDo(print());
        verify(userService).infoRecord(user);

    }
    @DisplayName("/insertEx 이동하면서 session exinfo 정보를 담는다.")
    @Test
    public void test8() throws Exception {
        session = new MockHttpSession();
        session.setAttribute("user",userHelper.makeUser());
        Exercies exercies = userHelper.makeExercies();
        given(this.exerciesService.exerciesInfo(exercies)).willReturn(exercies);


        mockMvc.perform(post("/insertEx").flashAttr("user_exercies",exercies)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }
    @DisplayName("main 페이지로 유저 정보가 담겨서 이동한다.")
    @Test
    public void test5() throws Exception {
        session = new MockHttpSession();
        session.setAttribute("user",userHelper.makeUser());

        mockMvc.perform(get("/main").session(session))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @DisplayName("/test 페이지로 user, calendar 정보를 담고 이동한다.")
    @Test
    public void test9() throws Exception {

        session = new MockHttpSession();
        session.setAttribute("user",userHelper.makeUser());
        session.setAttribute("calendarInfo",userHelper.makeCalendar());

        mockMvc.perform(get("/test").session(session))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @DisplayName("/record 페이지로 exinfoList, videoList 정보를 담고 이동한다.")
    @Test
    public void test10() throws Exception {
        session = new MockHttpSession();
        session.setAttribute("user",userHelper.makeUser());
        user = (User) session.getAttribute("user");

        session.setAttribute("exinfoList",userHelper.makeExinfos(user));
        session.setAttribute("videoList",userHelper.makeVideos(user));

        mockMvc.perform(get("/record").session(session))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @After
    public void clean(){
        session.clearAttributes();
    }

}

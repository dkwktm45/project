package com.example.project_2th.controller;

import com.example.project_2th.controller.helper.UserHelper;
import com.example.project_2th.entity.Calendar;
import com.example.project_2th.entity.Exercies;
import com.example.project_2th.entity.ExerciesVideo;
import com.example.project_2th.entity.User;
import com.example.project_2th.response.CalendarResponse;
import com.example.project_2th.response.ExerciesResponse;
import com.example.project_2th.security.config.SecurityConfig;
import com.example.project_2th.security.mock.WithMockCustomUser;
import com.example.project_2th.security.service.UserContext;
import com.example.project_2th.service.ExerciesService;
import com.example.project_2th.service.UserService;
import org.junit.After;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = MainController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        })
@WithMockCustomUser(username = "test",role = "ROLE_USER")
public class MainControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private ExerciesService exerciesService;

    @Autowired
    private MockMvc mockMvc;

    MockHttpSession session;
    protected UserHelper userHelper = new UserHelper();
    protected User user;

    @MockBean
    private UserContext userContext;
    @Test
    @DisplayName("로그인 페이지")
    public void test1() throws Exception {
        mockMvc.perform(get("/user/login"))
                .andDo(print())
                //정상 처리 되는지 확인
                .andExpect(status().isOk());
    }


    @DisplayName("calendar 페이지로 유저에 대한 운동 정보가 담겨서 이동한다.")
    @Test
    public void test6() throws Exception {
        // given
        Exercies calendar = userHelper.makeExercies();
        ExerciesResponse exerciesResponse = new ExerciesResponse(calendar);
        List<ExerciesResponse> responses = new ArrayList<>();
        responses.add(exerciesResponse);
        responses.add(exerciesResponse);
        responses.add(exerciesResponse);
        User result = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // when
        given(this.exerciesService.calendarResponse(result)).willReturn(responses);

        // then
        mockMvc.perform(get("/user/calendar"))
                .andExpect(redirectedUrl("/user/test"))
                .andExpect(request().sessionAttribute("exerciesInfo",responses))
                .andExpect(status().is3xxRedirection())
                .andDo(print());

        verify(exerciesService).calendarResponse(result);
    }


    @DisplayName("/exinfo session에는 exinfoList, videoList가 담긴채로 이동한다.")
    @Test
    public void test7() throws Exception {
        // given
        Map<String, Object> map = new HashMap<>();
        List<Exercies> exinfoList = this.userHelper.makeExinfos();
        List<ExerciesVideo> videoList = this.userHelper.makeVideos();
        map.put("videoList", videoList);
        map.put("exinfoList", exinfoList);

        // when
        given(this.userService.infoRecord(any(User.class))).willReturn(map);

        // then
        mockMvc.perform(get("/user/exinfo"))
                .andExpect(redirectedUrl("/user/record"))
                .andExpect(status().is3xxRedirection())
                .andExpect(request().sessionAttribute("exinfoList", map.get("exinfoList")))
                .andExpect(request().sessionAttribute("videoList", map.get("videoList")))
                .andDo(print());
        verify(userService).infoRecord(any(User.class));
    }

    @DisplayName("/exinfo 이동하면서 session exinfo 정보를 담는다.")
    @Test
    public void test8() throws Exception {
        // given
        session = new MockHttpSession();
        session.setAttribute("user", userHelper.makeUser());
        Exercies exercies = userHelper.makeExercies();
        ExerciesResponse response = new ExerciesResponse(exercies);
        // when
        given(this.exerciesService.exerciesInfo(exercies)).willReturn(response);

        // then
        mockMvc.perform(post("/user/exinfo").flashAttr("user_exercies", exercies)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @WithMockCustomUser(username = "test",role = "ROLE_USER")
    @Nested
    @DisplayName("main redirect")
    class t1est11 {

        @DisplayName("main 페이지로 유저 정보가 담겨서 이동한다.")
        @Test
        public void test5() throws Exception {
            session = new MockHttpSession();
            session.setAttribute("user", userHelper.makeUser());
            mockMvc.perform(get("/user/main").session(session))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @DisplayName("/test 페이지로 user, calendar 정보를 담고 이동한다.")
        @Test
        public void test9() throws Exception {

            session = new MockHttpSession();
            session.setAttribute("user", userHelper.makeUser());
            session.setAttribute("calendarInfo", userHelper.makeCalendar());

            mockMvc.perform(get("/user/test").session(session))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @DisplayName("/record 페이지로 exinfoList, videoList 정보를 담고 이동한다.")
        @Test
        public void test10() throws Exception {
            session = new MockHttpSession();
            session.setAttribute("user", userHelper.makeUser());
            user = (User) session.getAttribute("user");

            session.setAttribute("exinfoList", userHelper.makeExinfos());
            session.setAttribute("videoList", userHelper.makeVideos());

            mockMvc.perform(get("/user/record").session(session))
                    .andDo(print())
                    .andExpect(status().isOk());
        }
    }

    @After
    public void clean() {
        session.clearAttributes();
    }

}

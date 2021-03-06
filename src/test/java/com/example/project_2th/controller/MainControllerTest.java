package com.example.project_2th.controller;

import com.example.project_2th.entity.Calendar;
import com.example.project_2th.entity.Exercies;
import com.example.project_2th.entity.ExerciesVideo;
import com.example.project_2th.entity.User;
import com.example.project_2th.controller.helper.UserHelper;
import com.example.project_2th.exception.PostNotFound;
import com.example.project_2th.response.CalendarResponse;
import com.example.project_2th.response.ErrorResponse;
import com.example.project_2th.response.ExerciesResponse;
import com.example.project_2th.security.config.SecurityConfig;
import com.example.project_2th.security.service.UserContext;
import com.example.project_2th.service.ExerciesService;
import com.example.project_2th.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Date;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mockitoSession;
import static org.mockito.Mockito.verify;
import static org.springframework.boot.autoconfigure.condition.ConditionOutcome.match;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = MainController.class,
        excludeFilters = {
                @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE,classes = SecurityConfig.class)
        })
@WithMockUser(roles = "USER")
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

    @Test
    @DisplayName("????????? ?????????")
    public void test1() throws Exception {
        mockMvc.perform(get("/user/login"))
                .andDo(print())
                //?????? ?????? ????????? ??????
                .andExpect(status().isOk());
    }


    @DisplayName("calendar ???????????? ????????? ?????? ?????? ????????? ????????? ????????????.")
    @Test
    public void test6() throws Exception {
        // given
        session = new MockHttpSession();
        session.setAttribute("user", userHelper.makeUser());
        user = (User) session.getAttribute("user");
        Calendar calendar = userHelper.makeCalendar();
        CalendarResponse response = new CalendarResponse(calendar);
        List<CalendarResponse> calendarList = new ArrayList<>();
        calendarList.add(response);
        calendarList.add(response);
        calendarList.add(response);

        // when
        given(this.userService.infoCalendar(user)).willReturn(calendarList);

        // then
        mockMvc.perform(get("/user/calendar").session(session))
                .andExpect(redirectedUrl("/user/test"))
                .andExpect(request().sessionAttribute("calendarInfo", calendarList))
                .andExpect(status().is3xxRedirection())
                .andDo(print());

        verify(userService).infoCalendar(user);
    }
    @DisplayName("calendar ???????????? ????????? ?????? ?????? ????????? ????????? ????????????.")
    @Test
    public void test11() throws Exception {
        //given
        session = new MockHttpSession();
        session.setAttribute("user", userHelper.makeUser());
        user = (User) session.getAttribute("user");
        Calendar calendar = userHelper.makeCalendar();
        CalendarResponse response = new CalendarResponse(calendar);
        List<CalendarResponse> calendarList = new ArrayList<>();
        calendarList.add(response);
        calendarList.add(response);
        calendarList.add(response);

        // when
        given(this.userService.infoCalendar(user)).willReturn(calendarList);

        //then
        mockMvc.perform(get("/user/calendar").session(session))
                .andExpect(redirectedUrl("/user/test"))
                .andExpect(request().sessionAttribute("calendarInfo", calendarList))
                .andExpect(status().is3xxRedirection())
                .andDo(print());

        verify(userService).infoCalendar(user);
    }


    @DisplayName("/exinfo session?????? exinfoList, videoList??? ???????????? ????????????.")
    @Test
    public void test7() throws Exception {
        // given
        session = new MockHttpSession();
        session.setAttribute("user", userHelper.makeUser());
        user = (User) session.getAttribute("user");

        Map<String, Object> map = new HashMap<>();
        List<Exercies> exinfoList = this.userHelper.makeExinfos();
        List<ExerciesVideo> videoList = this.userHelper.makeVideos();
        map.put("videoList", videoList);
        map.put("exinfoList", exinfoList);

        // when
        given(this.userService.infoRecord(user)).willReturn(map);

        // then
        mockMvc.perform(get("/user/exinfo").session(session))
                .andExpect(redirectedUrl("/user/record"))
                .andExpect(status().is3xxRedirection())
                .andExpect(request().sessionAttribute("exinfoList", map.get("exinfoList")))
                .andExpect(request().sessionAttribute("videoList", map.get("videoList")))
                .andDo(print());
        verify(userService).infoRecord(user);

    }

    @DisplayName("/exinfo ??????????????? session exinfo ????????? ?????????.")
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

    @Nested
    @DisplayName("main redirect")
    class t1est11 {

        @DisplayName("main ???????????? ?????? ????????? ????????? ????????????.")
        @Test
        public void test5() throws Exception {
            session = new MockHttpSession();
            session.setAttribute("user", userHelper.makeUser());
            mockMvc.perform(get("/user/main").session(session))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @DisplayName("/test ???????????? user, calendar ????????? ?????? ????????????.")
        @Test
        public void test9() throws Exception {

            session = new MockHttpSession();
            session.setAttribute("user", userHelper.makeUser());
            session.setAttribute("calendarInfo", userHelper.makeCalendar());

            mockMvc.perform(get("/user/test").session(session))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @DisplayName("/record ???????????? exinfoList, videoList ????????? ?????? ????????????.")
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

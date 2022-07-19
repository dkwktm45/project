package com.example.project_2th.controller;

import com.example.project_2th.controller.helper.GsonLocalDateTimeAdapter;
import com.example.project_2th.controller.helper.UserHelper;
import com.example.project_2th.entity.Calendar;
import com.example.project_2th.entity.Exercies;
import com.example.project_2th.entity.ExerciesVideo;
import com.example.project_2th.entity.User;
import com.example.project_2th.response.ExerciesResponse;
import com.example.project_2th.security.config.SecurityConfig;
import com.example.project_2th.service.ExerciesService;
import com.example.project_2th.service.ExerciesVideoService;
import com.example.project_2th.service.PostruesService;
import com.example.project_2th.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.servlet.ServletInputStream;
import java.security.Principal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = RestControllerApi.class,
    excludeFilters = {
        @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE,classes = SecurityConfig.class)
    })
class RestControllerApiTest {

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

    protected UserHelper userHelper = new UserHelper();

    @Spy
    private List<Exercies> exerciesList = null;

    @Spy
    private Calendar calendar;


    @DisplayName("calendarView 에서 날짜에 따른 운동 정보들을 가져온다.")
    @Test
    @WithMockUser(roles = "USER")
    void calendarView() throws Exception {
        // given
        calendar = Calendar.builder().user(User.builder().userId(1L).userName("김화순").loginNumber("1234").userPhone("010-2345-1234")
                .userBirthdate(LocalDate.parse("1963-07-16")).userExpireDate(LocalDate.parse("2022-08-20"))
                .managerYn(0).videoYn(1).userGym("해운대").build()).exDay(LocalDate.parse("2021-05-05")).build();
        exerciesList = new ArrayList<>();
        exerciesList.add(this.userHelper.makeExercies());
        exerciesList.add(this.userHelper.makeExercies());
        exerciesList.add(this.userHelper.makeExercies());
        List<ExerciesResponse> list = new ArrayList(exerciesList);

        // when
        given(this.exerciesService.calendarExinfo(any(Calendar.class)))
                .willReturn(list);
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new GsonLocalDateTimeAdapter())
                .create();

        String json = gson.toJson(calendar);

        // then
        mockMvc.perform(post("/user/calendar-info")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(3)))
                .andExpect(jsonPath("$.[0:3].userSet").exists())
                .andExpect(jsonPath("$.[0:3].exKinds").exists())
                .andExpect(jsonPath("$.[0:3].cnt").exists())
                .andExpect(jsonPath("$.[0:3].exCount").exists())
                .andExpect(jsonPath("$.[0:3].exName").exists())
                .andExpect(handler().handlerType(RestControllerApi.class))
                .andDo(print());

        verify(exerciesService, never()).calendarExinfo(calendar);
    }

    @DisplayName("video 저장 uri")
    @Test
    @WithMockUser(roles = "USER")
    void insertExURL() throws Exception {
        //given
        byte[] bytes = new byte[]{1, 2};
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("userId", "1");
        data.add("exSeq", String.valueOf(userHelper.makeExercies().getExSeq()));
        data.add("cnt", userHelper.makeExercies().getCnt());

        // when
        MvcResult result = mockMvc.perform(post("/user/exercies-info")
                        .params(data).content(bytes)
                        .contentType(MediaType.APPLICATION_JSON).with(csrf()))
                //then
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(RestControllerApi.class))
                .andDo(print()).andReturn();

        MockHttpServletRequest request = result.getRequest();
        String cnt = String.valueOf(request.getAttribute("cnt"));
        String userId = String.valueOf(request.getAttribute("userId"));
        String exSeq = String.valueOf(request.getAttribute("exSeq"));
        ServletInputStream stream = request.getInputStream();

        assertNotNull(cnt);
        assertNotNull(userId);
        assertNotNull(exSeq);
        assertNotNull(stream);
    }
    @Nested
    class exinfo{
        MultiValueMap<String, String> data;
        @BeforeEach
        void setUp(){
            data = new LinkedMultiValueMap<>();

        }

        @DisplayName("/pose : video 번호를 통해 자세정보와 운동정보를 보내는 uri")
        @Test
        @WithMockUser(roles = "USER")
        void getVideoinfo() throws Exception {
            // given
            ExerciesVideo exerciesVideo = userHelper.makeVideo();
            data.add("videoSeq", String.valueOf(exerciesVideo.getVideoSeq()));
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("exinfo", userHelper.makeExercies());
            map.put("postures", userHelper.makePose());

            // when
            given(exerciesVideoService.selectVideoInfo(exerciesVideo.getVideoSeq()))
                    .willReturn(map);
            // then
            mockMvc.perform(get("/user/pose")
                            .params(data)
                            .contentType(MediaType.APPLICATION_JSON)
                            .with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.exinfo").exists())
                    .andExpect(jsonPath("$.postures[0:3].videoTime").exists())
                    .andExpect(jsonPath("$.postures[0:3].poseResult").exists())
                    .andExpect(jsonPath("$.postures[0:3].aiComment").exists())
                    .andExpect(handler().handlerType(RestControllerApi.class))
                    .andDo(print());

            verify(exerciesVideoService).selectVideoInfo(exerciesVideo.getVideoSeq());
        }

        @DisplayName("/pose-bad : bad image 를 저장하는 uri")
        @Test
        @WithMockUser(roles = "USER")
        void insertBadImage() throws Exception {
            //given
            data.add("ai_comment", String.valueOf(2L));
            data.add("ex_seq", String.valueOf(1L));


            // when
            MvcResult result = mockMvc.perform(put("/user/pose-bad")
                            .params(data)
                            .contentType(MediaType.APPLICATION_JSON)
                            .with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(handler().handlerType(RestControllerApi.class))
                    .andDo(print()).andReturn();
            // then
            MockHttpServletRequest request = result.getRequest();
            String comment = String.valueOf(request.getAttribute("ai_comment"));
            String seq = String.valueOf(request.getAttribute("ex_seq"));
            assertNotNull(comment);
            assertNotNull(seq);
        }

        @DisplayName("updateMonth uri 에서 개월수 update")
        @Test
        @WithMockUser(roles = "ADMIN")
        void updateMonth() throws Exception {
            // given
            data.add("userExpireDate", "2022-10-10");
            data.add("userId", String.valueOf(1L));

            //when
            MvcResult result = mockMvc.perform(patch("/admin/month")
                            .params(data)
                            .contentType(MediaType.APPLICATION_JSON)
                            .with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(handler().handlerType(RestControllerApi.class))
                    .andDo(print()).andReturn();

            //then
            MockHttpServletRequest request = result.getRequest();
            String userExpireDate = String.valueOf(request.getAttribute("userExpireDate"));
            String userId = String.valueOf(request.getAttribute("userId"));
            assertNotNull(userExpireDate);
            assertNotNull(userId);
        }

    }


}

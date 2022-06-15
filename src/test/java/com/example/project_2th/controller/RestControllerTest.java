package com.example.project_2th.controller;

import com.example.project_2th.controller.helper.UserHelper;
import com.example.project_2th.entity.Calendar;
import com.example.project_2th.entity.Exercies;
import com.example.project_2th.entity.ExerciesVideo;
import com.example.project_2th.service.ExerciesService;
import com.example.project_2th.service.ExerciesVideoService;
import com.example.project_2th.service.PostruesService;
import com.example.project_2th.service.UserService;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;

import javax.servlet.ServletInputStream;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.data.redis.serializer.RedisSerializer.json;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    @DisplayName("calendarView 에서 날짜에 따른 운동 정보들을 가져온다.")
    @Test
    void calendarView() throws Exception {

        Calendar calendar = this.userHelper.makeCalendar();
        List<Exercies> exerciesList = new ArrayList<>();
        exerciesList.add(this.userHelper.makeExercies());
        exerciesList.add(this.userHelper.makeExercies());
        exerciesList.add(this.userHelper.makeExercies());

        given(this.exerciesService.calendarExinfo(calendar))
                .willReturn(exerciesList);

        mockMvc.perform(get("/calendarView")
                        .flashAttr("user_calendar",calendar)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(handler().handlerType(RestController.class))
                .andDo(print());
        verify(exerciesService).calendarExinfo(calendar);
    }

    @DisplayName("video 저장 uri")
    @Test
    void insertExURL() throws Exception {
        byte[] bytes = new byte[]{1,2};
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        MultiValueMap<String ,String> data =  new LinkedMultiValueMap<>();
        data.add("userId", String.valueOf(userHelper.makeUser().getUserId()));
        data.add("exSeq", String.valueOf(userHelper.makeExercies().getExSeq()));
        data.add("cnt", "11");

        MvcResult result = mockMvc.perform(post("/insertExURL")
                        .params(data).content(bytes)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(RestController.class))
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

    @DisplayName("/insertPose : video 번호를 통해 자세정보와 운동정보를 보내는 uri")
    @Test//
    void getVideoinfo() throws Exception {
        MultiValueMap<String ,String> data =  new LinkedMultiValueMap<>();
        data.add("videoSeq", String.valueOf(userHelper.makeVideo().getVideoSeq()));
        ExerciesVideo videoInfo= userHelper.makeVideo();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("exinfo", userHelper.makeExercies());
        map.put("postures", userHelper.makePose());

        given(this.exerciesVideoService.selectVideoInfo(videoInfo.getVideoSeq()))
                .willReturn(map);

        mockMvc.perform(get("/insertPose")
                        .params(data)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(RestController.class))
                .andDo(print());

        verify(exerciesVideoService).selectVideoInfo(videoInfo.getVideoSeq());
    }

    @DisplayName("/insertBadImage : bad image 를 저장하는 uri")
    @Test
    void insertBadImage() throws Exception {
        MultiValueMap<String ,String> data =  new LinkedMultiValueMap<>();
        data.add("ai_comment", String.valueOf(2L));
        data.add("ex_seq", String.valueOf(1L));


        MvcResult result =mockMvc.perform(post("/insertBadImage")
                        .params(data)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(RestController.class))
                .andDo(print()).andReturn();

        MockHttpServletRequest request = result.getRequest();
        String comment = String.valueOf(request.getAttribute("ai_comment"));
        String seq = String.valueOf(request.getAttribute("ex_seq"));
        assertNotNull(comment);
        assertNotNull(seq);
    }

    @DisplayName("updateMonth uri 에서 개월수 update")
    @Test
    void updateMonth() throws Exception {
        MultiValueMap<String ,String> data =  new LinkedMultiValueMap<>();
        data.add("userExpireDate", "2022-10-10");
        data.add("userId", String.valueOf(1L));

        MvcResult result =mockMvc.perform(post("/updateMonth")
                        .params(data)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(RestController.class))
                .andDo(print()).andReturn();

        MockHttpServletRequest request = result.getRequest();
        String userExpireDate = String.valueOf(request.getAttribute("userExpireDate"));
        String userId = String.valueOf(request.getAttribute("userId"));
        assertNotNull(userExpireDate);
        assertNotNull(userId);

    }
}
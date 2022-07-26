package com.example.project_2th.controller;

import com.example.project_2th.controller.helper.UserHelper;
import com.example.project_2th.entity.ExerciesVideo;
import com.example.project_2th.security.config.SecurityConfig;
import com.example.project_2th.security.mock.WithMockCustomUser;
import com.example.project_2th.service.ExerciesService;
import com.example.project_2th.service.ExerciesVideoService;
import com.example.project_2th.service.PostruesService;
import com.example.project_2th.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.servlet.ServletInputStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
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



    @DisplayName("video 저장 uri")
    @Test
    @WithMockCustomUser(username = "test",role = "ROLE_USER")
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
                    .andExpect(jsonPath("$.postures[0:3].poseResult").exists())
                    .andExpect(jsonPath("$.postures[0:3].aiComment").exists())
                    .andExpect(handler().handlerType(RestControllerApi.class))
                    .andDo(print());

            verify(exerciesVideoService).selectVideoInfo(exerciesVideo.getVideoSeq());
        }

        @DisplayName("/pose-bad : bad image 를 저장하는 uri")
        @Test
        @WithMockCustomUser(username = "test",role = "ROLE_USER")
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
        @WithMockCustomUser(username = "test")
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

package com.example.project_2th.controller;

import com.example.project_2th.controller.helper.GsonLocalDateTimeAdapter;
import com.example.project_2th.controller.helper.UserHelper;
import com.example.project_2th.entity.ExerciesVideo;
import com.example.project_2th.entity.Postures;
import com.example.project_2th.response.PoseResponse;
import com.example.project_2th.security.config.SecurityConfig;
import com.example.project_2th.security.mock.WithMockCustomUser;
import com.example.project_2th.service.ExerciesService;
import com.example.project_2th.service.ExerciesVideoService;
import com.example.project_2th.service.PostruesService;
import com.example.project_2th.service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletInputStream;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
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
            List<PoseResponse> videoList = userHelper.makePose().stream()
                    .map(PoseResponse::new).collect(Collectors.toList());
            // when
            given(postruesService.selectVideoInfo(any(ExerciesVideo.class)))
                    .willReturn(videoList);

            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(LocalDate.class, new GsonLocalDateTimeAdapter())
                    .create();

            String json = gson.toJson(exerciesVideo);

            // then
            mockMvc.perform(post("/user/pose")
                            .content(json)
                            .contentType(MediaType.APPLICATION_JSON)
                            .with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.[0:3].poseResult").exists())
                    .andExpect(jsonPath("$.[0:3].aiComment").exists())
                    .andExpect(handler().handlerType(RestControllerApi.class))
                    .andDo(print());
        }

        @DisplayName("/pose-bad : bad image 를 저장하는 uri")
        @Test
        @WithMockCustomUser(username = "test",role = "ROLE_USER")
        void insertBadImage() throws Exception {
            //given
            List<String> aiComments = new ArrayList<>();
            aiComments.add("오른쪽을 들어주세요.");
            aiComments.add("왼쪽을 들어주세요.");
            MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
            );

            List<MockMultipartFile> files = new ArrayList<>();
            files.add(file);
            files.add(file);

            // when
            MvcResult result = mockMvc.perform(multipart("/user/pose-bad").file(file).file(file)
                    .param("aiComment", String.valueOf(aiComments))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(RestControllerApi.class))
                .andDo(print()).andReturn();

            // then
            MultipartHttpServletRequest request = (MultipartHttpServletRequest) result.getRequest();
            List<String> comment = Collections.singletonList(request.getParameter("aiComment"));
            List<MultipartFile> seq = request.getFiles("file");
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

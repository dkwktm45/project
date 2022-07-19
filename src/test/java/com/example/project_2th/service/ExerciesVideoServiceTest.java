package com.example.project_2th.service;


import com.example.project_2th.controller.helper.UserHelper;
import com.example.project_2th.entity.Exercies;
import com.example.project_2th.entity.ExerciesVideo;
import com.example.project_2th.entity.User;
import com.example.project_2th.repository.ExinfoRepository;
import com.example.project_2th.repository.UserRepository;
import com.example.project_2th.repository.VideoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
@Import({ExerciesService.class, ExinfoRepository.class})
public class ExerciesVideoServiceTest {

    @MockBean
    ExinfoRepository exinfoRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    VideoRepository videoRepository;

    @Mock
    ExerciesVideoService exerciesVideoService;


    protected MockHttpServletRequest request;
    protected UserHelper userHelper = new UserHelper();


    @DisplayName("videoSave service")
    @Test
    void videoSave() throws IOException {
        // given
        User user = userHelper.makeUser();
        Exercies exercies = userHelper.makeExercies();
        byte[] bytes = new byte[]{1,2};
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        request = new MockHttpServletRequest();
        request.setContent(bytes);
        ServletInputStream stream = request.getInputStream();

        // when
        Mockito.when(userRepository.findByUserId(anyLong())).thenReturn(Optional.ofNullable(user));
        Mockito.when(exinfoRepository.findByExSeq(anyLong())).thenReturn(exercies);

        exerciesVideoService = new ExerciesVideoService(userRepository,exinfoRepository,videoRepository);
        exerciesVideoService.videoSave("10",1L,1L,stream);

        // then
        Mockito.verify(userRepository).findByUserId(anyLong());
        Mockito.verify(exinfoRepository).findByExSeq(anyLong());
    }

    @DisplayName("selectVideoInfo service")
    @Test
    void selectVideoInfo() throws Exception {
        // given
        ExerciesVideo video = userHelper.makeVideo();

        // when
        Mockito.when(videoRepository.findByVideoSeq(anyLong())).thenReturn(Optional.ofNullable(video));

        // then
        exerciesVideoService = new ExerciesVideoService(userRepository,exinfoRepository,videoRepository);
        Map<String,Object> result = exerciesVideoService.selectVideoInfo(anyLong());
        assertEquals(result.size(),2);
        Mockito.verify(videoRepository).findByVideoSeq(anyLong());
    }
}

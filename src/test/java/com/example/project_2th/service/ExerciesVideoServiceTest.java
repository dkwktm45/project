package com.example.project_2th.service;


import com.example.project_2th.adapter.PostNotFound;
import com.example.project_2th.controller.helper.UserHelper;
import com.example.project_2th.entity.Exercies;
import com.example.project_2th.entity.ExerciesVideo;
import com.example.project_2th.entity.User;
import com.example.project_2th.repository.ExinfoRepository;
import com.example.project_2th.repository.UserRepository;
import com.example.project_2th.repository.VideoRepository;
import groovy.util.logging.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

    @Spy
    User user;

    protected MockHttpSession session;
    protected MockHttpServletRequest request;
    protected UserHelper userHelper;

    @DisplayName("videoSave service")
    @Test
    void videoSave() throws IOException {
        User user = userHelper.makeUser();
        Exercies exercies = userHelper.makeExercies();
        byte[] bytes = new byte[]{1,2};
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        request = new MockHttpServletRequest();
        request.setContent(bytes);
        ServletInputStream stream = request.getInputStream();

        Mockito.when(userRepository.findByUserId(1L).orElseThrow(PostNotFound::new)).thenReturn(user);
        Mockito.when(exinfoRepository.findByExSeq(1L)).thenReturn(exercies);

        exerciesVideoService = new ExerciesVideoService(userRepository,exinfoRepository,videoRepository);
        exerciesVideoService.videoSave(String.valueOf(10),1L,1L,stream);

        Mockito.verify(userRepository).findByUserId(1L);
        Mockito.verify(exinfoRepository).findByExSeq(1L);
    }

    @DisplayName("selectVideoInfo service")
    @Test
    void selectVideoInfo() throws Exception {

        this.userHelper= new UserHelper();

        Mockito.when(videoRepository.findByVideoSeq(1L))
                .thenReturn(this.userHelper.makeVideo());

        exerciesVideoService = new ExerciesVideoService(userRepository,exinfoRepository,videoRepository);
        Map<String,Object> result = exerciesVideoService.selectVideoInfo(1L);

        assertEquals(result.size(),2);
        Mockito.verify(videoRepository).findByVideoSeq(1L);
    }
}

package com.example.project_2th.service;

import com.example.project_2th.controller.MainController;
import com.example.project_2th.controller.helper.UserHelper;
import com.example.project_2th.entity.Calendar;
import com.example.project_2th.entity.Exercies;
import com.example.project_2th.entity.ExerciesVideo;
import com.example.project_2th.entity.User;
import com.example.project_2th.repository.ExinfoRepository;
import com.example.project_2th.repository.UserRepository;
import com.example.project_2th.repository.VideoRepository;
import groovy.util.logging.Slf4j;
import org.assertj.core.util.DateUtil;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@Import({ExerciesVideoService.class, ExinfoRepository.class,UserRepository.class, VideoRepository.class})
public class ExerciesServiceTest {

    @MockBean
    ExinfoRepository exinfoRepository;

    @Mock
    ExerciesService exerciesService;

    @Spy
    User user;

    protected MockHttpSession session;
    protected MockHttpServletRequest request;
    protected UserHelper userHelper;

    @Test
    @DisplayName("exerciesInfo service : 운동의 대한 정보를 가져온다.")
    void exerciesInfo() {
        Exercies exercies = userHelper.makeExercies();

        Mockito.when(exinfoRepository.save(exercies)).thenReturn(null);

        Mockito.when(exinfoRepository.findByOne(exercies.getUser().getUserId(),exercies.getExName())).thenReturn(exercies);

        exerciesService = new ExerciesService(exinfoRepository);

        Exercies exinfo = exerciesService.exerciesInfo(exercies);

        assertEquals(exinfo.getExCount(),exercies.getExCount());

        Mockito.verify(exinfoRepository).save(exercies);
        Mockito.verify(exinfoRepository).findByOne(exercies.getUser().getUserId(),exercies.getExName());
    }

    @Test
    @DisplayName("calendarExinfo : 날짜에 맞는 운동 정보")
    void calendarExinfo() {
        Calendar calendar = userHelper.makeCalendar();
        List<Exercies> exercies = userHelper.makeExinfos(userHelper.makeUser());
        Mockito.when(exinfoRepository.findExDay(calendar.getUser().getUserId(),calendar.getExDay()))
                .thenReturn(exercies);

        exerciesService = new ExerciesService(exinfoRepository);
        List<Exercies> exerciesResult =  exerciesService.calendarExinfo(calendar);

        assertEquals(exerciesResult,exercies);
        Mockito.verify(exinfoRepository).findExDay(calendar.getUser().getUserId(),calendar.getExDay());
    }
}

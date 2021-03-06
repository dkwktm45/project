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
import com.example.project_2th.response.ExerciesResponse;
import groovy.util.logging.Slf4j;
import org.assertj.core.util.DateUtil;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.refEq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
public class ExerciesServiceTest {

    @Mock
    ExinfoRepository exinfoRepository;

    @InjectMocks
    ExerciesService exerciesService;
    protected UserHelper userHelper = new UserHelper();

    @Test
    @DisplayName("exerciesInfo service : ????????? ?????? ????????? ????????????.")
    void exerciesInfo() {
        //given
        Exercies exercies = userHelper.makeExercies();

        // when
        exerciesService = new ExerciesService(exinfoRepository);
        ExerciesResponse exinfo = exerciesService.exerciesInfo(exercies);

        // then
        assertEquals(exinfo.getExCount(),exercies.getExCount());

    }

    @Test
    @DisplayName("calendarExinfo : ????????? ?????? ?????? ??????")
    void calendarExinfo() {
        // given
        Calendar calendar = userHelper.makeCalendar();
        List<Exercies> exercies = userHelper.makeExinfos();

        // when
        Mockito.when(exinfoRepository.findExDay(calendar.getUser().getUserId(),calendar.getExDay()))
                .thenReturn(exercies);
        exercies.forEach(ExerciesResponse::new);

        exerciesService = new ExerciesService(exinfoRepository);
        List<ExerciesResponse> exerciesResult =  exerciesService.calendarExinfo(calendar);

        // then
        assertEquals(exerciesResult.get(0).getExCount(),exercies.get(0).getExCount());
        Mockito.verify(exinfoRepository).findExDay(calendar.getUser().getUserId(),calendar.getExDay());
    }
}

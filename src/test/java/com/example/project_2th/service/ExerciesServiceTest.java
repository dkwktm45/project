package com.example.project_2th.service;

import com.example.project_2th.controller.helper.UserHelper;
import com.example.project_2th.entity.Exercies;
import com.example.project_2th.entity.User;
import com.example.project_2th.repository.ExinfoRepository;
import com.example.project_2th.response.ExerciesResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
 class ExerciesServiceTest {

    @Mock
    ExinfoRepository exinfoRepository;
    @Mock
    EntityManager em;
    @InjectMocks
    ExerciesService exerciesService;
    protected UserHelper userHelper = new UserHelper();

    @Test
    @DisplayName("exerciesInfo service : 운동의 대한 정보를 가져온다.")
    void exerciesInfo() {
        //given
        Exercies exercies = userHelper.makeExercies();

        // when
        exerciesService = new ExerciesService(exinfoRepository,em);
        ExerciesResponse exinfo = exerciesService.exerciesInfo(exercies);

        // then
        assertEquals(exinfo.getExCount(),exercies.getExCount());

    }
    @DisplayName("exerciesInfo service : userId를 통한 운동 정보를 가져온다.")
    @Test
    void calendarResponse() {
        //given
        User user = User.builder().userId(0L).exercieVideosList(userHelper.makeVideos()).exerciesList(userHelper.makeExinfos())
                .userName("김화순").loginNumber("1234").userPhone("010-2345-1234")
                .userBirthdate(LocalDate.parse("1963-07-16")).userExpireDate(LocalDate.parse("2022-08-20"))
                .managerYn(0).videoYn(1).userGym("해운대").build();
        Mockito.when(exinfoRepository.findByidExinfo(anyLong()))
                .thenReturn(user.getExerciesList());

        // when
        exerciesService = new ExerciesService(exinfoRepository,em);
        List<ExerciesResponse> exinfo = exerciesService.calendarResponse(user);

        // then
        assertNotNull(exinfo);
    }

}

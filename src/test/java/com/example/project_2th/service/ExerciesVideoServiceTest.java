package com.example.project_2th.service;


import com.example.project_2th.controller.helper.UserHelper;
import com.example.project_2th.entity.User;
import com.example.project_2th.repository.PosturesRepository;
import com.example.project_2th.repository.VideoRepository;
import com.example.project_2th.response.VideoResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static java.util.Optional.ofNullable;

@ExtendWith(MockitoExtension.class)
class ExerciesVideoServiceTest {

    @Mock
    PosturesRepository posturesRepository;

    @Mock
    VideoRepository videoRepository;

    @InjectMocks
    ExerciesVideoService exerciesVideoService;


    protected UserHelper userHelper = new UserHelper();

    private User user;
    @DisplayName("load User")
    @Test
    void loadUser(){
        user = User.builder().userId(1L).exercieVideosList(userHelper.makeVideos()).exerciesList(userHelper.makeExinfos()).userName("김화순").loginNumber("1234").userPhone("010-2345-1234")
                .userBirthdate(LocalDate.parse("1963-07-16")).userExpireDate(LocalDate.parse("2022-08-20"))
                .managerYn(1).role("ROLE_ADMIN").videoYn(1).userGym("해운대").build();

        Mockito.when(videoRepository.findUserVideos(user.getUserGym(),"ROLE_USER")).thenReturn(ofNullable(userHelper.makeVideos()));


        exerciesVideoService = new ExerciesVideoService(posturesRepository,videoRepository);

        List<VideoResponse> responses = exerciesVideoService.loadUser(user);

        Mockito.verify(videoRepository).findUserVideos(user.getUserGym(),"ROLE_USER");
    }


    @DisplayName("videoUpdate service")
    @Test
    void videoUpdate() throws IOException {
/*        // given
        User user = userHelper.makeUser();
        Exercies exercies = userHelper.makeExercies();
        byte[] bytes = new byte[]{1,2};
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        request = new MockHttpServletRequest();
        request.setContent(bytes);
        ServletInputStream stream = request.getInputStream();

        // when
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));
        Mockito.when(exinfoRepository.findByExSeq(anyLong())).thenReturn(exercies);

        exerciesVideoService = new ExerciesVideoService(userRepository,exinfoRepository,videoRepository);
        //exerciesVideoService.videoSave("10",1L,1L,stream);

        // then
        Mockito.verify(userRepository).findById(anyLong());
        Mockito.verify(exinfoRepository).findByExSeq(anyLong());*/
    }

}

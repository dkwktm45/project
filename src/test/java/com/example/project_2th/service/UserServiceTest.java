package com.example.project_2th.service;

import com.example.project_2th.controller.helper.UserHelper;
import com.example.project_2th.entity.User;
import com.example.project_2th.exception.PostNotFound;
import com.example.project_2th.repository.UserRepository;
import com.example.project_2th.response.UserResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static java.util.Optional.ofNullable;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
 class UserServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder encoder;

    @InjectMocks
    UserService userService;

    @Spy
    User user;

    protected UserHelper userHelper = new UserHelper();


    @Nested
    @DisplayName("join service")
    class join{
        User loginUser = null;
        @BeforeEach
        void setUp(){
            user = User.builder().userName("김태롱").userPhone("010-6457-2354")
                    .userBirthdate(LocalDate.parse(("1963-07-16"))).userExpireDate(LocalDate.parse("2022-08-20"))
                    .managerYn(0).videoYn(1).userGym("해운대").build();

            loginUser = User.builder().userId(1L).loginNumber("2354").userName("김태롱").userPhone("010-6457-2354")
                    .userBirthdate(LocalDate.parse("1963-07-16")).userExpireDate(LocalDate.parse("2022-08-20"))
                    .managerYn(0).videoYn(1).userGym("해운대").build();
            encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        }
        @AfterEach
        void afterSet(){
            user = null;
            loginUser = null;
        }
        @DisplayName("join success")
        @Test
        void test4(){
            // when
            Mockito.when(userRepository.findByUserIdAndUserGym(loginUser.getUserPhone(),loginUser.getUserGym())).thenReturn(null);
            Mockito.when(userRepository.save(any(User.class))).thenReturn(null);

            // then
            UserService userService = new UserService(userRepository,encoder);
            userService.join(user);
            verify(userRepository).findByUserIdAndUserGym(
                    refEq(loginUser.getUserPhone()),refEq(loginUser.getUserGym()));
        }


        @DisplayName("join fail")
        @Test
        void test5(){
            // when
            Mockito.lenient().when(userRepository.findByUserIdAndUserGym(
                    anyString()
                    ,anyString())).thenReturn(ofNullable(loginUser));
            Mockito.lenient().when(userRepository.save(loginUser)).thenReturn(null);
            UserService userService = new UserService(userRepository,encoder);

            // then
            IllegalStateException e = assertThrows(IllegalStateException.class,() ->{
                userService.join(user);
            });
            assertEquals("존재하는 회원입니다.",e.getMessage());
        }
    }

    @DisplayName("reload Service")
    @Test
    void test7(){
        Map<String, Object> adminInfo = userHelper.makeAdmin();
        User admin = (User) adminInfo.get("user");
        List<User> users = (List<User>) adminInfo.get("userList");
        Mockito.when(userRepository.findByUserGymAndManagerYn(admin.getUserGym(), admin.getManagerYn()-1)).thenReturn(ofNullable(users));

        UserService userService = new UserService(userRepository,encoder);
        List<UserResponse> resultUsers = userService.reLoadMember(admin);

        assertEquals(4,resultUsers.size());
        verify(userRepository).findByUserGymAndManagerYn(admin.getUserGym(), admin.getManagerYn()-1);
    }

    @DisplayName("updateMonth success")
    @Test
    void updateMonthSuccess(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("userExpireDate","2022-10-22");
        request.addParameter("userId", String.valueOf(1L));

        user = User.builder().userId(1L).userName("김화순").userPhone("9696")
                .userBirthdate(LocalDate.parse("1963-07-16")).userExpireDate(LocalDate.parse("2022-10-12"))
                .managerYn(0).videoYn(1).userGym("해운대").build();

        Mockito.when(userRepository.findById(user.getUserId())).thenReturn(ofNullable(user));


        UserService userService = new UserService(userRepository,encoder);

        userService.updateMonth(request);

        Mockito.verify(userRepository).findById(user.getUserId());
    }
    @DisplayName("updateMonth fail")
    @Test
    void updateMonthFail(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("userExpireDate","2022-10-22");
        request.addParameter("userId", String.valueOf(1L));

        user = User.builder().userId(1L).userName("김화순").userPhone("9696")
                .userBirthdate(LocalDate.parse("1963-07-16")).userExpireDate(LocalDate.parse("2022-10-12"))
                .managerYn(0).videoYn(1).userGym("해운대").build();

        Mockito.when(userRepository.findById(user.getUserId())).thenReturn(ofNullable(null));


        UserService userService = new UserService(userRepository,encoder);

        assertThrows(PostNotFound.class,() ->{
            userService.updateMonth(request);
        });
        Mockito.verify(userRepository).findById(user.getUserId());
    }


}

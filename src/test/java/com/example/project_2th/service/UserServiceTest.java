package com.example.project_2th.service;

import com.example.project_2th.adapter.PostNotFound;
import com.example.project_2th.controller.helper.UserHelper;
import com.example.project_2th.entity.Calendar;
import com.example.project_2th.entity.Exercies;
import com.example.project_2th.entity.ExerciesVideo;
import com.example.project_2th.entity.User;
import com.example.project_2th.repository.UserRepository;
import com.example.project_2th.response.UserResponse;
import org.junit.After;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@Import({UserService.class,UserRepository.class})
public class UserServiceTest {

    @MockBean
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Spy
    User user;

    protected MockHttpSession session;
    protected MockHttpServletRequest request;
    protected UserHelper userHelper = new UserHelper();
    @Nested
    @DisplayName("login service")
    class login{

        @DisplayName("user login")
        @Test
        void test1(){
            user = userHelper.makeUser();

            Mockito.when(userRepository.findByLoginNumberAndUserGym(
                    user.getUserPhone()
                    , user.getUserGym())).thenReturn(user);

            UserService userService = new UserService(userRepository);

            Map<String, Object> result = userService.filterLogin(user.getUserPhone(), user.getUserGym());
            assertEquals(result.get("user"),user);
            verify(userRepository).findByLoginNumberAndUserGym( user.getUserPhone()
                    , user.getUserGym());
        }

        @DisplayName("admin login")
        @Test
        void test2(){
            Map<String , Object> adminInfo = userHelper.makeAdmin();
            user = (User) adminInfo.get("user");
            List<User> users = (List<User>) adminInfo.get("userList");

            Mockito.when(userRepository.findByLoginNumberAndUserGym(
                    user.getLoginNumber()
                    , user.getUserGym())).thenReturn(user);
            Mockito.when(userRepository.findByUserGymAndManagerYn(
                    user.getUserGym()
                    , user.getManagerYn()-1)).thenReturn(users);


            UserService userService = new UserService(userRepository);

            Map<String, Object> result = userService.filterLogin(user.getLoginNumber(), user.getUserGym());
            assertEquals(result.get("user"),user);
            assertEquals(result.get("userList"),adminInfo.get("userList"));

            verify(userRepository).findByLoginNumberAndUserGym( user.getLoginNumber()
                    , user.getUserGym());
            verify(userRepository).findByUserGymAndManagerYn( user.getUserGym()
                    , user.getManagerYn()-1);
        }

        @DisplayName("user null")
        @Test
        void test3(){
            user = userHelper.makeUser();

            Mockito.when(userRepository.findByLoginNumberAndUserGym(
                    user.getUserPhone()
                    , user.getUserGym())).thenReturn(null);

            UserService userService = new UserService(userRepository);

            Map<String, Object> result = userService.filterLogin(user.getUserPhone(), user.getUserGym());
            assertNull(result);
            verify(userRepository).findByLoginNumberAndUserGym(user.getUserPhone()
                    , user.getUserGym());
        }
    }



    @Nested
    @DisplayName("user 운동정보 및 캘린더 정보")
    class userInfo{
        List<User> users = null;

        @BeforeEach
        void setUp(){
            users = userHelper.makeUsers();
            user = User.builder().userId(1L).userName("김화순").loginNumber("1234").userPhone("010-2345-1234")
                    .userBirthdate(LocalDate.parse("1963-07-16")).userExpireDate(LocalDate.parse("2022-08-20"))
                    .managerYn(0).videoYn(1).userGym("해운대").build();
        }
        @AfterEach
        void afterSet(){
            user = null;
            users = null;
        }
        @DisplayName("캘린더정보 4개를 가져온다.")
        @Test
        void test2(){

            Mockito.when(userRepository.findAllByFetchJoin()).thenReturn(users);

            UserService userService = new UserService(userRepository);

            List<Calendar> exinfo = userService.infoCalendar(user);

            assertEquals(4,exinfo.size());
            verify(userRepository).findAllByFetchJoin();
        }
        @DisplayName("사용자의 video exercies 정보들을 가져온다.")
        @Test
        void test3(){
            Map<String,Object> map = new HashMap<>();
            List<Exercies> exinfoList = userHelper.makeExinfos();
            List<ExerciesVideo> videoList = userHelper.makeVideos();
            map.put("videoList",videoList);
            map.put("exinfoList",exinfoList);

            Mockito.when(userRepository.findAllByFetchJoin()).thenReturn(users);

            UserService userService = new UserService(userRepository);

            Map<String, Object> exinfo = userService.infoRecord(user);
            assertEquals(exinfo.size(),2);
            assertNotNull(exinfo.get("videoList"));
            assertNotNull(exinfo.get("exinfoList"));
            verify(userRepository).findAllByFetchJoin();
        }
    }


    @Nested
    @DisplayName("join service")
    class join{
        User loginUser = null;
        @BeforeEach
        void setUp(){
            user = User.builder().userName("김태롱").userPhone("010-6457-2354")
                    .userBirthdate(LocalDate.parse(("1963-07-16"))).userExpireDate(LocalDate.parse("2022-08-20"))
                    .managerYn(0).videoYn(1).userGym("해운대").build();

            loginUser = User.builder().loginNumber("2354").userName("김태롱").userPhone("010-6457-2354")
                    .userBirthdate(LocalDate.parse("1963-07-16")).userExpireDate(LocalDate.parse("2022-08-20"))
                    .managerYn(0).videoYn(1).userGym("해운대").build();
        }
        @AfterEach
        void afterSet(){
            user = null;
            loginUser = null;
        }
        @DisplayName("join success")
        @Test
        void test4(){

            Mockito.when(userRepository.findByUserIdAndUserGym(loginUser.getUserPhone(),loginUser.getUserGym())).thenReturn(null);
            Mockito.when(userRepository.save(any(User.class))).thenReturn(null);

            UserService userService = new UserService(userRepository);
            userService.join(user);

            verify(userRepository).findByUserIdAndUserGym(
                    refEq(loginUser.getUserPhone()),refEq(loginUser.getUserGym()));
            verify(userRepository).save(any(User.class));

        }

        @DisplayName("join fail")
        @Test
        void test5(){

            Mockito.when(userRepository.findByUserIdAndUserGym(
                    anyString()
                    ,anyString())).thenReturn(loginUser);
            Mockito.when(userRepository.save(loginUser)).thenReturn(null);
            try{
                UserService userService = new UserService(userRepository);
                userService.join(user);
            }catch (IllegalStateException e){
                System.out.println(e.getMessage());
            }

            verify(userRepository).findByUserIdAndUserGym(anyString(),anyString());
        }
    }




    @DisplayName("reload Service")
    @Test
    void test7(){
        Map<String, Object> adminInfo = userHelper.makeAdmin();
        User admin = (User) adminInfo.get("user");
        List<User> users = (List<User>) adminInfo.get("userList");
        Mockito.when(userRepository.findByUserGymAndManagerYn(admin.getUserGym(), admin.getManagerYn()-1)).thenReturn(users);

        UserService userService = new UserService(userRepository);
        List<UserResponse> resultUsers = userService.reLoadMember(admin);

        assertEquals(resultUsers.size(),4);
        verify(userRepository).findByUserGymAndManagerYn(admin.getUserGym(), admin.getManagerYn()-1);
    }

    @DisplayName("updateMonth service")
    @Test
    void test8(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("userExpireDate","2022-10-22");
        request.addParameter("userId", String.valueOf(1L));
        user = User.builder().userId(1L).userName("김화순").userPhone("9696")
                .userBirthdate(LocalDate.parse("1963-07-16")).userExpireDate(LocalDate.parse("2022-10-22"))
                .managerYn(0).videoYn(1).userGym("해운대").build();
        Mockito.when(userRepository.findByUserId(user.getUserId())).thenReturn(Optional.ofNullable(user));

        Mockito.when(userRepository.save(user)).thenReturn(null);

        UserService userService = new UserService(userRepository);

        userService.updateMonth(request);

        Mockito.verify(userRepository).findByUserId(user.getUserId());
        Mockito.verify(userRepository).save(refEq(user));
    }
    @After
    public void clear(){
        request.clearAttributes();
        request = null;
    }
}

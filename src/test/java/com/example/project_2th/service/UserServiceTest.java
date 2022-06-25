package com.example.project_2th.service;

import com.example.project_2th.controller.helper.UserHelper;
import com.example.project_2th.entity.Calendar;
import com.example.project_2th.entity.Exercies;
import com.example.project_2th.entity.ExerciesVideo;
import com.example.project_2th.entity.User;
import com.example.project_2th.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    protected UserHelper userHelper;
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
                    user.getUserPhone()
                    , user.getUserGym())).thenReturn(user);
            Mockito.when(userRepository.findByUserGymAndManagerYn(
                    user.getUserGym()
                    , user.getManagerYn()-1)).thenReturn(users);

            UserService userService = new UserService(userRepository);

            Map<String, Object> result = userService.filterLogin(user.getUserPhone(), user.getUserGym());
            assertEquals(result.get("user"),user);
            assertEquals(result.get("userList"),adminInfo.get("userList"));

            verify(userRepository).findByLoginNumberAndUserGym( user.getUserPhone()
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

    @DisplayName("캘린더정보 4개를 가져온다.")
    @Test
    void test2(){
        List<User> users = userHelper.makeUsers();

        Mockito.when(userRepository.findAllByFetchJoin()).thenReturn(users);

        UserService userService = new UserService(userRepository);

        List<Calendar> exinfo = userService.infoCalendar(userHelper.makeUser());

        assertEquals(4,exinfo.size());
        verify(userRepository).findAllByFetchJoin();
    }

    @DisplayName("사용자의 video exercies 정보들을 가져온다.")
    @Test
    void test3(){
        List<User> users = userHelper.makeUsers();
        user = userHelper.makeUser();

        Map<String,Object> map = new HashMap<>();
        List<Exercies> exinfoList = this.userHelper.makeExinfos();
        List<ExerciesVideo> videoList = this.userHelper.makeVideos();
        map.put("videoList",videoList);
        map.put("exinfoList",exinfoList);

        Mockito.when(userRepository.findAllByFetchJoin()).thenReturn(users);

        UserService userService = new UserService(userRepository);

        Map<String, Object> exinfo = userService.infoRecord(userHelper.makeUser());
        assertEquals(exinfo.size(),2);
        assertNotNull(exinfo.get("videoList"));
        assertNotNull(exinfo.get("exinfoList"));
        verify(userRepository).findAllByFetchJoin();
    }

    @Nested
    @DisplayName("join service")
    class join{
        @DisplayName("join success")
        @Test
        void test4(){
            user = User.builder().name("김태롱").userPhone("010-6457-2354")
                    .userBirthdate(java.sql.Date.valueOf("1963-07-16")).userExpireDate(java.sql.Date.valueOf("2022-08-20"))
                    .managerYn(0).videoYn(1).userGym("해운대").build();

            User loginUser = User.builder().loginNumber("2354").name("김태롱").userPhone("010-6457-2354")
                    .userBirthdate(java.sql.Date.valueOf("1963-07-16")).userExpireDate(java.sql.Date.valueOf("2022-08-20"))
                    .managerYn(0).videoYn(1).userGym("해운대").build();


            Mockito.when(userRepository.findByUserIdAndUserGym(loginUser.getUserPhone(),loginUser.getUserGym())).thenReturn(null);
            Mockito.when(userRepository.save(loginUser)).thenReturn(null);

            UserService userService = new UserService(userRepository);
            userService.join(user);

            verify(userRepository).findByUserIdAndUserGym(loginUser.getUserPhone(),loginUser.getUserGym());
            verify(userRepository).save(refEq(loginUser));

        }

        @DisplayName("join fail")
        @Test
        void test5(){
            user = User.builder().name("김태롱").userPhone("010-6457-2354")
                    .userBirthdate(java.sql.Date.valueOf("1963-07-16")).userExpireDate(java.sql.Date.valueOf("2022-08-20"))
                    .managerYn(0).videoYn(1).userGym("해운대").build();

            User loginUser = User.builder().loginNumber("2354").name("김태롱").userPhone("010-6457-2354")
                    .userBirthdate(java.sql.Date.valueOf("1963-07-16")).userExpireDate(java.sql.Date.valueOf("2022-08-20"))
                    .managerYn(0).videoYn(1).userGym("해운대").build();


            Mockito.when(userRepository.findByUserIdAndUserGym(loginUser.getUserPhone(),loginUser.getUserGym())).thenReturn(refEq(loginUser));
            Mockito.when(userRepository.save(loginUser)).thenReturn(null);
            try{

                UserService userService = new UserService(userRepository);
                userService.join(user);
            }catch (IllegalStateException e){
                System.out.println(e.getMessage());
            }

            verify(userRepository).findByUserIdAndUserGym(loginUser.getUserPhone(),loginUser.getUserGym());
            verify(userRepository).save(refEq(loginUser));

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
        List<User> resultUsers = userService.reLoadMember(admin);

        assertEquals(resultUsers.size(),4);
        verify(userRepository).findByUserGymAndManagerYn(admin.getUserGym(), admin.getManagerYn()-1);
    }

    @DisplayName("updateMonth service")
    @Test
    void test8(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("userExpireDate","2022-10-22");
        request.addParameter("userId", String.valueOf(1L));
        user = User.builder().userId(1L).name("김화순").userPhone("9696")
                .userBirthdate(java.sql.Date.valueOf("1963-07-16")).userExpireDate(java.sql.Date.valueOf("2022-10-22"))
                .managerYn(0).videoYn(1).userGym("해운대").build();
        Mockito.when(userRepository.findByUserId(user.getUserId())).thenReturn(userHelper.makeUser());

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

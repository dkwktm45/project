package com.example.project_2th.service;

import com.example.project_2th.entity.Calendar;
import com.example.project_2th.entity.User;
import com.example.project_2th.repository.CalendarRepositroy;
import com.example.project_2th.repository.ExinfoRepository;
import com.example.project_2th.repository.UserRepository;
import groovy.util.logging.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@Slf4j
public class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CalendarRepositroy calendarRepositroy;

    @Autowired
    ExinfoRepository exinfoRepository;

    @Autowired
    EntityManager em;



    @BeforeEach
    void before(){
    }

    @DisplayName("사용자의 정보를 하나 가져온다.")
    @Test
    void test1(){
        User user = userService.login("4903","해운대");
        assertEquals("이진영",user.getUserName());
    }

    @DisplayName("캘린더정보들을 가져온다.")
    @Test
    void test2(){
        User user = userService.login("4903","해운대");
        List<Calendar> exinfo =userService.infoCalendar(user);
        try{
            assertEquals(1,exinfo.size());
        }catch (Exception e){
            System.out.println("사이즈에 따라 달라질 수 있습니다.");
        }
    }

    @DisplayName("사용자의 달력 기록들 불러온다.")
    @Test
    void test3(){
        User user = userService.login("4903","해운대");

        Map<String, Object> exinfo =userService.infoRecord(user);
        assertEquals(2,exinfo.size());
    }
    @DisplayName("사용자의 운동 기록들 불러온다.")
    @Test
    void test4(){
        User user = userService.login("4903","해운대");
        //userService.findExercies(user).forEach(System.out::println);

    }

    @DisplayName("회원들의 비디오 정보들을 가져온다.")
    @Test
    void test5(){
        User user = userService.login("1234","해운대");

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = (MockHttpSession) request.getSession();
        session.setAttribute("user",user);
        userService.filterLogin(user,session);
        session = (MockHttpSession) request.getSession();

        List<User> userList = (List<User>) session.getAttribute("userList");
        userList.get(0).getExercieVideosList().forEach(System.out::println);

    }

}

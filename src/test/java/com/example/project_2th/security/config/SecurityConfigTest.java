package com.example.project_2th.security.config;

import com.example.project_2th.controller.helper.UserHelper;
import com.example.project_2th.entity.User;
import com.example.project_2th.repository.UserRepository;
import com.example.project_2th.security.service.UserContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class SecurityConfigTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    private MockMvc mvc;
    @BeforeEach
    public void setup(){
        mvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .apply(springSecurity())
                .build();

    }

    @Test
    @DisplayName("user login 테스트")
    public void login_test() throws Exception {
        User user = User.builder().userName("김화순").loginNumber(passwordEncoder.encode("1234")).userPhone("010-2345-1234")
                .userBirthdate(LocalDate.parse("1963-07-16")).userExpireDate(LocalDate.parse("2022-08-20"))
                .managerYn(0).videoYn(1).userGym("해운대").role("USER").build();
        userRepository.save(user);

        // given
        String userId = "010-2345-1234";
        String password = "1234";

        // when
        mvc.perform(formLogin("/loginInsert").user("userPhone",userId)
                        .password("loginNumber",password))
                .andDo(print())
                // then
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/main"));
    }
    @Test
    @DisplayName("manager login 테스트")
    public void admin_test() throws Exception {
        User user = User.builder().userName("김화순").loginNumber(passwordEncoder.encode("1234")).userPhone("010-2345-1234")
                .userBirthdate(LocalDate.parse("1963-07-16")).userExpireDate(LocalDate.parse("2022-08-20"))
                .managerYn(1).videoYn(1).userGym("해운대").role("USER").build();
        userRepository.save(user);

        // given
        String userId = "010-2345-1234";
        String password = "1234";

        // when
        mvc.perform(formLogin("/loginInsert").user("userPhone",userId)
                        .password("loginNumber",password))
                .andDo(print())
                // then
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"));
    }

    @Test
    @DisplayName("login fail테스트")
    public void fail_test() throws Exception {
        User user = User.builder().userName("김화순").loginNumber(passwordEncoder.encode("1234")).userPhone("010-2345-1234")
                .userBirthdate(LocalDate.parse("1963-07-16")).userExpireDate(LocalDate.parse("2022-08-20"))
                .managerYn(1).videoYn(1).userGym("해운대").role("USER").build();
        userRepository.save(user);

        // given
        String userId = "010-2345-1234";
        String password = "1235";

        // when
        mvc.perform(formLogin("/loginInsert").user("userPhone",userId)
                        .password("loginNumber",password))
                .andDo(print())
                // then
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"));
    }
}

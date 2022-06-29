package com.example.project_2th.repository;

import com.example.project_2th.adapter.PostNotFound;
import com.example.project_2th.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Date;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class userPostures {

    @Autowired
    UserRepository userRepository;

    @DisplayName("해당 번호에 해당하는 자세 정보를 가져온다.")
    @Test
    @Transactional
    public void insertEx(){
        String date = String.valueOf(new Date());
        System.out.println("date : "+date);


        User user = User.builder().userName("김화순").loginNumber("1234").userPhone("010-2345-1234")
                .userBirthdate(LocalDate.parse(date)).userExpireDate(LocalDate.parse((date)))
                .managerYn(0).videoYn(1).userGym("해운대").build();
        userRepository.save(user);
        User result = userRepository.findByUserId(1L).orElseThrow(PostNotFound::new);
        System.out.println("user : " + result.getUserBirthdate());
    }
}

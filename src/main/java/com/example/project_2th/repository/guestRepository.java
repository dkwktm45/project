package com.example.project_2th.repository;

import com.example.project_2th.entity.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Column;
import java.util.List;

public interface guestRepository extends JpaRepository<user,Long> {


    @Query(value = "select * from user where user_phone = :user_phone and user_gym = :user_gym"
            ,nativeQuery=true)
    user findByUserIdAndUserGym(
            @Param("user_phone") String userPhone,
            @Param("user_gym") String userGym);

    user findByUserPhoneAndUserGym(String userPhone, String userGym);


    user findByUserId(Long userId);
}

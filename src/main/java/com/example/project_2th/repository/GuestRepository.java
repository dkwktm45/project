package com.example.project_2th.repository;

import com.example.project_2th.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GuestRepository extends JpaRepository<User,Long> {


    @Query(value = "select * from user where user_phone = :user_phone and user_gym = :user_gym"
            ,nativeQuery=true)
    User findByUserIdAndUserGym(
            @Param("user_phone") String userPhone,
            @Param("user_gym") String userGym);

    User findByUserPhoneAndUserGym(String userPhone, String userGym);

    @EntityGraph(attributePaths = {"exercieVideosList"})
    User findByUserId(Long userId);
}

package com.example.project_2th.repository;

import com.example.project_2th.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {


    @Nullable
    @Query(value = "select * from user where user_phone = :user_phone and user_gym = :user_gym"
            ,nativeQuery=true)
    User findByUserIdAndUserGym(
            @Param("user_phone") String userPhone,
            @Param("user_gym") String userGym);

    Optional<User> findByLoginNumberAndUserGym(String loginNumber, String userGym);

    Optional<User> findByUserId(Long userId);

    User findByLoginNumber(String loginNumber);

    @EntityGraph(attributePaths = {"calendarList"})
    @Query(value = "select u from User u")
    List<User> findAllByFetchJoin();

    @Nullable
    @EntityGraph(attributePaths = {"exercieVideosList"})
    List<User> findByUserGymAndManagerYn(String gym, int yn);
}

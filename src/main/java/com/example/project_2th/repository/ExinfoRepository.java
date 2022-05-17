package com.example.project_2th.repository;

import com.example.project_2th.entity.UserExercies;
import com.example.project_2th.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public interface ExinfoRepository extends JpaRepository<UserExercies,Long> {
    List<UserExercies> findByExDay(Date exDay);

    @Query(value = "SELECT * FROM user_exercies uex " +
            "where uex.user_id= (select user_id from user u " +
            "where u.user_id = :userId) and uex.ex_name = :exName order by uex.ex_day desc limit 1",nativeQuery = true)
    UserExercies findByOne(
            @Param("userId") Long userId,
            @Param("exName") String exName);

    //void inertCNT(String cnt, int ex_seq);

}

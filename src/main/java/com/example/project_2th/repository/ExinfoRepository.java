package com.example.project_2th.repository;

import com.example.project_2th.entity.UserExercies;
import com.example.project_2th.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public interface ExinfoRepository extends JpaRepository<UserExercies,Long> {
    List<UserExercies> findByExDay(Date exDay);
}

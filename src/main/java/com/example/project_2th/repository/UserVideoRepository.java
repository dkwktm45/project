package com.example.project_2th.repository;

import com.example.project_2th.entity.UserExercieVideos;
import com.example.project_2th.entity.UserExercies;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserVideoRepository extends JpaRepository<UserExercieVideos,Long> {

    UserExercieVideos findByUserExercies(UserExercies exinfo);
}

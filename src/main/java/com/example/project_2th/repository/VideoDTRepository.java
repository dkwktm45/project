package com.example.project_2th.repository;

import com.example.project_2th.entity.UserExercieVideos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoDTRepository extends JpaRepository<UserExercieVideos,Long> {
}

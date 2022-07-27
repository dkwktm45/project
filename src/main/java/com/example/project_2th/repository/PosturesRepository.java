package com.example.project_2th.repository;

import com.example.project_2th.entity.ExerciesVideo;
import com.example.project_2th.entity.Postures;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PosturesRepository extends JpaRepository<Postures,Long> {
    Optional<List<Postures>> findByExerciesVideo(ExerciesVideo exerciesVideo);
}

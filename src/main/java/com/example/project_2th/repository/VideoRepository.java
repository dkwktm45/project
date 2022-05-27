package com.example.project_2th.repository;

import com.example.project_2th.entity.ExerciesVideo;
import com.example.project_2th.entity.Exercies;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<ExerciesVideo,Long> {

    ExerciesVideo findByExercies(Exercies exinfo);

    ExerciesVideo findByVideoSeq(Long id);
}

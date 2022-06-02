package com.example.project_2th.repository;

import com.example.project_2th.entity.ExerciesVideo;
import com.example.project_2th.entity.Exercies;
import com.example.project_2th.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface VideoRepository extends JpaRepository<ExerciesVideo,Long> {

    ExerciesVideo findByExercies(Exercies exinfo);

    @EntityGraph(attributePaths = {"exercies","postures"})
    ExerciesVideo findByVideoSeq(Long id);


    List<ExerciesVideo> findAll();

}

package com.example.project_2th.repository;

import com.example.project_2th.entity.ExerciesVideo;
import com.example.project_2th.entity.Exercies;
import com.example.project_2th.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface VideoRepository extends JpaRepository<ExerciesVideo,Long> {

    ExerciesVideo findByExercies(Exercies exinfo);

    @EntityGraph(attributePaths = {"exercies","postures"})
    Optional<ExerciesVideo> findByVideoSeq(Long id);

    @EntityGraph(attributePaths = {"exercies"})
    Optional<List<ExerciesVideo>> findByUser(User user);

    List<ExerciesVideo> findAll();

    @Query(value = "select * from user_exercies_videos video " +
            "where video.user_id = (select user_id from user u "+
            "where u.user_gym = :userGym and u.role = :role)",nativeQuery = true)
    Optional<List<ExerciesVideo>> findUserVideos(@Param("userGym") String userGym
                                       ,@Param("role") String role);
}

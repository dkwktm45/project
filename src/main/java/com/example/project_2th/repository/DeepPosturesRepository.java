package com.example.project_2th.repository;

import com.example.project_2th.entity.UserPostures;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeepPosturesRepository extends JpaRepository<UserPostures,Long> {
}

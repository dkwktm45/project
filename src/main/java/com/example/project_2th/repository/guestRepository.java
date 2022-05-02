package com.example.project_2th.repository;

import com.example.project_2th.entity.user;
import org.springframework.data.jpa.repository.JpaRepository;

public interface guestRepository extends JpaRepository<user,Long> {
}

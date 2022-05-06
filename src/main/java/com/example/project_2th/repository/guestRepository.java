package com.example.project_2th.repository;

import com.example.project_2th.entity.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Column;

public interface guestRepository extends JpaRepository<user,Long> {

    user findByUserIdAndUserGym(Long user_id,String user_gym);
}

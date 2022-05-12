package com.example.project_2th.repository;

import com.example.project_2th.entity.UserCalendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCalendarRepositroy extends JpaRepository<UserCalendar,Long> {
    List<UserCalendarRepositroy> findByUser(Long userId);
}

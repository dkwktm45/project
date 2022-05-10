package com.example.project_2th.repository;

import com.example.project_2th.entity.UserCalendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface userCalendarRepositroy extends JpaRepository<UserCalendar,Long> {
    List<userCalendarRepositroy> findByUser(Long userId);
}

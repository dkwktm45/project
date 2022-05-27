package com.example.project_2th.repository;

import com.example.project_2th.entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalendarRepositroy extends JpaRepository<Calendar,Long> {
    List<CalendarRepositroy> findByUser(Long userId);
}

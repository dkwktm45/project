package com.example.project_2th.service;


import com.example.project_2th.entity.Calendar;
import com.example.project_2th.entity.Exercies;
import com.example.project_2th.entity.ExerciesVideo;
import com.example.project_2th.entity.User;
import com.example.project_2th.repository.ExinfoRepository;
import com.example.project_2th.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ExerciesService {

    @Autowired
    private final ExinfoRepository exinfoRepository;
    private final Logger logger = LoggerFactory.getLogger(ExerciesService.class);

    public Exercies exerciesInfo(Exercies exercies){
        exercies.setExDay(Date.valueOf(LocalDate.now()));
        exinfoRepository.save(exercies);
        Exercies exinfo = exinfoRepository.findByOne(exercies.getUser().getUserId(), exercies.getExName());
        return exercies;
    }

    public List<Exercies> calendarExinfo(Calendar calendar){
        return exinfoRepository.findExDay(calendar.getUser().getUserId(),calendar.getExDay());
    }

    public List<Exercies> findExercies(String exkinds){
        return exinfoRepository.findByExKinds(exkinds);
    }
}

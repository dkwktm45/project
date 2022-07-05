package com.example.project_2th.service;


import com.example.project_2th.entity.Calendar;
import com.example.project_2th.entity.Exercies;
import com.example.project_2th.repository.ExinfoRepository;
import com.example.project_2th.response.ExerciesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ExerciesService {

    @Autowired
    private final ExinfoRepository exinfoRepository;
    private final Logger logger = LoggerFactory.getLogger(ExerciesService.class);

    public ExerciesResponse exerciesInfo(Exercies exercies){
        exercies.setExDay(Date.valueOf(LocalDate.now()));
        ExerciesResponse response = new ExerciesResponse(exercies);
        return response;
    }

    public List<ExerciesResponse> calendarExinfo(Calendar calendar){
        return exinfoRepository.findExDay(calendar.getUser().getUserId(),calendar.getExDay())
                .stream().map(ExerciesResponse::new)
                .collect(Collectors.toList());
    }

    public List<Exercies> findExercies(String exkinds){
        return exinfoRepository.findByExKinds(exkinds);
    }
}

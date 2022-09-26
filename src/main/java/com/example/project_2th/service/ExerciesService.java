package com.example.project_2th.service;


import com.example.project_2th.entity.Exercies;
import com.example.project_2th.entity.User;
import com.example.project_2th.repository.ExinfoRepository;
import com.example.project_2th.response.ExerciesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
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
    @Autowired
    private final EntityManager em;

    public ExerciesResponse exerciesInfo(Exercies exercies){
        logger.info("exerciesInfo perform");
        exercies.setExDay(LocalDate.now());
        em.persist(exercies);

        return new ExerciesResponse(exercies);
    }

    public List<ExerciesResponse> calendarResponse(User user){
        logger.info("calendarResponse perform");
        return exinfoRepository.findByidExinfo(user.getUserId()).stream().map(ExerciesResponse::new).collect(Collectors.toList());
    }
}

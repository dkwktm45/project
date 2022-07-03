package com.example.project_2th.response;

import com.example.project_2th.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Builder
@RequiredArgsConstructor
public class CalendarResponse {
    private final Long id;

    private final Date exDay;
    private final String timeDiff;
    private final String userWeight;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id" ,nullable = false)
    private final UserResponse user;

}

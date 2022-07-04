package com.example.project_2th.response;

import com.example.project_2th.entity.Calendar;
import com.example.project_2th.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Getter
public class CalendarResponse {
    private Long id;

    private Date exDay;
    private String timeDiff;
    private String userWeight;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id" ,nullable = false)
    private UserResponse user;

    public CalendarResponse(Calendar calendar) {
        this.id = calendar.getId();
        this.exDay = calendar.getExDay();
        this.timeDiff = calendar.getTimeDiff();
        this.userWeight = calendar.getUserWeight();
        this.user = new UserResponse(calendar.getUser());
    }
}

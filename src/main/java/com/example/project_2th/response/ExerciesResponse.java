package com.example.project_2th.response;

import com.example.project_2th.entity.Exercies;
import com.example.project_2th.entity.ExerciesVideo;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ExerciesResponse {
    private Long exSeq;
    private LocalDate exDay;
    private String exName;
    private String exKinds;
    private String userSet;
    private String exCount;
    private String cnt;

    @JsonBackReference
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserResponse user;

    public ExerciesResponse(Exercies exercies) {
        this.exSeq = exercies.getExSeq();
        this.exDay = exercies.getExDay();
        this.exName = exercies.getExName();
        this.exKinds = exercies.getExKinds();
        this.user = new UserResponse(exercies.getUser());
        this.userSet = exercies.getUserSet();
        this.exCount = exercies.getExCount();
    }

}

package com.example.project_2th.response;

import com.example.project_2th.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;

@Builder
@Getter
public class ExerciesResponse {
    private Long exSeq;
    private Date exDay;
    private String exName;
    private String exKinds;
    private String userSet;
    private String exCount;
    private String cnt;

    @JsonBackReference
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}

package com.example.project_2th.entity;


import com.example.project_2th.response.ExerciesResponse;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_exercies")
public class Exercies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long exSeq;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
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
    private User user;

}

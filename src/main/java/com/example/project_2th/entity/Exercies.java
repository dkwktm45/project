package com.example.project_2th.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_exercies")
public class Exercies {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long exSeq;
    private Date exDay;
    private String exName;
    private String exKinds;
    private String userSet;
    private String exCount;
    private String cnt;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}

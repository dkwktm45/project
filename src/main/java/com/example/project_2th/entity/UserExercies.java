package com.example.project_2th.entity;


import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString(callSuper = true)
@Table(name = "user_exercies")
public class UserExercies {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long exSeq;

    @ManyToOne
    @ToString.Exclude
    private User user;


    private Date exDay;
    private String exName;
    private String exKinds;
    private String userSet;
    private String exCount;
    private String cnt;

}

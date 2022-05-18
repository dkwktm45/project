package com.example.project_2th.entity;


import lombok.*;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_exercies")
public class UserExercies {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long exSeq;
    private Date exDay;
    private String exName;
    private String exKinds;
    private String userSet;
    private String exCount;
    private String cnt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}

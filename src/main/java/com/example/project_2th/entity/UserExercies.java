package com.example.project_2th.entity;


import lombok.*;

import javax.persistence.*;
import java.util.List;

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
    private Long ex_seq;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private user user_id;

    private String ex_name;
    private String ex_kinds;
    private String user_set;
    private String ex_count;
    private String ex_date;
    private String cnt;
    private Long id;

}

package com.example.project_2th.entity;


import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString(callSuper = true)
@Table(name = "user_exercies")
public class UserExercies {
    @Id
    private Long ex_seq;
    private String user_id;
    private String ex_name;
    private String ex_kinds;
    private String user_set;
    private String ex_count;
    private String ex_date;
    private String cnt;
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private user user;
}

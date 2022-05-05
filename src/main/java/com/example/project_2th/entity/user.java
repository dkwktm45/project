package com.example.project_2th.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "user")
@ToString(callSuper = true)
public class user {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long user_id;


    private String user_name;
    private String user_phone;
    private String user_gym;
    private String user_expire_date;
    private String user_birthdate;
    private String contents;
    private String day;
    private String time;
    private int manager_yn;
    private String ad_comment;
    private int month;

    @OneToMany
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private List<UserExercieVideos> userExercieVideosList;

    @OneToMany
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private List<UserExercies> userExerciesList;



}

package com.example.project_2th.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "user")
@EqualsAndHashCode(callSuper = false)
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
    private List<UserExercies> userExerciesList;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<UserExercieVideos> VideosList;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<UserCalendar> calendarList;
}

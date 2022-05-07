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
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;


    private String userName;
    private String userPhone;
    @Column(name = "user_gym")
    private String userGym;
    private String userExpireDate;
    private String userBirthdate;
    private String contents;
    private String day;
    private String time;
    private int managerYn;
    private String adComment;
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

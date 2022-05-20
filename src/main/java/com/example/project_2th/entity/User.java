package com.example.project_2th.entity;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;


    private String userName;
    private String userPhone;
    @Column(name = "user_gym")
    private String userGym;
    private Date userExpireDate;
    private String userBirthdate;
    private String contents;
    private String day;
    private String time;
    private int managerYn;
    private String adComment;
    private int month;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", insertable = false,updatable = false)
    @ToString.Exclude
    private final List<UserExercies> userExerciesList = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "user_id", insertable = false,updatable = false)
    @ToString.Exclude
    private final List<UserCalendar> calendarList = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "user_id", insertable = false,updatable = false)
    @ToString.Exclude
    private final List<UserExercieVideos> exercieVideosList = new ArrayList<>();
}

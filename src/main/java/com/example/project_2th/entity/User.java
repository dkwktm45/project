package com.example.project_2th.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

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
@ToString(exclude = {"userExerciesList","calendarList","exercieVideosList"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;


    private String userName;
    private String userPhone;
    private String userGym;
    private Date userExpireDate;
    private String userBirthdate;
    private String contents;
    private String day;
    private String time;
    private int managerYn;
    private String adComment;
    private int month;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", insertable = false,updatable = false)
    private final List<Exercies> exerciesList = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", insertable = false,updatable = false)
    private final List<Calendar> calendarList = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "user_id", insertable = false,updatable = false)
    private final List<ExerciesVideo> exercieVideosList = new ArrayList<>();
}

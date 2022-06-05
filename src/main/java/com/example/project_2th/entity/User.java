package com.example.project_2th.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "user")
@JsonIgnoreProperties(value = {"exerciesList","calendarList","exercieVideosList"} , allowSetters = true)
@ToString(exclude = {"exerciesList","calendarList","exercieVideosList"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;


    private String userName;
    private String userPhone;
    private String userGym;
    private Date userExpireDate;
    private Date userBirthdate;
    private String contents;
    private String day;
    private String time;
    private int managerYn;
    private int videoYn;
    private String adComment;
    private int month;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false,updatable = false)
    private final List<Exercies> exerciesList = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", insertable = false,updatable = false)
    private final List<Calendar> calendarList = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "user_id", insertable = false,updatable = false)
    private final List<ExerciesVideo> exercieVideosList = new ArrayList<>();

    @Builder
    public User(String userName ,String userPhone,Date userExpireDate,
                Date userBirthdate,int managerYn,int videoYn,String userGym){
        this.userName = userName;
        this.userPhone = userPhone;
        this.userExpireDate = userExpireDate;
        this.userBirthdate = userBirthdate;
        this.managerYn = managerYn;
        this.videoYn = videoYn;
        this.userGym = userGym;
    }
}

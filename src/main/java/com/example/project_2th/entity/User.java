package com.example.project_2th.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.lang.Nullable;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
@Builder
@JsonIgnoreProperties(value = {"exerciesList","calendarList","exercieVideosList"} , allowSetters = true)
@ToString(exclude = {"exerciesList","calendarList","exercieVideosList"})
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String loginNumber;
    private String userName;
    private String userPhone;
    private String userGym;

    @Setter
    private Date userExpireDate;
    private Date userBirthdate;

    @Nullable
    private String contents;
    private int managerYn;
    private int videoYn;


    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY )
    @Builder.Default
    @JoinColumn(name = "user_id",updatable = false,insertable = false)
    private final List<Exercies> exerciesList = new ArrayList<>();

    @JsonManagedReference
    @OneToMany
    @Builder.Default
    @JoinColumn(name = "user_id",updatable = false,insertable = false)
    private final List<Calendar> calendarList = new ArrayList<>();

    @OneToMany
    @Builder.Default
    @JoinColumn(name = "user_id",updatable = false,insertable = false)
    private final List<ExerciesVideo> exercieVideosList = new ArrayList<>();


}

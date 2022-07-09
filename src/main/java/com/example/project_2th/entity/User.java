package com.example.project_2th.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "user")
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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate userExpireDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate userBirthdate;

    @Nullable
    private String contents;
    private int managerYn;
    private int videoYn;
    private String role;

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

    public UserEditor.UserEditorBuilder toEditor(){
        return UserEditor.builder().userPhone(userPhone).userExpireDate(userExpireDate);
    }

    public void editor(UserEditor userEditor){
        this.userExpireDate = userEditor.getUserExpireDate();
        this.loginNumber = userEditor.getUserPhone();
    }

    public Object valid(){
        if(this.userId !=null){
            throw new IllegalStateException("존재하는 회원입니다.");
        }
        return null;
    }

}

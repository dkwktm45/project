package com.example.project_2th.response;

import com.example.project_2th.entity.Calendar;
import com.example.project_2th.entity.Exercies;
import com.example.project_2th.entity.ExerciesVideo;
import com.example.project_2th.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@Getter
public class UserResponse{

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

    public UserResponse(User user){
        this.userId = user.getUserId();
        this.loginNumber = user.getLoginNumber();
        this.userName = user.getUserName();
        this.userPhone = user.getUserPhone();
        this.contents = user.getContents();
        this.userGym = user.getUserGym();
        this.userBirthdate = user.getUserBirthdate();
        this.userExpireDate = user.getUserExpireDate();
        this.managerYn = user.getManagerYn();
        this.videoYn = user.getVideoYn();
        this.exercieVideosList = new ArrayList(user.getExercieVideosList());
        this.exerciesList = new ArrayList(user.getExerciesList());
        this.calendarList = new ArrayList(user.getCalendarList());
    }

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",updatable = false,insertable = false)
    private List<ExerciesResponse> exerciesList = new ArrayList<>();

    @JsonIgnore
    @JsonManagedReference
    @OneToMany
    @JoinColumn(name = "user_id",updatable = false,insertable = false)
    private List<CalendarResponse> calendarList = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "user_id",updatable = false,insertable = false)
    private List<VideoResponse> exercieVideosList = new ArrayList<>();

}

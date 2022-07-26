package com.example.project_2th.response;

import com.example.project_2th.entity.ExerciesVideo;
import com.example.project_2th.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
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

    }

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",updatable = false,insertable = false)
    @JsonIgnore
    private List<ExerciesResponse> exerciesList = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "user_id",updatable = false,insertable = false)
    @JsonIgnore
    private List<VideoResponse> exercieVideosList = new ArrayList<>();

    @Builder
    public void setExercieVideosList(List<ExerciesVideo> exercieVideosList) {
        this.exercieVideosList = new ArrayList(exercieVideosList);
    }
}

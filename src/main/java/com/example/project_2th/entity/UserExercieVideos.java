package com.example.project_2th.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@ToString(callSuper = true)
@Table(name = "user_exercies_videos")
public class UserExercieVideos {
    @Id
    @GeneratedValue
    private Long videoSeq;

    private String fileName;
    private String videoDate;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ex_seq")
    private UserExercies userExercies;

    @OneToMany
    @JsonBackReference
    @JoinColumn(name = "video_seq", insertable = false,updatable = false)
    @ToString.Exclude
    private List<UserPostures> userPostures = new ArrayList<>();

    //getter 재정의를 통한 null 처리
    public List<UserPostures> getUserPostures(){
        return this.userPostures == null ? new ArrayList<>() : this.userPostures;
    }
}

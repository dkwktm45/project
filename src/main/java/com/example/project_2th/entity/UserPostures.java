package com.example.project_2th.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_postures")
public class UserPostures {

    @Id
    @GeneratedValue
    private Long postureSeq;

    private int videoTime;
    private String poseResult;
    private String aiComment;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "video_seq")
    private UserExercieVideos userExercieVideos;

}

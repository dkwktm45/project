package com.example.project_2th.entity;

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

    private int video_time;
    private String pose_result;
    private String ai_comment;

    @ManyToOne
    @JoinColumn(name = "video_seq")
    private UserExercieVideos userExercieVideos;

}

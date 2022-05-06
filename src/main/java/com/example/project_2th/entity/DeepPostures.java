package com.example.project_2th.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "deep_postures")
public class DeepPostures {

    @Id
    @GeneratedValue
    private Long deep_seq;

    @ManyToOne
    @JoinColumn(name = "video_seq")
    private UserExercieVideos video_seq;

    private int video_time;
    private String pose_result;
    private String ai_comment;
}

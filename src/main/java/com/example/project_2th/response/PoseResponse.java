package com.example.project_2th.response;

import com.example.project_2th.entity.Exercies;
import com.example.project_2th.entity.ExerciesVideo;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Getter
@Builder
public class PoseResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postureSeq;

    private int videoTime;
    private String poseResult;
    private String aiComment;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "video_seq")
    private VideoResponse exerciesVideo;

}
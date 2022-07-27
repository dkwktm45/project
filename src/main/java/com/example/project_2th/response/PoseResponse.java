package com.example.project_2th.response;

import com.example.project_2th.entity.Postures;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;

import javax.persistence.*;

@Getter
public class PoseResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postureSeq;

    private String poseResult;
    private String aiComment;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "video_seq")
    private VideoResponse exerciesVideo;

    public PoseResponse(Postures postures) {
        this.poseResult = postures.getPoseResult();
        this.aiComment = postures.getAiComment();
    }
}
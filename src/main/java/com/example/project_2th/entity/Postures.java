package com.example.project_2th.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "user_postures")
public class Postures {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postureSeq;

    private String poseResult;
    private String aiComment;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "video_seq")
    private ExerciesVideo exerciesVideo;

}

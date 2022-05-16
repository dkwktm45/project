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
@Table(name = "deep_postures")
public class DeepPostures {

    @Id
    @GeneratedValue
    private Long deep_seq;



    private int video_time;
    private String pose_result;
    private String ai_comment;

    @ManyToOne
    @ToString.Exclude
    private UserExercieVideos video_seq;
}

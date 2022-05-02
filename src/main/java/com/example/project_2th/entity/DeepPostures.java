package com.example.project_2th.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "deep_postures")
public class DeepPostures {

    @Id
    @GeneratedValue
    private Long deep_seq;

    private Long video_seq;
    private int video_time;
    private String pose_result;
    private String ai_comment;
}

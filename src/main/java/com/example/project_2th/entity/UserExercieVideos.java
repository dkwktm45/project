package com.example.project_2th.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@Table(name = "user_exercies_videos")
public class UserExercieVideos {
    @Id
    @GeneratedValue
    private Long video_seq;
    private Long ex_seq;
    private String file_name;
    private String user_id;
    private String video_date;
}


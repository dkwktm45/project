package com.example.project_2th.entity;

import lombok.*;

import javax.persistence.*;

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
    private Long video_seq;
    private Long ex_seq;
    private String file_name;
    private String user_name;
    private String video_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private user user;
}


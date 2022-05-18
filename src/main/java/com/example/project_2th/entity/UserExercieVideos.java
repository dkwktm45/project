package com.example.project_2th.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

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
    private Long videoSeq;

    private String fileName;
    private String videoDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ex_seq")
    private UserExercies userExercies;

    @OneToMany
    @JoinColumn(name = "video_seq", insertable = false,updatable = false)
    @ToString.Exclude
    private List<UserPostures> userPostures;

}

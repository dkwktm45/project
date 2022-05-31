package com.example.project_2th.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@ToString(exclude = {"user","exercies","postures"})
@Table(name = "user_exercies_videos")
public class ExerciesVideo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long videoSeq;

    private String fileName;
    private Date videoDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ex_seq")
    private Exercies exercies;

    @Builder.Default
    @OneToMany
    @JsonBackReference
    @JoinColumn(name = "video_seq", insertable = false,updatable = false)
    private List<Postures> postures = new ArrayList<>();

    //getter 재정의를 통한 null 처리
    public List<Postures> getPostures(){
        return this.postures == null ? new ArrayList<>() : this.postures;
    }
}

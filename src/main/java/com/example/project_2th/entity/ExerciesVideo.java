package com.example.project_2th.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@ToString(callSuper = true)
@Table(name = "user_exercies_videos")
public class ExerciesVideo {
    @Id
    @GeneratedValue
    private Long videoSeq;

    private String fileName;
    private String videoDate;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ex_seq")
    private Exercies exercies;

    @OneToMany
    @JsonBackReference
    @JoinColumn(name = "video_seq", insertable = false,updatable = false)
    @ToString.Exclude
    private List<Postures> postures = new ArrayList<>();

    //getter 재정의를 통한 null 처리
    public List<Postures> getPostures(){
        return this.postures == null ? new ArrayList<>() : this.postures;
    }
}
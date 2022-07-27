package com.example.project_2th.response;

import com.example.project_2th.entity.ExerciesVideo;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class VideoResponse{
    private Long videoSeq;

    private String fileName;
    private LocalDate videoDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private UserResponse user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ex_seq")
    private ExerciesResponse exercies;

    @OneToMany
    @JsonManagedReference
    @JoinColumn(name = "video_seq", insertable = false,updatable = false)
    private List<PoseResponse> postures = new ArrayList<>();

    //getter 재정의를 통한 null 처리
    public List<PoseResponse> getPostures(){
        return this.postures == null ? new ArrayList<>() : this.postures;
    }

    public VideoResponse(ExerciesVideo exerciesVideo){
        this.videoSeq = exerciesVideo.getVideoSeq();
        this.fileName = exerciesVideo.getFileName();
        this.videoDate = exerciesVideo.getVideoDate();
        this.exercies = new ExerciesResponse(exerciesVideo.getExercies());
        this.user = new UserResponse(exerciesVideo.getUser());
    }
}

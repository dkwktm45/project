package com.example.project_2th.response;

import com.example.project_2th.entity.Exercies;
import com.example.project_2th.entity.Postures;
import com.example.project_2th.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class VideoResponse {
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

    @OneToMany
    @JsonManagedReference
    @JoinColumn(name = "video_seq", insertable = false,updatable = false)
    private List<Postures> postures = new ArrayList<>();

    //getter 재정의를 통한 null 처리
    public List<Postures> getPostures(){
        return this.postures == null ? new ArrayList<>() : this.postures;
    }

}

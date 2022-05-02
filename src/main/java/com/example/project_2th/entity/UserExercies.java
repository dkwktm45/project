package com.example.project_2th.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_exercies")
public class UserExercies {
    @Id
    private Long ex_seq;
    private String user_id;
    private String ex_name;
    private String ex_kinds;
    private String user_set;
    private String ex_count;
    private String ex_date;
    private String cnt;
    private Long id;

}

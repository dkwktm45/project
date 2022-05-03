package com.example.project_2th.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "user")
public class user {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long user_id;

    private String user_name;
    private String user_phone;
    private String user_gym;
    private String user_expire_date;
    private String user_birthdate;
    private String contents;
    private String day;
    private String time;
    private int manager_yn;
    private String ad_comment;
    private int month;
}

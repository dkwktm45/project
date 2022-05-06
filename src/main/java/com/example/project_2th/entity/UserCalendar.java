package com.example.project_2th.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserCalendar {
    // 달력에 들어갈 거였네...시바
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private user user_id;

    private String ex_day;
    private String timediff;
    private String user_weight;
}

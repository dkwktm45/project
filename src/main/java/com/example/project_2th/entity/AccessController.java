package com.example.project_2th.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AccessController {
    // 이게 뭐지?
    @Id
    private Long id;
    private Long user_id;
    private String ex_day;
    private String timediff;
    private String user_weight;
}

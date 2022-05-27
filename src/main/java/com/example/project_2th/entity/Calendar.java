package com.example.project_2th.entity;

// lombok.* 하면 필요없는 부분들 까지 import가 된다.
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
// entity에는 setter를 하지 않는다.
// --> 보존되어야하기 때문에!!!
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_calendar")
public class Calendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동으로 숫자가 증가
    private Long id;

    private Date exDay;

    private String timeDiff;
    private String userWeight;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id" ,nullable = false)
    private User user;

}

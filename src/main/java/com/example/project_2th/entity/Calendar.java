package com.example.project_2th.entity;

// lombok.* 하면 필요없는 부분들 까지 import가 된다.
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
// entity에는 setter를 하지 않는다.
// --> 보존되어야하기 때문에!!!

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "user_calendar")
public class Calendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동으로 숫자가 증가
    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate exDay;

    private String timeDiff;
    private String userWeight;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id" ,nullable = false)
    private User user;

}

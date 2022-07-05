package com.example.project_2th.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
@Builder
@Getter
public class UserEditor {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate userExpireDate;
    private final String userPhone;

}

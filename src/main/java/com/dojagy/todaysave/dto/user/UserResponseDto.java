package com.dojagy.todaysave.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class UserResponseDto {
    private Long id;
    private String email;
    private String nickname;
    private LocalDate birthday;
    private String gender;
    private String grade;
    private LocalDateTime createDt;
}

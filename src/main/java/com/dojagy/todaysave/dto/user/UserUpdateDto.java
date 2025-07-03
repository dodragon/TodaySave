package com.dojagy.todaysave.dto.user;

import com.dojagy.todaysave.entity.value.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateDto {
    private LocalDate birthday;
    private Gender gender;
}

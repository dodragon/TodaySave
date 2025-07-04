package com.dojagy.todaysave.data.dto.user;

import com.dojagy.todaysave.data.entity.value.Gender;
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

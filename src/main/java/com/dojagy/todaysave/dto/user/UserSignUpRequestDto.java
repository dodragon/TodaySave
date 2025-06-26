package com.dojagy.todaysave.dto.user;

import com.dojagy.todaysave.entity.value.Gender;
import com.dojagy.todaysave.entity.value.Sns;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Setter
@Getter
@ToString
public class UserSignUpRequestDto {
    @NotNull(message = "SNS 타입은 필수입니다.")
    private Sns sns;

    @NotBlank(message = "SNS KEY는 필수입니다.")
    private String snsKey;

    @Email(message = "유효한 이메일 형식이 아닙니다.")
    @NotBlank(message = "이메일은 필수입니다.")
    private String email;

    @NotBlank(message = "닉네임은 필수입니다.")
    @Size(min = 2, max = 8, message = "닉네임은 2자 이상 8자 이하로 입력해주세요.")
    private String nickname;

    private LocalDate birthday;
    private Gender gender;
}

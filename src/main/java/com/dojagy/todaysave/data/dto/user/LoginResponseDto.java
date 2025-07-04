package com.dojagy.todaysave.data.dto.user;

import com.dojagy.todaysave.data.dto.TokenInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LoginResponseDto {
    private UserResponseDto userInfo;
    private TokenInfo tokenInfo;
}

package com.dojagy.todaysave.dto.user;

import lombok.Data;

@Data
public class TokenReissueRequestDto {
    private String accessToken;
    private String refreshToken;
}

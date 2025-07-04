package com.dojagy.todaysave.data.dto.user;

import lombok.Data;

@Data
public class TokenReissueRequestDto {
    private String accessToken;
    private String refreshToken;
}

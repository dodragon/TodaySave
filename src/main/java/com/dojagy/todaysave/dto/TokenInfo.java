package com.dojagy.todaysave.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenInfo {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}

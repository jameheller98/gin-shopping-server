package com.gin.usermicroservice.resource.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RefreshTokenResponse {
    private String token;
    private String refreshToken;
    private String tokenType = "Bearer";

    @Builder
    public RefreshTokenResponse(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }
}

package com.gin.usermicroservice.resource.dto;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
public class RefreshTokenResponse {
    private String token;
    private String refreshToken;
    private String tokenType = "Bearer";

    @Builder
    public RefreshTokenResponse(String token , String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }
}

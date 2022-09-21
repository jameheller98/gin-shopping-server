package com.gin.usermicroservice.resource.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RefreshTokenRequest {
    private String refreshToken;
}

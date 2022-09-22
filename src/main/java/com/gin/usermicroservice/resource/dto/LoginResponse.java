package com.gin.usermicroservice.resource.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LoginResponse {
    private RefreshTokenResponse token;
    private UserResponse user;
}

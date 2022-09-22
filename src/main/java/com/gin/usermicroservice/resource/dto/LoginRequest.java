package com.gin.usermicroservice.resource.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LoginRequest {
    private String email;
    private String password;
}

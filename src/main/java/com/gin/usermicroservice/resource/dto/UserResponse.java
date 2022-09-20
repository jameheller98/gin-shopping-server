package com.gin.usermicroservice.resource.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserResponse {
    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private List<String> roles;
}

package com.gin.usermicroservice.service.map;

import com.gin.usermicroservice.domain.UserEntity;
import com.gin.usermicroservice.resource.dto.*;
import com.gin.usermicroservice.security.UserDetailsImpl;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public class AuthMapper {
    private static AuthMapper loginMapper;

    public static AuthMapper getInstance() {
        if (loginMapper == null) {
            loginMapper = new AuthMapper();
        }

        return loginMapper;
    }

    public UserEntity toUserEntity(RegisterRequest registerRequest) {
        return UserEntity.builder()
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .build();
    }

    public MessageResponse toRegisterResponse(String msg) {
        return MessageResponse.builder().msg(msg).build();
    }

    public LoginResponse toLoginResponse(String token, String refreshToken, UserDetailsImpl userDetails, List<String> roles) {
        return LoginResponse.builder()
                .token(RefreshTokenResponse.builder()
                        .token(token)
                        .refreshToken(refreshToken)
                        .build())
                .user(UserResponse.builder()
                        .id(userDetails.getId())
                        .email(userDetails.getEmail())
                        .firstName(userDetails.getFirstName())
                        .lastName(userDetails.getLastName())
                        .roles(roles)
                        .build())
                .build();
    }

    public UserResponse toUserResponse(UserDetailsImpl userDetails) {
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return UserResponse.builder()
                .id(userDetails.getId())
                .email(userDetails.getEmail())
                .firstName(userDetails.getFirstName())
                .lastName(userDetails.getLastName())
                .roles(roles)
                .build();
    }
}

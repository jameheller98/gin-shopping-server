package com.gin.usermicroservice.service.map;

import com.gin.usermicroservice.domain.UserEntity;
import com.gin.usermicroservice.resource.dto.LoginResponse;
import com.gin.usermicroservice.resource.dto.RegisterRequest;
import com.gin.usermicroservice.resource.dto.RegisterResponse;
import com.gin.usermicroservice.resource.dto.UserResponse;
import com.gin.usermicroservice.security.UserDetailsImpl;

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
        UserEntity userEntity = UserEntity.builder()
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .build();

        return userEntity;
    }

    public RegisterResponse toRegisterResponse(String msg) {
        RegisterResponse registerResponse = new RegisterResponse();

        registerResponse.setMsg(msg);

        return registerResponse;
    }

    public LoginResponse toLoginResponse(String jwt) {
        LoginResponse loginResponse = new LoginResponse();

        loginResponse.setJwt(jwt);

        return loginResponse;
    }

    public UserResponse toUserResponse(UserDetailsImpl userDetails) {
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
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

package com.gin.usermicroservice.service;

import com.gin.usermicroservice.resource.dto.LoginRequest;
import com.gin.usermicroservice.resource.dto.LoginResponse;
import com.gin.usermicroservice.resource.dto.MessageResponse;
import com.gin.usermicroservice.resource.dto.RegisterRequest;

public interface AuthService {
    LoginResponse loginUser(LoginRequest loginRequest);

    MessageResponse registerUser(RegisterRequest registerRequest);

    MessageResponse logoutUser(Long userId);
}

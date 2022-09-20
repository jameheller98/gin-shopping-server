package com.gin.usermicroservice.service;

import com.gin.usermicroservice.resource.dto.LoginRequest;
import com.gin.usermicroservice.resource.dto.LoginResponse;
import com.gin.usermicroservice.resource.dto.RegisterRequest;
import com.gin.usermicroservice.resource.dto.RegisterResponse;

public interface AuthService {
    LoginResponse loginUser(LoginRequest loginRequest);

    RegisterResponse registerUser(RegisterRequest registerRequest);
}

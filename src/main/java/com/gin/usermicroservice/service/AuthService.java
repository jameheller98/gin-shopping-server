package com.gin.usermicroservice.service;

import com.gin.usermicroservice.resource.dto.*;

public interface AuthService {
    LoginResponse loginUser(LoginRequest loginRequest);

    RegisterResponse registerUser(RegisterRequest registerRequest);

    MessageResponse logoutUser();
}

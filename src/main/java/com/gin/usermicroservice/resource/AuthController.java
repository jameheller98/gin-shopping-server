package com.gin.usermicroservice.resource;

import com.gin.usermicroservice.resource.dto.*;
import com.gin.usermicroservice.service.AuthService;
import com.gin.usermicroservice.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "https://gin-shopping-client.vercel.app", maxAge = 3600)
//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/gin/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public LoginResponse loginUser(@RequestBody LoginRequest loginRequest) {
        return authService.loginUser(loginRequest);
    }

    @PostMapping("/register")
    public RegisterResponse registerUser(@RequestBody RegisterRequest registerRequest) {
        return authService.registerUser(registerRequest);
    }

    @PostMapping("/refresh-token")
    public RefreshTokenResponse registerUser(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return refreshTokenService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public MessageResponse logoutUser() {
        return authService.logoutUser();
    }
}

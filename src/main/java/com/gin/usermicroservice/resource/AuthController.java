package com.gin.usermicroservice.resource;

import com.gin.usermicroservice.resource.dto.LoginRequest;
import com.gin.usermicroservice.resource.dto.LoginResponse;
import com.gin.usermicroservice.resource.dto.RegisterRequest;
import com.gin.usermicroservice.resource.dto.RegisterResponse;
import com.gin.usermicroservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "https://gin-shopping-client.vercel.app", maxAge = 3600)
//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/gin/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public LoginResponse loginUser(@RequestBody LoginRequest loginRequest) {
        return authService.loginUser(loginRequest);
    }

    @PostMapping("/register")
    public RegisterResponse registerUser(@RequestBody RegisterRequest registerRequest) {
        return authService.registerUser(registerRequest);
    }
}

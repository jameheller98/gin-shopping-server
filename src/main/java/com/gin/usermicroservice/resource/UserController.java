package com.gin.usermicroservice.resource;

import com.gin.usermicroservice.resource.dto.UserResponse;
import com.gin.usermicroservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "https://gin-shopping-client.vercel.app", maxAge = 3600)
//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/gin/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/info")
    @PreAuthorize("hasRole('USER')")
    public UserResponse getInfoUser() {
        return userService.getInfoUser();
    }
}

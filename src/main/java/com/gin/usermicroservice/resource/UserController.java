package com.gin.usermicroservice.resource;

import com.gin.usermicroservice.resource.dto.UserResponse;
import com.gin.usermicroservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/gin/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/info")
    public UserResponse getInfoUser(@RequestHeader(name = "Authorization") String token) {
        return userService.getInfoUser(token);
    }
}

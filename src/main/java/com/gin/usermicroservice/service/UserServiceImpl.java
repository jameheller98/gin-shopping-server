package com.gin.usermicroservice.service;

import com.gin.usermicroservice.resource.dto.UserResponse;
import com.gin.usermicroservice.security.UserDetailsImpl;
import com.gin.usermicroservice.service.map.AuthMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Override
    public UserResponse getInfoUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return AuthMapper.getInstance().toUserResponse(userDetails);
    }
}

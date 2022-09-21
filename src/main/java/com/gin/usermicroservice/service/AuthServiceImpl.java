package com.gin.usermicroservice.service;

import com.gin.usermicroservice.domain.RefreshTokenEntity;
import com.gin.usermicroservice.domain.RoleEntity;
import com.gin.usermicroservice.domain.RoleEnum;
import com.gin.usermicroservice.domain.UserEntity;
import com.gin.usermicroservice.repository.RoleRepository;
import com.gin.usermicroservice.repository.UserRepository;
import com.gin.usermicroservice.resource.dto.*;
import com.gin.usermicroservice.security.JwtUtils;
import com.gin.usermicroservice.security.UserDetailsImpl;
import com.gin.usermicroservice.service.map.AuthMapper;
import com.gin.usermicroservice.validate.UserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RefreshTokenService refreshTokenService;

    @Override
    public LoginResponse loginUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(userDetails);

        RefreshTokenEntity refreshTokenEntity = refreshTokenService.createRefreshToken(userDetails.getId());

        return AuthMapper.getInstance().toLoginResponse(jwt, refreshTokenEntity.getToken());
    }

    @Override
    public RegisterResponse registerUser(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email is already taken.");
        }

        UserHelper.registerValidate(registerRequest);

        registerRequest.setPassword(encoder.encode(registerRequest.getPassword()));
        UserEntity userEntity = AuthMapper.getInstance().toUserEntity(registerRequest);

        Set<String> strRoles = registerRequest.getRoles();
        Set<RoleEntity> roles = new HashSet<>();

        if (strRoles == null) {
            RoleEntity userRoleEntity = roleRepository.findByName(RoleEnum.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException(("Error: Role is not found.")));
            roles.add(userRoleEntity);
        } else {
            strRoles.forEach(role -> {
                if ("admin".equals(role)) {
                    RoleEntity adminRoleEntity = roleRepository.findByName(RoleEnum.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRoleEntity);
                } else {
                    RoleEntity userRoleEntity = roleRepository.findByName(RoleEnum.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRoleEntity);
                }
            });
        }

        userEntity.setRoleEntities(roles);
        userRepository.save(userEntity);

        return AuthMapper.getInstance().toRegisterResponse("User registered successfully!");
    }

    @Override
    public MessageResponse logoutUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Long userId = userDetails.getId();

        refreshTokenService.deleteByUserId(userId);

        return new MessageResponse("Log out successful!");
    }
}

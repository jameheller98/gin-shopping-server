package com.gin.usermicroservice.service;

import com.gin.usermicroservice.domain.RoleEntity;
import com.gin.usermicroservice.domain.RoleEnum;
import com.gin.usermicroservice.domain.UserEntity;
import com.gin.usermicroservice.repository.RoleRepository;
import com.gin.usermicroservice.repository.UserRepository;
import com.gin.usermicroservice.resource.dto.LoginRequest;
import com.gin.usermicroservice.resource.dto.LoginResponse;
import com.gin.usermicroservice.resource.dto.RegisterRequest;
import com.gin.usermicroservice.resource.dto.RegisterResponse;
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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public LoginResponse loginUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return AuthMapper.getInstance().toLoginResponse(jwt);
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
}

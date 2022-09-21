package com.gin.usermicroservice.service;

import com.gin.usermicroservice.domain.RefreshTokenEntity;
import com.gin.usermicroservice.resource.dto.RefreshTokenRequest;
import com.gin.usermicroservice.resource.dto.RefreshTokenResponse;

import java.util.Optional;

public interface RefreshTokenService {

    Optional<RefreshTokenEntity> findByToken(String token);

    RefreshTokenEntity createRefreshToken(Long userId);

    RefreshTokenEntity verifyExpiration(RefreshTokenEntity refreshTokenEntity);

    RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

    int deleteByUserId(Long userId);
}

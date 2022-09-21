package com.gin.usermicroservice.service;

import com.gin.usermicroservice.domain.RefreshTokenEntity;
import com.gin.usermicroservice.exception.TokenRefreshException;
import com.gin.usermicroservice.repository.RefreshTokenRepository;
import com.gin.usermicroservice.repository.UserRepository;
import com.gin.usermicroservice.resource.dto.RefreshTokenRequest;
import com.gin.usermicroservice.resource.dto.RefreshTokenResponse;
import com.gin.usermicroservice.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    @Value("${gin.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtils jwtUtils;

    public Optional<RefreshTokenEntity> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public RefreshTokenEntity createRefreshToken(Long userId) {
        RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.builder()
                .userEntity(userRepository.findById(userId).get())
                .expiryDate(Instant.now().plusMillis(refreshTokenDurationMs))
                .token(UUID.randomUUID().toString())
                .build();

        refreshTokenEntity = refreshTokenRepository.save(refreshTokenEntity);

        return refreshTokenEntity;
    }

    @Override
    public RefreshTokenEntity verifyExpiration(RefreshTokenEntity refreshTokenEntity) {
        if (refreshTokenEntity.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshTokenEntity);
            throw new TokenRefreshException(refreshTokenEntity.getToken(), "Refresh token was expired, Please make a new login request");
        }
        return refreshTokenEntity;
    }

    @Override
    public RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String requestRefreshToken = refreshTokenRequest.getRefreshToken();

        return findByToken(requestRefreshToken)
                .map(this::verifyExpiration)
                .map(RefreshTokenEntity::getUserEntity)
                .map(userEntity -> {
                    String token = jwtUtils.generateTokenFromEmail(userEntity.getEmail());
                    return RefreshTokenResponse.builder()
                            .token(token)
                            .refreshToken(requestRefreshToken)
                            .build();
                }).orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    @Override
    @Transactional
    public int deleteByUserId(Long userId) {
        return refreshTokenRepository.deleteByUserEntity(userRepository.findById(userId).get());
    }
}

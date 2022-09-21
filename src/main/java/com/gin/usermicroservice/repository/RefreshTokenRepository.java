package com.gin.usermicroservice.repository;

import com.gin.usermicroservice.domain.RefreshTokenEntity;
import com.gin.usermicroservice.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

    Optional<RefreshTokenEntity> findByToken(String token);

    @Modifying
    int deleteByUserEntity(UserEntity userEntity);
}

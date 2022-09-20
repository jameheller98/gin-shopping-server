package com.gin.usermicroservice.repository;

import com.gin.usermicroservice.domain.RoleEntity;
import com.gin.usermicroservice.domain.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    Optional<RoleEntity> findByName(RoleEnum name);
}

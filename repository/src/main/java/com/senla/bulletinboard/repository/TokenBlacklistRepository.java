package com.senla.bulletinboard.repository;

import com.senla.bulletinboard.entity.TokenBlacklistEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenBlacklistRepository extends CommonRepository<TokenBlacklistEntity, Long> {

    Optional<TokenBlacklistEntity> findByToken(String token);
}

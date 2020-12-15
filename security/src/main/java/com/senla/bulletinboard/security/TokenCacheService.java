package com.senla.bulletinboard.security;

import com.senla.bulletinboard.entity.TokenBlacklistEntity;
import com.senla.bulletinboard.repository.TokenBlacklistRepository;
import lombok.Data;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class TokenCacheService {

    private final TokenBlacklistRepository repository;

    @Cacheable(value = "tokens", cacheManager = "invalidatedTokenCacheManager")
    public boolean checkToken(final String token) {
        Optional<TokenBlacklistEntity> tokenBlacklistEntity = repository
                .findByToken(token);
        return tokenBlacklistEntity.isPresent();
    }
}
package com.senla.bulletinboard.service;

import com.senla.bulletinboard.entity.TokenBlacklistEntity;
import com.senla.bulletinboard.repository.TokenBlacklistRepository;
import com.senla.bulletinboard.service.interfaces.TokenBlacklistService;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class TokenBlacklistServiceImpl implements TokenBlacklistService {

    private final TokenBlacklistRepository repository;

    @Override
    public void post(final String token) {
        TokenBlacklistEntity tokenBlacklistEntity = new TokenBlacklistEntity();
        tokenBlacklistEntity.setToken(token);
        repository.save(tokenBlacklistEntity);
    }
}

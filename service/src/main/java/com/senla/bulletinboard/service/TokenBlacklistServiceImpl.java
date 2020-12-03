package com.senla.bulletinboard.service;

import com.senla.bulletinboard.dto.TokenDto;
import com.senla.bulletinboard.entity.TokenBlacklistEntity;
import com.senla.bulletinboard.mapper.interfaces.TokenDtoEntityMapper;
import com.senla.bulletinboard.repository.TokenBlacklistRepository;
import com.senla.bulletinboard.service.interfaces.TokenBlacklistService;
import org.springframework.stereotype.Service;

@Service
public class TokenBlacklistServiceImpl extends AbstractService<TokenDto, TokenBlacklistEntity, TokenBlacklistRepository> implements TokenBlacklistService {

    public TokenBlacklistServiceImpl(TokenDtoEntityMapper dtoEntityMapper, TokenBlacklistRepository repository) {
        super(dtoEntityMapper, repository);
    }

    @Override
    public void post(final String token) {
        TokenBlacklistEntity tokenBlacklistEntity = new TokenBlacklistEntity();
        tokenBlacklistEntity.setToken(token);
        repository.save(tokenBlacklistEntity);
    }
}

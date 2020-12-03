package com.senla.bulletinboard.mapper;

import com.senla.bulletinboard.dto.TokenDto;
import com.senla.bulletinboard.entity.TokenBlacklistEntity;
import com.senla.bulletinboard.mapper.interfaces.TokenDtoEntityMapper;
import org.springframework.stereotype.Component;

@Component
public class TokenDtoEntityMapperImpl implements TokenDtoEntityMapper {

    @Override
    public TokenBlacklistEntity sourceToDestination(final TokenDto source) {
        TokenBlacklistEntity destination = new TokenBlacklistEntity();
        destination.setToken(source.getToken());
        return destination;
    }

    @Override
    public TokenDto destinationToSource(final TokenBlacklistEntity destination) {
        TokenDto source = new TokenDto();
        source.setToken(destination.getToken());
        return source;
    }
}

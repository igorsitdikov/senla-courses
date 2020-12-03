package com.senla.bulletinboard.service.interfaces;

import com.senla.bulletinboard.dto.TokenDto;
import com.senla.bulletinboard.entity.TokenBlacklistEntity;

public interface TokenBlacklistService extends CommonService<TokenDto, TokenBlacklistEntity> {

    void post(String token);
}

package com.senla.bulletinboard.repository;

import com.senla.bulletinboard.entity.TokenBlacklistEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenBlacklistRepository extends CommonRepository<TokenBlacklistEntity, Long> {

}
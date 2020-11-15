package com.senla.bulletin_board.repository;

import com.senla.bulletin_board.entity.TokenBlacklistEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenBlacklistRepository extends CommonRepository<TokenBlacklistEntity, Long> {

}

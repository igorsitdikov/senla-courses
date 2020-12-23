package com.senla.bulletinboard.repository;

import com.senla.bulletinboard.entity.TokenBlacklistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklistEntity, Long> {

    Optional<TokenBlacklistEntity> findByToken(String token);
}

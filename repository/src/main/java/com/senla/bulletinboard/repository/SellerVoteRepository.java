package com.senla.bulletinboard.repository;

import com.senla.bulletinboard.entity.SellerVoteEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerVoteRepository extends CommonRepository<SellerVoteEntity, Long> {

    boolean existsByVoterIdAndBulletinId(Long voterId, Long bulletinId);
}

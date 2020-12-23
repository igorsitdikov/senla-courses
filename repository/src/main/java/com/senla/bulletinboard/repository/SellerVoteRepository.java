package com.senla.bulletinboard.repository;

import com.senla.bulletinboard.entity.SellerVoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerVoteRepository extends JpaRepository<SellerVoteEntity, Long> {

    boolean existsByVoterIdAndBulletinId(Long voterId, Long bulletinId);
}

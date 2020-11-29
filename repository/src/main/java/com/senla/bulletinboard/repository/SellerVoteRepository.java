package com.senla.bulletinboard.repository;

import com.senla.bulletinboard.entity.SellerVoteEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerVoteRepository extends CommonRepository<SellerVoteEntity, Long> {

    @Query(value = "SELECT AVG(vote) avg_vote FROM seller_vote " +
                   "INNER JOIN bulletin b on seller_vote.bulletin_id = b.id " +
                   "WHERE b.seller_id = ?", nativeQuery = true)
    Double averageRatingByUserId(final Long id);

    boolean existsByVoterIdAndBulletinId(final Long voterId, final Long bulletinId);
}

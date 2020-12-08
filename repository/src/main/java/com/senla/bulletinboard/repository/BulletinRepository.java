package com.senla.bulletinboard.repository;

import com.senla.bulletinboard.entity.BulletinEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BulletinRepository extends CommonRepository<BulletinEntity, Long>,
                                            JpaSpecificationExecutor<BulletinEntity> {

    List<BulletinEntity> findAllBySellerId(Long id);

    @Query(value =
        "SELECT bulletin.*, AVG(sv.vote) AS avg_vote FROM bulletin " +
        "INNER JOIN user ON bulletin.seller_id = user.id " +
        "INNER JOIN seller_vote sv ON bulletin.id = sv.bulletin_id " +
        "WHERE bulletin.status = 'OPEN' " +
        "GROUP BY bulletin.id " +
        "ORDER BY user.premium, avg_vote DESC", nativeQuery = true)
    List<BulletinEntity> findAllSortedByRating();
}

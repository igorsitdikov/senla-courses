package com.senla.bulletinboard.repository;

import com.senla.bulletinboard.entity.SubscriptionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends CommonRepository<SubscriptionEntity, Long> {

    @Query(value =
        "SELECT subscription.id, subscription.user_id, subscription.tariff_id, MAX(subscription.subscribed_at) AS subscribed_at " +
        "FROM subscription " +
        "WHERE user_id IN :ids " +
        "GROUP BY user_id " +
        "ORDER BY subscribed_at DESC", nativeQuery = true)
    List<SubscriptionEntity> findLastSubscriptionsForUserIds(List<Long> ids);

    Optional<SubscriptionEntity> findTopByUserIdOrderBySubscribedAt(Long id);
}

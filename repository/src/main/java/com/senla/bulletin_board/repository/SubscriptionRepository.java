package com.senla.bulletin_board.repository;

import com.senla.bulletin_board.entity.SubscriptionEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends CommonRepository<SubscriptionEntity, Long> {

}

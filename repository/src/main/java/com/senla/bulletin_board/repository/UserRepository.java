package com.senla.bulletin_board.repository;

import com.senla.bulletin_board.entity.UserEntity;
import com.senla.bulletin_board.enumerated.AutoSubscribeStatus;
import com.senla.bulletin_board.enumerated.PremiumStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CommonRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findAllByPremium(final PremiumStatus status);

    List<UserEntity> findAllByPremiumAndAutoSubscribe(final PremiumStatus premium,
                                                      final AutoSubscribeStatus autoSubscribe);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE user SET user.premium = :#{#status.name()} WHERE user.id IN :ids", nativeQuery = true)
    void updatePremiumStatusForIds(final PremiumStatus status, final List<Long> ids);
}

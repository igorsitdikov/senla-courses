package com.senla.bulletinboard.repository;

import com.senla.bulletinboard.entity.UserEntity;
import com.senla.bulletinboard.enumerated.AutoSubscribeStatus;
import com.senla.bulletinboard.enumerated.PremiumStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findAllByPremium(PremiumStatus status);

    List<UserEntity> findAllByPremiumAndAutoSubscribe(PremiumStatus premium,
                                                      AutoSubscribeStatus autoSubscribe);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE user SET user.premium = :#{#status.name()} WHERE user.id IN :ids", nativeQuery = true)
    void updatePremiumStatusForIds(PremiumStatus status, List<Long> ids);
}

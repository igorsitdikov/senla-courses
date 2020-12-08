package com.senla.bulletinboard.repository;

import com.senla.bulletinboard.entity.DialogEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DialogRepository extends CommonRepository<DialogEntity, Long>,
                                          JpaSpecificationExecutor<DialogEntity> {

    List<DialogEntity> findAllByBulletinSellerIdOrCustomerId(Long bulletinSellerId, Long customerId);

    Optional<DialogEntity> findOne(Specification<DialogEntity> spec);

    boolean existsByBulletinIdAndCustomerId(Long bulletinId, Long customerId);
}

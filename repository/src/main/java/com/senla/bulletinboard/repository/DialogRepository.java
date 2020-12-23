package com.senla.bulletinboard.repository;

import com.senla.bulletinboard.entity.DialogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DialogRepository extends JpaRepository<DialogEntity, Long>,
                                          JpaSpecificationExecutor<DialogEntity> {

    List<DialogEntity> findAllByBulletinSellerIdOrCustomerId(Long bulletinSellerId, Long customerId);

    boolean existsByBulletinIdAndCustomerId(Long bulletinId, Long customerId);
}

package com.senla.bulletinboard.repository;

import com.senla.bulletinboard.entity.PaymentEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends CommonRepository<PaymentEntity, Long> {

    List<PaymentEntity> findAllByUserId(Long id);
}

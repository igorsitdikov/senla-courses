package com.senla.bulletin_board.repository;

import com.senla.bulletin_board.entity.PaymentEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends CommonRepository<PaymentEntity, Long> {

    List<PaymentEntity> findAllByUserId(final Long id);

}

package com.senla.bulletin_board.repository;

import com.senla.bulletin_board.entity.DialogEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DialogRepository extends CommonRepository<DialogEntity, Long> {

    List<DialogEntity> findAllByBulletin_SellerIdOrCustomerId(final Long bulletin_seller_id,
                                                              final Long customerId);

    boolean existsByBulletin_TitleAndCustomerId(final String title, final Long customerId);
}

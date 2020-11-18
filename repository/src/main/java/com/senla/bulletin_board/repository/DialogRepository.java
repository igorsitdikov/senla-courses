package com.senla.bulletin_board.repository;

import com.senla.bulletin_board.entity.DialogEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DialogRepository extends CommonRepository<DialogEntity, Long> {

    List<DialogEntity> findAllByBulletin_SellerId(final Long id);

    boolean existsByBulletin_TitleAndCustomerId(final String title, final Long customerId);
}

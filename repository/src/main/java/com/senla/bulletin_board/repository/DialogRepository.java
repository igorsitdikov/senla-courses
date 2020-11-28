package com.senla.bulletin_board.repository;

import com.senla.bulletin_board.entity.DialogEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface DialogRepository extends CommonRepository<DialogEntity, Long> {

    List<DialogEntity> findAllByBulletin_SellerIdOrCustomerId(final Long bulletin_seller_id,
                                                              final Long customerId);

    @Query(value = "SELECT CASE WHEN COUNT(*) = 1 THEN true ELSE false END FROM dialog d " +
                   "INNER JOIN bulletin b on d.bulletin_id = b.id " +
                   "WHERE d.id = :id AND (b.seller_id = :ownerId OR d.customer_id = :ownerId)",
        nativeQuery = true)
    BigInteger findByIdAndOwnerId(final Long id, final Long ownerId);

    boolean existsByBulletin_TitleAndCustomerId(final String title, final Long customerId);
}

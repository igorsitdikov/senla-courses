package com.senla.bulletin_board.repository;

import com.senla.bulletin_board.entity.BulletinEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BulletinRepository extends CommonRepository<BulletinEntity, Long>,
                                            JpaSpecificationExecutor<BulletinEntity> {

    List<BulletinEntity> findAllBySellerId(final Long id);

}

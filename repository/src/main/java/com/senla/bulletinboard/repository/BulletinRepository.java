package com.senla.bulletinboard.repository;

import com.senla.bulletinboard.entity.BulletinEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BulletinRepository extends CommonRepository<BulletinEntity, Long>,
                                            JpaSpecificationExecutor<BulletinEntity>,
                                            PagingAndSortingRepository<BulletinEntity, Long> {

    List<BulletinEntity> findAllBySellerId(Long id, Pageable pageable);
}

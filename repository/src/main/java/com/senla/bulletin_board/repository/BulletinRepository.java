package com.senla.bulletin_board.repository;

import com.senla.bulletin_board.entity.BulletinEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BulletinRepository extends JpaRepository<BulletinEntity, Long> {

}

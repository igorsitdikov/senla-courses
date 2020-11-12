package com.senla.bulletin_board.repository;

import com.senla.bulletin_board.entity.TariffEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TariffRepository extends JpaRepository<TariffEntity, Long> {

}

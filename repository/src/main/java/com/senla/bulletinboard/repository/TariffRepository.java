package com.senla.bulletinboard.repository;

import com.senla.bulletinboard.entity.TariffEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TariffRepository extends JpaRepository<TariffEntity, Long> {

}

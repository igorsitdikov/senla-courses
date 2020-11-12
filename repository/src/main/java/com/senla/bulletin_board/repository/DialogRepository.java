package com.senla.bulletin_board.repository;

import com.senla.bulletin_board.entity.DialogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DialogRepository extends JpaRepository<DialogEntity, Long> {

}

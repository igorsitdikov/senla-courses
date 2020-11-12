package com.senla.bulletin_board.repository;

import com.senla.bulletin_board.entity.SellerVoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerVoteRepository extends JpaRepository<SellerVoteEntity, Long> {

}

package com.senla.bulletin_board.repository;

import com.senla.bulletin_board.entity.CommentEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CommonRepository<CommentEntity, Long> {

}

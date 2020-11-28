package com.senla.bulletinboard.repository;

import com.senla.bulletinboard.entity.CommentEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CommonRepository<CommentEntity, Long> {

}

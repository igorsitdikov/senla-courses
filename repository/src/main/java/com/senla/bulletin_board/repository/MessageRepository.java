package com.senla.bulletin_board.repository;

import com.senla.bulletin_board.entity.MessageEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends CommonRepository<MessageEntity, Long> {

    List<MessageEntity> findAllByDialogId(final Long id);
}

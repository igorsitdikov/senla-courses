package com.senla.bulletinboard.repository;

import com.senla.bulletinboard.entity.MessageEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends CommonRepository<MessageEntity, Long> {

    List<MessageEntity> findAllByDialogId(final Long id);
}

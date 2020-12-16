package com.senla.bulletinboard.repository;

import com.senla.bulletinboard.entity.MessageEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface MessageRepository extends CommonRepository<MessageEntity, Long>, PagingAndSortingRepository<MessageEntity, Long> {

    List<MessageEntity> findAllByDialogId(Long id, Pageable pageable);
}

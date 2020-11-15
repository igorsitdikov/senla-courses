package com.senla.bulletin_board.service;

import com.senla.bulletin_board.dto.MessageDto;
import com.senla.bulletin_board.entity.MessageEntity;
import com.senla.bulletin_board.mapper.interfaces.MessageDtoEntityMapper;
import com.senla.bulletin_board.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService extends AbstractService<MessageDto, MessageEntity, MessageRepository> {

    private final MessageDtoEntityMapper dtoEntityMapper;

    private MessageService(final MessageDtoEntityMapper dtoEntityMapper,
                           final MessageRepository repository) {
        super(dtoEntityMapper, repository);
        this.dtoEntityMapper = dtoEntityMapper;
    }

    public List<MessageDto> findAllMessagesByDialogId(final Long id) {
        return repository.findAllByDialogId(id)
            .stream()
            .map(dtoEntityMapper::destinationToSource)
            .collect(Collectors.toList());
    }
}

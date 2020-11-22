package com.senla.bulletin_board.service;

import com.senla.bulletin_board.dto.IdDto;
import com.senla.bulletin_board.dto.MessageDto;
import com.senla.bulletin_board.entity.BulletinEntity;
import com.senla.bulletin_board.entity.DialogEntity;
import com.senla.bulletin_board.entity.MessageEntity;
import com.senla.bulletin_board.exception.WrongMessageRecipientException;
import com.senla.bulletin_board.exception.WrongRecipientException;
import com.senla.bulletin_board.exception.WrongSenderException;
import com.senla.bulletin_board.mapper.interfaces.MessageDtoEntityMapper;
import com.senla.bulletin_board.repository.BulletinRepository;
import com.senla.bulletin_board.repository.DialogRepository;
import com.senla.bulletin_board.repository.MessageRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class MessageService extends AbstractService<MessageDto, MessageEntity, MessageRepository> {

    private final MessageDtoEntityMapper dtoEntityMapper;
    private final BulletinRepository bulletinRepository;
    private final DialogRepository dialogRepository;

    private MessageService(final MessageDtoEntityMapper dtoEntityMapper,
                           final MessageRepository repository,
                           final BulletinRepository bulletinRepository,
                           final DialogRepository dialogRepository) {
        super(dtoEntityMapper, repository);
        this.dtoEntityMapper = dtoEntityMapper;
        this.bulletinRepository = bulletinRepository;
        this.dialogRepository = dialogRepository;
    }

    public List<MessageDto> findAllMessagesByDialogId(final Long id) {
        return repository.findAllByDialogId(id)
            .stream()
            .map(dtoEntityMapper::destinationToSource)
            .collect(Collectors.toList());
    }

    public IdDto createMessage(final MessageDto messageDto)
        throws WrongRecipientException, WrongSenderException, WrongMessageRecipientException {
        if (messageDto.getRecipientId().equals(messageDto.getSenderId())) {
            final String message = "Can not send message to yourself";
            log.error(message);
            throw new WrongMessageRecipientException(message);
        }
        final DialogEntity dialogEntity = dialogRepository.getOne(messageDto.getDialogId());
        final BulletinEntity bulletinEntity = bulletinRepository.getOne(dialogEntity.getBulletinId());
        if (!messageDto.getRecipientId().equals(dialogEntity.getCustomerId()) &&
            !messageDto.getRecipientId().equals(bulletinEntity.getSeller().getId())) {
            final String message = String.format("Wrong recipent id %d", messageDto.getRecipientId());
            log.error(message);
            throw new WrongRecipientException(message);
        }
        if (!messageDto.getSenderId().equals(dialogEntity.getCustomerId()) &&
            !messageDto.getSenderId().equals(bulletinEntity.getSeller().getId())) {
            final String message = String.format("Wrong sender id %d", messageDto.getSenderId());
            log.error(message);
            throw new WrongSenderException(message);
        }
        return super.post(messageDto);
    }
}

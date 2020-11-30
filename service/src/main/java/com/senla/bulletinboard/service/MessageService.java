package com.senla.bulletinboard.service;

import com.senla.bulletinboard.dto.IdDto;
import com.senla.bulletinboard.dto.MessageDto;
import com.senla.bulletinboard.entity.BulletinEntity;
import com.senla.bulletinboard.entity.DialogEntity;
import com.senla.bulletinboard.entity.MessageEntity;
import com.senla.bulletinboard.exception.EntityNotFoundException;
import com.senla.bulletinboard.exception.WrongMessageRecipientException;
import com.senla.bulletinboard.exception.WrongRecipientException;
import com.senla.bulletinboard.exception.WrongSenderException;
import com.senla.bulletinboard.mapper.interfaces.MessageDtoEntityMapper;
import com.senla.bulletinboard.repository.BulletinRepository;
import com.senla.bulletinboard.repository.DialogRepository;
import com.senla.bulletinboard.repository.MessageRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class MessageService extends AbstractService<MessageDto, MessageEntity, MessageRepository> {

    private final MessageDtoEntityMapper dtoEntityMapper;
    private final BulletinRepository bulletinRepository;
    private final DialogRepository dialogRepository;

    public MessageService(final MessageDtoEntityMapper dtoEntityMapper,
                          final MessageRepository repository,
                          final BulletinRepository bulletinRepository,
                          final DialogRepository dialogRepository) {
        super(dtoEntityMapper, repository);
        this.dtoEntityMapper = dtoEntityMapper;
        this.bulletinRepository = bulletinRepository;
        this.dialogRepository = dialogRepository;
    }

    @PreAuthorize("@messageService.checkOwner(authentication.principal.id, #id)")
    public List<MessageDto> findAllMessagesByDialogId(final Long id) {
        return repository.findAllByDialogId(id)
            .stream()
            .map(dtoEntityMapper::destinationToSource)
            .collect(Collectors.toList());
    }

    public boolean checkOwner(final Long userId, final Long dialogId) {
        return dialogRepository.findByIdAndOwnerId(dialogId, userId).compareTo(BigInteger.ONE) == 0;
    }

    public IdDto createMessage(final MessageDto messageDto)
        throws WrongRecipientException, WrongSenderException, WrongMessageRecipientException, EntityNotFoundException {
        if (messageDto.getRecipientId().equals(messageDto.getSenderId())) {
            final String message = "Forbidden send message to yourself";
            log.error(message);
            throw new WrongMessageRecipientException(message);
        }
        final DialogEntity dialogEntity = dialogRepository.findById(messageDto.getDialogId())
            .orElseThrow(
                () -> new EntityNotFoundException("Dialog with id " + messageDto.getDialogId() + " not exists"));
        final BulletinEntity bulletinEntity = bulletinRepository.findById(dialogEntity.getBulletinId())
            .orElseThrow(
                () -> new EntityNotFoundException("Bulletin with id " + dialogEntity.getBulletinId() + " not exists"));
        if (!messageDto.getRecipientId().equals(dialogEntity.getCustomerId()) &&
            !messageDto.getRecipientId().equals(bulletinEntity.getSeller().getId())) {
            final String message = String.format("Wrong recipient id %d", messageDto.getRecipientId());
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

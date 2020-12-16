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
import com.senla.bulletinboard.repository.specification.DialogExistsSpecification;
import com.senla.bulletinboard.service.interfaces.MessageService;
import com.senla.bulletinboard.utils.Translator;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl extends AbstractService<MessageDto, MessageEntity, MessageRepository> implements
                                                                                                      MessageService {

    private final MessageDtoEntityMapper dtoEntityMapper;
    private final BulletinRepository bulletinRepository;
    private final DialogRepository dialogRepository;

    public MessageServiceImpl(final MessageDtoEntityMapper dtoEntityMapper,
                              final MessageRepository repository,
                              final BulletinRepository bulletinRepository,
                              final DialogRepository dialogRepository,
                              final Translator translator) {
        super(dtoEntityMapper, repository, translator);
        this.dtoEntityMapper = dtoEntityMapper;
        this.bulletinRepository = bulletinRepository;
        this.dialogRepository = dialogRepository;
    }

    @Override
    @PreAuthorize("@messageServiceImpl.checkDialogOwner(authentication.principal.id, #id)")
    public List<MessageDto> findAllMessagesByDialogId(final Long id, final Integer page, final Integer size) {
        Pageable pageWithSize = PageRequest.of(page, size);
        return repository.findAllByDialogId(id, pageWithSize)
            .stream()
            .map(dtoEntityMapper::destinationToSource)
            .collect(Collectors.toList());
    }

    @Cacheable(value = "dialogOwner", key = "{#userId, #dialogId}")
    public boolean checkDialogOwner(final Long userId, final Long dialogId) {
        DialogExistsSpecification specification = new DialogExistsSpecification(dialogId, userId);
        return dialogRepository.findOne(specification).isPresent();
    }

    @Override
    @PreAuthorize("authentication.principal.id == #messageDto.getSenderId()")
    public IdDto createMessage(final MessageDto messageDto)
        throws WrongRecipientException, WrongSenderException, WrongMessageRecipientException, EntityNotFoundException {
        if (messageDto.getRecipientId().equals(messageDto.getSenderId())) {
            final String message = translator.toLocale("send-message-forbidden");
            throw new WrongMessageRecipientException(message);
        }
        final DialogEntity dialogEntity = dialogRepository.findById(messageDto.getDialogId())
            .orElseThrow(
                () -> new EntityNotFoundException(translator.toLocale("dialog-not-exists", messageDto.getDialogId())));
        final BulletinEntity bulletinEntity = bulletinRepository.findById(dialogEntity.getBulletinId())
            .orElseThrow(
                () -> new EntityNotFoundException(
                    translator.toLocale("bulletin-not-exists", dialogEntity.getBulletinId())));
        boolean checkRecipient = checkRecipient(messageDto, dialogEntity, bulletinEntity);
        if (checkRecipient) {
            final String message = translator.toLocale("wrong-recipient", messageDto.getRecipientId());
            throw new WrongRecipientException(message);
        }
        boolean checkSender = checkSender(messageDto, dialogEntity, bulletinEntity);
        if (checkSender) {
            final String message = translator.toLocale("wrong-sender", messageDto.getSenderId());
            throw new WrongSenderException(message);
        }
        return super.post(messageDto);
    }

    private boolean checkRecipient(final MessageDto messageDto,
                                   final DialogEntity dialogEntity,
                                   final BulletinEntity bulletinEntity) {
        return !messageDto.getRecipientId().equals(dialogEntity.getCustomerId()) &&
               !messageDto.getRecipientId().equals(bulletinEntity.getSeller().getId());
    }

    private boolean checkSender(final MessageDto messageDto,
                                final DialogEntity dialogEntity,
                                final BulletinEntity bulletinEntity) {
        return !messageDto.getSenderId().equals(dialogEntity.getCustomerId()) &&
               !messageDto.getSenderId().equals(bulletinEntity.getSeller().getId());
    }
}

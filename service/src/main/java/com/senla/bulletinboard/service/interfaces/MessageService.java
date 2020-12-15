package com.senla.bulletinboard.service.interfaces;

import com.senla.bulletinboard.dto.IdDto;
import com.senla.bulletinboard.dto.MessageDto;
import com.senla.bulletinboard.entity.MessageEntity;
import com.senla.bulletinboard.exception.EntityNotFoundException;
import com.senla.bulletinboard.exception.WrongMessageRecipientException;
import com.senla.bulletinboard.exception.WrongRecipientException;
import com.senla.bulletinboard.exception.WrongSenderException;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface MessageService extends CommonService<MessageDto, MessageEntity> {

    @PreAuthorize("@messageServiceImpl.checkDialogOwner(authentication.principal.id, #id)")
    List<MessageDto> findAllMessagesByDialogId(Long id);

    IdDto createMessage(MessageDto messageDto)
        throws WrongRecipientException, WrongSenderException, WrongMessageRecipientException, EntityNotFoundException;
}

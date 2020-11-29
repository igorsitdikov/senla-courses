package com.senla.bulletinboard.controller;

import com.senla.bulletinboard.dto.IdDto;
import com.senla.bulletinboard.dto.MessageDto;
import com.senla.bulletinboard.exception.EntityNotFoundException;
import com.senla.bulletinboard.exception.WrongMessageRecipientException;
import com.senla.bulletinboard.exception.WrongRecipientException;
import com.senla.bulletinboard.exception.WrongSenderException;
import com.senla.bulletinboard.service.MessageService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Data
@RestController
@RequestMapping(value = "/messages")
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IdDto createMessage(@RequestBody final MessageDto messageDto)
        throws WrongMessageRecipientException, WrongRecipientException, WrongSenderException, EntityNotFoundException {
        return messageService.createMessage(messageDto);
    }

}

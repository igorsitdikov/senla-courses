package com.senla.bulletinboard.controller;

import com.senla.bulletinboard.dto.IdDto;
import com.senla.bulletinboard.dto.MessageDto;
import com.senla.bulletinboard.exception.EntityNotFoundException;
import com.senla.bulletinboard.exception.WrongMessageRecipientException;
import com.senla.bulletinboard.exception.WrongRecipientException;
import com.senla.bulletinboard.exception.WrongSenderException;
import com.senla.bulletinboard.service.interfaces.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Message Management Controller")
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create message", description = "Create new message")
    public IdDto createMessage(@RequestBody final MessageDto messageDto)
        throws WrongMessageRecipientException, WrongRecipientException, WrongSenderException, EntityNotFoundException {
        return messageService.createMessage(messageDto);
    }

}

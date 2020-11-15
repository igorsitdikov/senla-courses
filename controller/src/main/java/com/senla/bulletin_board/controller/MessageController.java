package com.senla.bulletin_board.controller;

import com.senla.bulletin_board.dto.IdDto;
import com.senla.bulletin_board.dto.MessageDto;
import com.senla.bulletin_board.service.MessageService;
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
    public IdDto createMessage(@RequestBody final MessageDto messageDto) {
        return messageService.post(messageDto);
    }

}

package com.senla.bulletin_board.controller;

import com.senla.bulletin_board.dto.DialogRequestDto;
import com.senla.bulletin_board.dto.IdDto;
import com.senla.bulletin_board.dto.MessageDto;
import com.senla.bulletin_board.mock.MessageMock;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/dialogs")
public class DialogController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IdDto createDialog(@RequestBody final DialogRequestDto dialogRequestDto) {
        return new IdDto(1L);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteDialog(@PathVariable final Long id) {
    }

    @GetMapping(value = "/{id}/messages")
    @ResponseStatus(HttpStatus.OK)
    public List<MessageDto> showMessages(@PathVariable final Long id) {
        return MessageMock.getAll();
    }
}

package com.senla.bulletinboard.controller;

import com.senla.bulletinboard.dto.DialogDto;
import com.senla.bulletinboard.dto.IdDto;
import com.senla.bulletinboard.dto.MessageDto;
import com.senla.bulletinboard.exception.EntityAlreadyExistsException;
import com.senla.bulletinboard.service.interfaces.DialogService;
import com.senla.bulletinboard.service.interfaces.MessageService;
import lombok.Data;
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

@Data
@RestController
@RequestMapping(value = "/dialogs")
public class DialogController {

    private final DialogService dialogService;
    private final MessageService messageService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IdDto createDialog(@RequestBody final DialogDto dialogDto) throws EntityAlreadyExistsException {
        return dialogService.createDialog(dialogDto);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteDialog(@PathVariable final Long id) {
        dialogService.delete(id);
    }

    @GetMapping(value = "/{id}/messages")
    @ResponseStatus(HttpStatus.OK)
    public List<MessageDto> showMessages(@PathVariable final Long id) {
        return messageService.findAllMessagesByDialogId(id);
    }
}

package com.senla.bulletin_board.controller;

import com.senla.bulletin_board.dto.CommentDto;
import com.senla.bulletin_board.dto.IdDto;
import com.senla.bulletin_board.service.CommentService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Data
@RestController
@RequestMapping(value = "/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IdDto createComment(@RequestBody final CommentDto commentDto) {
        return commentService.post(commentDto);
    }
}

package com.senla.bulletin_board.controller;

import com.senla.bulletin_board.dto.IdDto;
import com.senla.bulletin_board.dto.StarRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/stars")
public class StarController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IdDto addStarToSeller(@RequestBody final StarRequestDto starRequestDto) {
        return new IdDto(1L);
    }
}

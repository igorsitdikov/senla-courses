package com.senla.bulletin_board.controller;

import com.senla.bulletin_board.dto.BulletinDto;
import com.senla.bulletin_board.dto.IdDto;
import com.senla.bulletin_board.mock.BulletinMock;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/bulletins")
public class BulletinController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BulletinDto> showBulletins() {
        return BulletinMock.getAll();
    }

    // TODO: add BB-7

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IdDto createBulletin(@RequestBody final BulletinDto bulletinDto) {
        return new IdDto(4L);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateBulletin(@PathVariable final Long id, @RequestBody final BulletinDto bulletinDto) {
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBulletin(@PathVariable final Long id) {

    }
}

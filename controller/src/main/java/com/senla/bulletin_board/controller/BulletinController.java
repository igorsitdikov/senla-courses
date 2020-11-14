package com.senla.bulletin_board.controller;

import com.senla.bulletin_board.dto.BulletinDto;
import com.senla.bulletin_board.dto.BulletinBaseDto;
import com.senla.bulletin_board.dto.IdDto;
import com.senla.bulletin_board.mock.BulletinDetailsMock;
import com.senla.bulletin_board.service.BulletinService;
import lombok.Data;
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

@Data
@RestController
@RequestMapping(value = "/bulletins")
public class BulletinController {

    private final BulletinService bulletinService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BulletinBaseDto> showBulletins() {
        return bulletinService.showAll();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BulletinDto showBulletinDetails(@PathVariable final Long id) throws Exception {
        return bulletinService.findDtoById(id);
    }

    // TODO: add BB-7

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IdDto createBulletin(@RequestBody final BulletinDto bulletinDto) {
        return bulletinService.post(bulletinDto);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BulletinDto updateBulletin(@PathVariable final Long id, @RequestBody final BulletinDto bulletinDto)
        throws Exception {
        return bulletinService.update(id, bulletinDto);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBulletin(@PathVariable final Long id) {
        bulletinService.delete(id);
    }
}

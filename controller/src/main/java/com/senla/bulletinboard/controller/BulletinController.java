package com.senla.bulletinboard.controller;

import com.senla.bulletinboard.dto.BulletinBaseDto;
import com.senla.bulletinboard.dto.BulletinDto;
import com.senla.bulletinboard.dto.IdDto;
import com.senla.bulletinboard.enumerated.SortBulletin;
import com.senla.bulletinboard.exception.EntityNotFoundException;
import com.senla.bulletinboard.service.interfaces.BulletinService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@Data
@RestController
@RequestMapping(value = "/bulletins")
public class BulletinController {

    private final BulletinService bulletinService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BulletinBaseDto> showBulletins(
        @RequestParam(value = "filter", required = false) final String[] filters,
        @RequestParam(value = "sort", required = false) final SortBulletin sort) {
        return bulletinService.findAllBulletins(filters, sort);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BulletinDto showBulletinDetails(@PathVariable final Long id) throws EntityNotFoundException {
        return bulletinService.findBulletinById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IdDto createBulletin(@RequestBody final BulletinDto bulletinDto) {
        return bulletinService.post(bulletinDto);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BulletinDto updateBulletin(@PathVariable final Long id, @RequestBody final BulletinDto bulletinDto)
        throws EntityNotFoundException {
        return bulletinService.updateBulletin(id, bulletinDto);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBulletin(@PathVariable final Long id, Principal principal) throws EntityNotFoundException {
        bulletinService.deleteBulletin(id);
    }
}

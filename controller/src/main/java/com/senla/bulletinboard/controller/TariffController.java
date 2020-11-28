package com.senla.bulletinboard.controller;

import com.senla.bulletinboard.dto.IdDto;
import com.senla.bulletinboard.dto.TariffDto;
import com.senla.bulletinboard.exception.EntityNotFoundException;
import com.senla.bulletinboard.service.TariffService;
import lombok.Data;
import org.springframework.http.HttpStatus;
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
@RequestMapping(value = "/tariffs")
public class TariffController {

    private final TariffService tariffService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TariffDto> showTariffs() {
        return tariffService.findAllDto();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IdDto addTariff(@RequestBody final TariffDto tariffDto) {
        return tariffService.post(tariffDto);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TariffDto updateTariff(@PathVariable final Long id, @RequestBody final TariffDto tariffDto)
        throws EntityNotFoundException {
        return tariffService.updateTariff(id, tariffDto);
    }
}

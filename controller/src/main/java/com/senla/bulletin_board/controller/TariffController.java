package com.senla.bulletin_board.controller;

import com.senla.bulletin_board.dto.IdDto;
import com.senla.bulletin_board.dto.TariffDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/tariffs")
public class TariffController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TariffDto> showTariffs() {
        List<TariffDto> tariffs = new ArrayList<>();
        tariffs.add(new TariffDto(1L, BigDecimal.valueOf(5), 1, "5$ за 1 день"));
        tariffs.add(new TariffDto(2L, BigDecimal.valueOf(12), 3, "12$ за 3 дня"));
        tariffs.add(new TariffDto(3L, BigDecimal.valueOf(19.5), 7, "19.5$ за 7 дней"));
        return tariffs;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IdDto addTariff(@RequestBody final TariffDto tariffDto) {
        return new IdDto(3L);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void subscribe(@PathVariable final Long id, @RequestParam(name = "user_id") final Long userId) {

    }
}

package com.senla.bulletin_board.controller;

import com.senla.bulletin_board.dto.IdDto;
import com.senla.bulletin_board.dto.TariffDto;
import com.senla.bulletin_board.service.PaymentService;
import com.senla.bulletin_board.service.TariffService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Data
@RestController
@RequestMapping(value = "/tariffs")
public class TariffController {

    private final TariffService tariffService;
    private final PaymentService paymentService;

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
        throws Exception {
        return tariffService.update(id, tariffDto);
    }


    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void subscribe(@PathVariable final Long id, @RequestParam(name = "user_id") final Long userId) {
        paymentService.addPremium(userId, id);
    }
}

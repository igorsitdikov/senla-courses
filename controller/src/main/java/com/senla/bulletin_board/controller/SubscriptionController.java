package com.senla.bulletin_board.controller;

import com.senla.bulletin_board.dto.IdDto;
import com.senla.bulletin_board.dto.SubscriptionDto;
import com.senla.bulletin_board.dto.TariffDto;
import com.senla.bulletin_board.exception.EntityNotFoundException;
import com.senla.bulletin_board.exception.InsufficientFundsException;
import com.senla.bulletin_board.service.PaymentService;
import com.senla.bulletin_board.service.SubscriptionService;
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
@RequestMapping(value = "/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void subscribe(@RequestBody final SubscriptionDto subscriptionDto) throws InsufficientFundsException {
        subscriptionService.addPremium(subscriptionDto);
    }
}

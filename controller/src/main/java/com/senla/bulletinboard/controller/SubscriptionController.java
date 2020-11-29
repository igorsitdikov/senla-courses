package com.senla.bulletinboard.controller;

import com.senla.bulletinboard.dto.SubscriptionDto;
import com.senla.bulletinboard.exception.EntityNotFoundException;
import com.senla.bulletinboard.exception.InsufficientFundsException;
import com.senla.bulletinboard.repository.SubscriptionRepository;
import com.senla.bulletinboard.repository.UserRepository;
import com.senla.bulletinboard.service.SubscriptionService;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@Data
@RestController
@RequestMapping(value = "/subscriptions")
public class SubscriptionController {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;

    private final SubscriptionService subscriptionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void subscribe(@RequestBody final SubscriptionDto subscriptionDto)
        throws InsufficientFundsException, EntityNotFoundException {
        subscriptionService.addPremium(subscriptionDto);
    }
}
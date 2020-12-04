package com.senla.bulletinboard.controller;

import com.senla.bulletinboard.dto.SubscriptionDto;
import com.senla.bulletinboard.exception.EntityNotFoundException;
import com.senla.bulletinboard.exception.InsufficientFundsException;
import com.senla.bulletinboard.exception.NoSuchUserException;
import com.senla.bulletinboard.service.interfaces.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Subscription Management Controller")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Subscribe for premium account", description = "Subscribe for premium account")
    public void subscribe(@RequestBody final SubscriptionDto subscriptionDto)
        throws InsufficientFundsException, EntityNotFoundException, NoSuchUserException {
        subscriptionService.addPremium(subscriptionDto);
    }
}
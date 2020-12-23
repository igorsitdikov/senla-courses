package com.senla.bulletinboard.controller;

import com.senla.bulletinboard.dto.ApiErrorDto;
import com.senla.bulletinboard.dto.IdDto;
import com.senla.bulletinboard.dto.SubscriptionDto;
import com.senla.bulletinboard.exception.EntityNotFoundException;
import com.senla.bulletinboard.exception.InsufficientFundsException;
import com.senla.bulletinboard.exception.NoSuchUserException;
import com.senla.bulletinboard.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successful operation", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = IdDto.class))
        }),
        @ApiResponse(responseCode = "400", description = "Bad request", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDto.class))
        }),
        @ApiResponse(responseCode = "402", description = "Insufficient funds", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDto.class))
        }),
        @ApiResponse(responseCode = "403", description = "Forbidden", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDto.class))
        }),
        @ApiResponse(responseCode = "404", description = "User not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDto.class))
        })
    })
    public void subscribe(@RequestBody final SubscriptionDto subscriptionDto)
        throws InsufficientFundsException, EntityNotFoundException, NoSuchUserException {
        subscriptionService.addPremium(subscriptionDto);
    }
}
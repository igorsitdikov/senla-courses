package com.senla.bulletinboard.controller;

import com.senla.bulletinboard.dto.ApiErrorDto;
import com.senla.bulletinboard.dto.IdDto;
import com.senla.bulletinboard.dto.SellerVoteDto;
import com.senla.bulletinboard.dto.UserDto;
import com.senla.bulletinboard.exception.BulletinIsClosedException;
import com.senla.bulletinboard.exception.EntityNotFoundException;
import com.senla.bulletinboard.exception.VoteAlreadyExistsException;
import com.senla.bulletinboard.exception.WrongVoterException;
import com.senla.bulletinboard.service.SellerVoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Data
@RestController
@RequestMapping(value = "/votes")
@Tag(name = "Vote Management Controller")
public class VoteController {

    private final SellerVoteService sellerVoteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create vote", description = "Add vote to bulletin if bulletin's status is OPEN")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
        }),
        @ApiResponse(responseCode = "400", description = "Bad request", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDto.class))
        }),
        @ApiResponse(responseCode = "404", description = "User not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDto.class))
        })
    })
    public IdDto addVoteToBulletin(@RequestBody final SellerVoteDto sellerVoteDto)
        throws WrongVoterException, EntityNotFoundException, BulletinIsClosedException, VoteAlreadyExistsException {
        return sellerVoteService.addVoteToBulletin(sellerVoteDto);
    }
}

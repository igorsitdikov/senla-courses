package com.senla.bulletinboard.controller;

import com.senla.bulletinboard.dto.IdDto;
import com.senla.bulletinboard.dto.SellerVoteDto;
import com.senla.bulletinboard.exception.BulletinIsClosedException;
import com.senla.bulletinboard.exception.EntityNotFoundException;
import com.senla.bulletinboard.exception.VoteAlreadyExistsException;
import com.senla.bulletinboard.exception.WrongVoterException;
import com.senla.bulletinboard.service.interfaces.SellerVoteService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping(value = "/stars")
@Tag(name = "Vote Management Controller")
public class VoteController {

    private final SellerVoteService sellerVoteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create vote", description = "Add vote to bulletin if bulletin's status is OPEN")
    public IdDto addVoteToBulletin(@RequestBody final SellerVoteDto sellerVoteDto)
        throws WrongVoterException, EntityNotFoundException, BulletinIsClosedException, VoteAlreadyExistsException {
        return sellerVoteService.addVoteToBulletin(sellerVoteDto);
    }
}

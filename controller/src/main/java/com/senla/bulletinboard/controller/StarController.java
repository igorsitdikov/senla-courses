package com.senla.bulletinboard.controller;

import com.senla.bulletinboard.dto.IdDto;
import com.senla.bulletinboard.dto.SellerVoteDto;
import com.senla.bulletinboard.exception.BulletinIsClosedException;
import com.senla.bulletinboard.exception.EntityNotFoundException;
import com.senla.bulletinboard.exception.VoteAlreadyExistsException;
import com.senla.bulletinboard.exception.WrongVoterException;
import com.senla.bulletinboard.service.interfaces.SellerVoteService;
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
public class StarController {

    private final SellerVoteService sellerVoteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IdDto addStarToSeller(@RequestBody final SellerVoteDto sellerVoteDto)
        throws WrongVoterException, EntityNotFoundException, BulletinIsClosedException, VoteAlreadyExistsException {
        return sellerVoteService.addVoteToBulletin(sellerVoteDto);
    }
}

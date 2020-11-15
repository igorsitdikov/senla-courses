package com.senla.bulletin_board.controller;

import com.senla.bulletin_board.dto.IdDto;
import com.senla.bulletin_board.dto.SellerVoteDto;
import com.senla.bulletin_board.service.SellerVoteService;
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
    public IdDto addStarToSeller(@RequestBody final SellerVoteDto sellerVoteDto) {
        return sellerVoteService.post(sellerVoteDto);
    }
}

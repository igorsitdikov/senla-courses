package com.senla.bulletinboard.service.interfaces;

import com.senla.bulletinboard.dto.IdDto;
import com.senla.bulletinboard.dto.SellerVoteDto;
import com.senla.bulletinboard.entity.SellerVoteEntity;
import com.senla.bulletinboard.exception.BulletinIsClosedException;
import com.senla.bulletinboard.exception.EntityNotFoundException;
import com.senla.bulletinboard.exception.VoteAlreadyExistsException;
import com.senla.bulletinboard.exception.WrongVoterException;
import org.springframework.security.access.prepost.PreAuthorize;

public interface SellerVoteService extends CommonService<SellerVoteDto, SellerVoteEntity> {

    @PreAuthorize("authentication.principal.id == #sellerVoteDto.getVoterId()")
    IdDto addVoteToBulletin(SellerVoteDto sellerVoteDto)
        throws WrongVoterException, EntityNotFoundException, BulletinIsClosedException, VoteAlreadyExistsException;
}

package com.senla.bulletinboard.service;

import com.senla.bulletinboard.dto.IdDto;
import com.senla.bulletinboard.dto.SellerVoteDto;
import com.senla.bulletinboard.entity.BulletinEntity;
import com.senla.bulletinboard.entity.SellerVoteEntity;
import com.senla.bulletinboard.enumerated.BulletinStatus;
import com.senla.bulletinboard.exception.BulletinIsClosedException;
import com.senla.bulletinboard.exception.EntityNotFoundException;
import com.senla.bulletinboard.exception.VoteAlreadyExistsException;
import com.senla.bulletinboard.exception.WrongVoterException;
import com.senla.bulletinboard.mapper.interfaces.DtoEntityMapper;
import com.senla.bulletinboard.repository.BulletinRepository;
import com.senla.bulletinboard.repository.SellerVoteRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class SellerVoteService extends AbstractService<SellerVoteDto, SellerVoteEntity, SellerVoteRepository> {

    private BulletinRepository bulletinRepository;

    public SellerVoteService(final DtoEntityMapper<SellerVoteDto, SellerVoteEntity> dtoEntityMapper,
                             final SellerVoteRepository repository,
                             final BulletinRepository bulletinRepository) {
        super(dtoEntityMapper, repository);
        this.bulletinRepository = bulletinRepository;
    }

    @PreAuthorize("authentication.principal.id == #sellerVoteDto.getVoterId()")
    public IdDto addVoteToBulletin(final SellerVoteDto sellerVoteDto)
        throws WrongVoterException, EntityNotFoundException, BulletinIsClosedException, VoteAlreadyExistsException {
        final Optional<BulletinEntity> bulletinEntity = bulletinRepository.findById(sellerVoteDto.getBulletinId());
        if (bulletinEntity.isPresent() && bulletinEntity.get().getStatus() == BulletinStatus.CLOSE) {
            final String message = String.format("Bulletin with id %d is closed",
                                                 sellerVoteDto.getBulletinId());
            log.error(message);
            throw new BulletinIsClosedException(message);
        } else if (!bulletinEntity.isPresent()) {
            final String message = String.format("Bulletin with id %d does not exist",
                                                 sellerVoteDto.getBulletinId());
            log.error(message);
            throw new EntityNotFoundException(message);
        }
        if (bulletinEntity.get().getSeller().getId().equals(sellerVoteDto.getVoterId())) {
            final String message = "Forbidden to vote for yourself";
            throw new WrongVoterException(message);
        }
        try {
            return super.post(sellerVoteDto);
        } catch (DataIntegrityViolationException e) {
            final String message = String
                .format("User with id %d already voted for bulletin with id %d",
                        sellerVoteDto.getVoterId(),
                        sellerVoteDto.getBulletinId());
            log.warn(message);
            throw new VoteAlreadyExistsException(message);
        }
    }

    public Double averageSellerRating(final Long id) {
        return repository.averageRatingByUserId(id);
    }
}

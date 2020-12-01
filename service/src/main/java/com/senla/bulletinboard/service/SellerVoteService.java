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
import com.senla.bulletinboard.utils.Translator;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class SellerVoteService extends AbstractService<SellerVoteDto, SellerVoteEntity, SellerVoteRepository> {

    private final BulletinRepository bulletinRepository;

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
            final String message = Translator.toLocale("bulletin-closed", sellerVoteDto.getBulletinId());
            log.error(message);
            throw new BulletinIsClosedException(message);
        } else if (!bulletinEntity.isPresent()) {
            final String message = Translator.toLocale("bulletin-not-exists", sellerVoteDto.getBulletinId());
            log.error(message);
            throw new EntityNotFoundException(message);
        }
        if (bulletinEntity.get().getSeller().getId().equals(sellerVoteDto.getVoterId())) {
            final String message = Translator.toLocale("vote-forbidden");
            throw new WrongVoterException(message);
        }
        if (checkVoteExistence(sellerVoteDto)) {
            final String message = Translator.toLocale("user-already-voted",
                        sellerVoteDto.getVoterId(),
                        sellerVoteDto.getBulletinId());
            log.warn(message);
            throw new VoteAlreadyExistsException(message);
        }
        return super.post(sellerVoteDto);
    }

    public boolean checkVoteExistence(final SellerVoteDto sellerVoteDto) {
        return repository.existsByVoterIdAndBulletinId(sellerVoteDto.getVoterId(), sellerVoteDto.getBulletinId());
    }
}

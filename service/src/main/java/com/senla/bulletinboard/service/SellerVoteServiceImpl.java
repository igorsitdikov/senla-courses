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
import com.senla.bulletinboard.service.interfaces.SellerVoteService;
import com.senla.bulletinboard.utils.Translator;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SellerVoteServiceImpl extends AbstractService<SellerVoteDto, SellerVoteEntity, SellerVoteRepository>
    implements
    SellerVoteService {

    private final BulletinRepository bulletinRepository;

    public SellerVoteServiceImpl(final DtoEntityMapper<SellerVoteDto, SellerVoteEntity> dtoEntityMapper,
                                 final SellerVoteRepository repository,
                                 final BulletinRepository bulletinRepository,
                                 final Translator translator) {
        super(dtoEntityMapper, repository, translator);
        this.bulletinRepository = bulletinRepository;
    }

    @Override
    @PreAuthorize("authentication.principal.id == #sellerVoteDto.getVoterId()")
    public IdDto addVoteToBulletin(final SellerVoteDto sellerVoteDto)
        throws WrongVoterException, EntityNotFoundException, BulletinIsClosedException, VoteAlreadyExistsException {
        final Optional<BulletinEntity> bulletinEntity = bulletinRepository.findById(sellerVoteDto.getBulletinId());
        if (bulletinEntity.isPresent() && bulletinEntity.get().getStatus() == BulletinStatus.CLOSE) {
            final String message = translator.toLocale("bulletin-closed", sellerVoteDto.getBulletinId());
            throw new BulletinIsClosedException(message);
        } else if (!bulletinEntity.isPresent()) {
            final String message = translator.toLocale("bulletin-not-exists", sellerVoteDto.getBulletinId());
            throw new EntityNotFoundException(message);
        }
        if (bulletinEntity.get().getSeller().getId().equals(sellerVoteDto.getVoterId())) {
            final String message = translator.toLocale("vote-forbidden");
            throw new WrongVoterException(message);
        }
        if (checkVoteExistence(sellerVoteDto)) {
            final String message = translator.toLocale("user-already-voted",
                                                       sellerVoteDto.getVoterId(),
                                                       sellerVoteDto.getBulletinId());
            throw new VoteAlreadyExistsException(message);
        }
        return super.post(sellerVoteDto);
    }

    public boolean checkVoteExistence(final SellerVoteDto sellerVoteDto) {
        return repository.existsByVoterIdAndBulletinId(sellerVoteDto.getVoterId(), sellerVoteDto.getBulletinId());
    }
}

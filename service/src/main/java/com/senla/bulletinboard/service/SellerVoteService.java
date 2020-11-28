package com.senla.bulletinboard.service;

import com.senla.bulletinboard.dto.SellerVoteDto;
import com.senla.bulletinboard.entity.SellerVoteEntity;
import com.senla.bulletinboard.mapper.interfaces.DtoEntityMapper;
import com.senla.bulletinboard.repository.SellerVoteRepository;
import org.springframework.stereotype.Service;

@Service
public class SellerVoteService extends AbstractService<SellerVoteDto, SellerVoteEntity, SellerVoteRepository> {

    private SellerVoteService(final DtoEntityMapper<SellerVoteDto, SellerVoteEntity> dtoEntityMapper,
                              final SellerVoteRepository repository) {
        super(dtoEntityMapper, repository);
    }

    public Double averageSellerRating(final Long id) {
        return repository.averageRatingByUserId(id);
    }
}

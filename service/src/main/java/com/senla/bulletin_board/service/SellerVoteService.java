package com.senla.bulletin_board.service;

import com.senla.bulletin_board.dto.SellerVoteDto;
import com.senla.bulletin_board.entity.SellerVoteEntity;
import com.senla.bulletin_board.mapper.interfaces.DtoEntityMapper;
import com.senla.bulletin_board.repository.SellerVoteRepository;
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

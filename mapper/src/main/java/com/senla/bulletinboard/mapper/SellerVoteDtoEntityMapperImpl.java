package com.senla.bulletinboard.mapper;

import com.senla.bulletinboard.dto.SellerVoteDto;
import com.senla.bulletinboard.entity.SellerVoteEntity;
import com.senla.bulletinboard.mapper.interfaces.SellerVoteDtoEntityMapper;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class SellerVoteDtoEntityMapperImpl implements SellerVoteDtoEntityMapper {

    @Override
    public SellerVoteEntity sourceToDestination(final SellerVoteDto source) {
        final SellerVoteEntity destination = new SellerVoteEntity();
        destination.setBulletinId(source.getBulletinId());
        destination.setId(source.getId());
        destination.setVoterId(source.getVoterId());
        destination.setVote(source.getVote());
        return destination;
    }

    @Override
    public SellerVoteDto destinationToSource(final SellerVoteEntity destination) {
        final SellerVoteDto source = new SellerVoteDto();
        source.setBulletinId(destination.getBulletinId());
        source.setId(destination.getId());
        source.setVoterId(destination.getVoterId());
        source.setVote(destination.getVote());
        return source;
    }
}

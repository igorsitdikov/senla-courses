package com.senla.bulletinboard.mapper;

import com.senla.bulletinboard.dto.BulletinBaseDto;
import com.senla.bulletinboard.entity.BulletinEntity;
import com.senla.bulletinboard.mapper.interfaces.BulletinDtoEntityMapper;
import com.senla.bulletinboard.mapper.interfaces.UserDtoEntityMapper;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class BulletinDtoEntityMapperImpl implements BulletinDtoEntityMapper {

    private final UserDtoEntityMapper userDtoEntityMapper;

    @Override
    public BulletinEntity sourceToDestination(final BulletinBaseDto source) {
        BulletinEntity destination = new BulletinEntity();
        destination.setId(source.getId());
        destination.setTitle(source.getTitle());
        destination.setCreatedAt(source.getCreatedAt());
        destination.setPrice(source.getPrice());
        destination.setSellerId(source.getSellerId());
        if (source.getSeller() != null) {
            destination.setSeller(userDtoEntityMapper.sourceToDestination(source.getSeller()));
        }
        return destination;
    }

    @Override
    public BulletinBaseDto destinationToSource(final BulletinEntity destination) {
        BulletinBaseDto source = new BulletinBaseDto();
        source.setId(destination.getId());
        source.setId(destination.getId());
        source.setTitle(destination.getTitle());
        source.setCreatedAt(destination.getCreatedAt());
        source.setPrice(destination.getPrice());
        source.setSellerId(destination.getSellerId());
        if (source.getSeller() != null) {
            source.setSeller(userDtoEntityMapper.destinationToSource(destination.getSeller()));
        }
        return source;
    }
}

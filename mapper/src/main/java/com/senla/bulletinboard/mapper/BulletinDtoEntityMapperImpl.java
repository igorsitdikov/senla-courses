package com.senla.bulletinboard.mapper;

import com.senla.bulletinboard.dto.BulletinBaseDto;
import com.senla.bulletinboard.dto.BulletinDto;
import com.senla.bulletinboard.entity.BulletinEntity;
import com.senla.bulletinboard.mapper.interfaces.BulletinDetailsDtoEntityMapper;
import com.senla.bulletinboard.mapper.interfaces.BulletinDtoEntityMapper;
import com.senla.bulletinboard.mapper.interfaces.CommentDtoEntityMapper;
import com.senla.bulletinboard.mapper.interfaces.UserDtoEntityMapper;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

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
        destination.setSeller(userDtoEntityMapper.sourceToDestination(source.getAuthor()));
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
        source.setAuthor(userDtoEntityMapper.destinationToSource(destination.getSeller()));
        return source;
    }
}

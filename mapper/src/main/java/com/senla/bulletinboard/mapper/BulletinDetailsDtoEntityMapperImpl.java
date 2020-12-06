package com.senla.bulletinboard.mapper;

import com.senla.bulletinboard.dto.BulletinDto;
import com.senla.bulletinboard.entity.BulletinEntity;
import com.senla.bulletinboard.mapper.interfaces.BulletinDetailsDtoEntityMapper;
import com.senla.bulletinboard.mapper.interfaces.CommentDtoEntityMapper;
import com.senla.bulletinboard.mapper.interfaces.UserDtoEntityMapper;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Data
@Component
public class BulletinDetailsDtoEntityMapperImpl implements BulletinDetailsDtoEntityMapper {

    private final UserDtoEntityMapper userDtoEntityMapper;
    private final CommentDtoEntityMapper commentDtoEntityMapper;

    @Override
    public BulletinEntity sourceToDestination(final BulletinDto source) {
        BulletinEntity destination = new BulletinEntity();
        destination.setId(source.getId());
        destination.setTitle(source.getTitle());
        destination.setText(source.getDescription());
        destination.setCreatedAt(source.getCreatedAt());
        destination.setPrice(source.getPrice());
        destination.setSellerId(source.getSellerId());
        if (source.getComments() != null) {
            destination.setComments(source.getComments()
                                        .stream()
                                        .map(commentDtoEntityMapper::sourceToDestination)
                                        .collect(Collectors.toSet()));
        }
        destination.setStatus(source.getStatus());
        if (source.getSeller() != null) {
            destination.setSeller(userDtoEntityMapper.sourceToDestination(source.getSeller()));
        }
        return destination;
    }

    @Override
    public BulletinDto destinationToSource(final BulletinEntity destination) {
        BulletinDto source = new BulletinDto();
        source.setId(destination.getId());
        source.setId(destination.getId());
        source.setTitle(destination.getTitle());
        source.setDescription(destination.getText());
        source.setCreatedAt(destination.getCreatedAt());
        source.setPrice(destination.getPrice());
        source.setSeller(userDtoEntityMapper.destinationToSource(destination.getSeller()));
        source.setSellerId(destination.getSellerId());
        if (destination.getComments() != null) {
            source.setComments(destination.getComments()
                                   .stream()
                                   .map(commentDtoEntityMapper::destinationToSource)
                                   .collect(Collectors.toList()));
        } else {
            source.setComments(new ArrayList<>());
        }
        source.setStatus(destination.getStatus());
        return source;
    }
}

package com.senla.bulletin_board.mapper;

import com.senla.bulletin_board.dto.BulletinDto;
import com.senla.bulletin_board.entity.BulletinEntity;
import com.senla.bulletin_board.mapper.interfaces.BulletinDtoEntityMapper;
import com.senla.bulletin_board.mapper.interfaces.CommentDtoEntityMapper;
import com.senla.bulletin_board.mapper.interfaces.UserDtoEntityMapper;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Data
@Component
public class BulletinDetailsDtoEntityMapperImpl implements BulletinDtoEntityMapper {

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
        if (source.getComments() != null) {
            destination.setComments(source.getComments()
                                        .stream()
                                        .map(commentDtoEntityMapper::sourceToDestination)
                                        .collect(Collectors.toSet()));
        }
        destination.setStatus(source.getStatus());
        destination.setSeller(userDtoEntityMapper.sourceToDestination(source.getAuthor()));
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
        source.setAuthor(userDtoEntityMapper.destinationToSource(destination.getSeller()));
//        if (destination.getComments() != null) {
//            source.setComments(destination.getComments()
//                                   .stream()
//                                   .map(commentDtoEntityMapper::destinationToSource)
//                                   .collect(Collectors.toList()));
//        } else {
            source.setComments(new ArrayList<>());
//        }
        source.setStatus(destination.getStatus());
        return source;
    }
}

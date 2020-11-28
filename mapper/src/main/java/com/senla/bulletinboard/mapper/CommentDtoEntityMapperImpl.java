package com.senla.bulletinboard.mapper;

import com.senla.bulletinboard.dto.CommentDto;
import com.senla.bulletinboard.entity.CommentEntity;
import com.senla.bulletinboard.mapper.interfaces.CommentDtoEntityMapper;
import com.senla.bulletinboard.mapper.interfaces.UserDtoEntityMapper;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class CommentDtoEntityMapperImpl implements CommentDtoEntityMapper {

    private final UserDtoEntityMapper userDtoEntityMapper;

    @Override
    public CommentEntity sourceToDestination(final CommentDto source) {
        CommentEntity destination = new CommentEntity();
        destination.setText(source.getComment());
        if (source.getAuthor() != null) {
            destination.setUser(userDtoEntityMapper.sourceToDestination(source.getAuthor()));
        }
        destination.setCreatedAt(source.getCreatedAt());
        destination.setAuthorId(source.getAuthorId());
        destination.setBulletinId(source.getBulletinId());
        return destination;
    }

    @Override
    public CommentDto destinationToSource(final CommentEntity destination) {
        CommentDto source = new CommentDto();
        source.setAuthor(userDtoEntityMapper.destinationToSource(destination.getUser()));
        source.setComment(destination.getText());
        source.setCreatedAt(destination.getCreatedAt());
        source.setBulletinId(destination.getBulletinId());
        return source;
    }
}

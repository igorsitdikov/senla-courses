package com.senla.bulletin_board.mapper;

import com.senla.bulletin_board.dto.DialogDto;
import com.senla.bulletin_board.entity.DialogEntity;
import com.senla.bulletin_board.mapper.interfaces.BulletinDtoEntityMapper;
import com.senla.bulletin_board.mapper.interfaces.DialogDtoEntityMapper;
import com.senla.bulletin_board.mapper.interfaces.UserDtoEntityMapper;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class DialogDtoEntityMapperImpl implements DialogDtoEntityMapper {

    private final UserDtoEntityMapper userDtoEntityMapper;
    private final BulletinDtoEntityMapper bulletinDtoEntityMapper;

    @Override
    public DialogEntity sourceToDestination(final DialogDto source) {
        DialogEntity destination = new DialogEntity();
        destination.setBulletinId(source.getBulletinId());
        destination.setCustomerId(source.getCustomerId());
        return destination;
    }

    @Override
    public DialogDto destinationToSource(final DialogEntity destination) {
        DialogDto source = new DialogDto();
        source.setId(destination.getId());
        source.setTitle(destination.getBulletin().getTitle());
        source.setBulletinId(destination.getBulletinId());
        source.setUser(userDtoEntityMapper.destinationToSource(destination.getCustomer()));
        source.setBulletin(bulletinDtoEntityMapper.destinationToSource(destination.getBulletin()));
        source.setCustomerId(destination.getCustomerId());
        source.setCreatedAt(destination.getCreatedAt());
        return source;
    }
}

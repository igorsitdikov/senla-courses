package com.senla.bulletinboard.mapper;

import com.senla.bulletinboard.dto.DialogDto;
import com.senla.bulletinboard.entity.DialogEntity;
import com.senla.bulletinboard.mapper.interfaces.DialogDtoEntityMapper;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class DialogDtoEntityMapperImpl implements DialogDtoEntityMapper {

    @Override
    public DialogEntity sourceToDestination(final DialogDto source) {
        DialogEntity destination = new DialogEntity();
        destination.setId(source.getId());
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
        source.setCustomerId(destination.getCustomerId());
        source.setCreatedAt(destination.getCreatedAt());
        return source;
    }
}

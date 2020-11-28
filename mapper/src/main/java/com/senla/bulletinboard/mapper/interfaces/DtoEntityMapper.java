package com.senla.bulletinboard.mapper.interfaces;

import com.senla.bulletinboard.dto.AbstractDto;
import com.senla.bulletinboard.entity.AbstractEntity;

public interface DtoEntityMapper<D extends AbstractDto, E extends AbstractEntity> {

    E sourceToDestination(D source);

    D destinationToSource(E destination);
}

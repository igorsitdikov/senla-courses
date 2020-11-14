package com.senla.bulletin_board.mapper.interfaces;

import com.senla.bulletin_board.dto.AbstractDto;
import com.senla.bulletin_board.entity.AbstractEntity;

public interface DtoEntityMapper<D extends AbstractDto, E extends AbstractEntity> {

    E sourceToDestination(D source);

    D destinationToSource(E destination);
}

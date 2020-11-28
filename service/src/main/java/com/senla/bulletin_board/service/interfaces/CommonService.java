package com.senla.bulletin_board.service.interfaces;

import com.senla.bulletin_board.dto.AbstractDto;
import com.senla.bulletin_board.dto.IdDto;
import com.senla.bulletin_board.entity.AbstractEntity;
import com.senla.bulletin_board.exception.EntityNotFoundException;

import java.util.List;

public interface CommonService<D extends AbstractDto, E extends AbstractEntity> {

    List<D> findAllDto();

    List<E> findAll();

    IdDto post(D dto);

    D update(Long id, D dto);

    D findDtoById(Long id) throws EntityNotFoundException;

    E findEntityById(Long id) throws EntityNotFoundException;

    void delete(Long id);

    E save(E entity);

    boolean isExists(Long id);
}

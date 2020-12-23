package com.senla.bulletinboard.service;

import com.senla.bulletinboard.dto.AbstractDto;
import com.senla.bulletinboard.dto.IdDto;
import com.senla.bulletinboard.entity.AbstractEntity;
import com.senla.bulletinboard.exception.EntityNotFoundException;

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

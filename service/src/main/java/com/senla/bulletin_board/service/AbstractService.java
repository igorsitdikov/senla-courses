package com.senla.bulletin_board.service;

import com.senla.bulletin_board.dto.AbstractDto;
import com.senla.bulletin_board.dto.IdDto;
import com.senla.bulletin_board.entity.AbstractEntity;
import com.senla.bulletin_board.exception.EntityNotFoundException;
import com.senla.bulletin_board.mapper.interfaces.DtoEntityMapper;
import com.senla.bulletin_board.repository.CommonRepository;
import com.senla.bulletin_board.service.interfaces.CommonService;
import lombok.Data;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public abstract class AbstractService<D extends AbstractDto, E extends AbstractEntity, R extends CommonRepository<E, Long>>
    implements CommonService<D, E> {

    private final DtoEntityMapper<D, E> dtoEntityMapper;
    protected final R repository;

    @Override
    public List<D> findAllDto() {
        final List<E> all = findAll();
        return all
            .stream()
            .map(dtoEntityMapper::destinationToSource)
            .collect(Collectors.toList());
    }

    @Override
    public List<E> findAll() {
        return repository.findAll();
    }

    @Override
    public IdDto post(final D dto) {
        final E entity = dtoEntityMapper.sourceToDestination(dto);
        final Long id = save(entity).getId();
        return new IdDto(id);
    }

    @Override
    public D update(final Long id, final D dto) {
        final E entity = dtoEntityMapper.sourceToDestination(dto);
        return dtoEntityMapper.destinationToSource(save(entity));
    }

    @Override
    public D findDtoById(final Long id) {
        return dtoEntityMapper.destinationToSource(findEntityById(id));
    }

    @Override
    public E findEntityById(final Long id) {
        final Optional<E> entityById = repository.findById(id);
        return entityById.get();
    }

    @Override
    public void delete(final Long id) {
        repository.deleteById(id);
    }

    @Override
    public E save(final E entity) {
        return repository.save(entity);
    }

    @Override
    public boolean isExists(final Long id) {
        return repository.existsById(id);
    }
}

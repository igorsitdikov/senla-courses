package com.senla.bulletinboard.service;

import com.senla.bulletinboard.dto.AbstractDto;
import com.senla.bulletinboard.dto.IdDto;
import com.senla.bulletinboard.entity.AbstractEntity;
import com.senla.bulletinboard.exception.EntityNotFoundException;
import com.senla.bulletinboard.mapper.interfaces.DtoEntityMapper;
import com.senla.bulletinboard.repository.CommonRepository;
import com.senla.bulletinboard.service.interfaces.CommonService;
import com.senla.bulletinboard.utils.Translator;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractService<D extends AbstractDto, E extends AbstractEntity, R extends CommonRepository<E, Long>>
    implements CommonService<D, E> {

    protected final Translator translator;
    protected final DtoEntityMapper<D, E> dtoEntityMapper;
    protected final R repository;
    private final Class<E> persistentClass;

    @SuppressWarnings("unchecked")
    public AbstractService(final DtoEntityMapper<D, E> dtoEntityMapper, final R repository, final Translator translator) {
        this.dtoEntityMapper = dtoEntityMapper;
        this.repository = repository;
        this.translator = translator;
        this.persistentClass = (Class<E>)
            ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[1];
    }

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
        entity.setId(id);
        return dtoEntityMapper.destinationToSource(save(entity));
    }

    @Override
    public D findDtoById(final Long id) throws EntityNotFoundException {
        return dtoEntityMapper.destinationToSource(findEntityById(id));
    }

    @Override
    public E findEntityById(final Long id) throws EntityNotFoundException {
        return repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(
                translator.toLocale("entity-not-found", this.persistentClass.getSimpleName(), id)));
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

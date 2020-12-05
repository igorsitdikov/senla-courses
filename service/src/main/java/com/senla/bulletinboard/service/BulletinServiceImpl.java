package com.senla.bulletinboard.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.bulletinboard.dto.BulletinBaseDto;
import com.senla.bulletinboard.dto.BulletinDto;
import com.senla.bulletinboard.dto.FilterDto;
import com.senla.bulletinboard.entity.BulletinEntity;
import com.senla.bulletinboard.enumerated.SortBulletin;
import com.senla.bulletinboard.exception.EntityNotFoundException;
import com.senla.bulletinboard.exception.NoSuchUserException;
import com.senla.bulletinboard.mapper.interfaces.BulletinDetailsDtoEntityMapper;
import com.senla.bulletinboard.mapper.interfaces.BulletinDtoEntityMapper;
import com.senla.bulletinboard.repository.BulletinRepository;
import com.senla.bulletinboard.repository.UserRepository;
import com.senla.bulletinboard.repository.specification.BulletinFilterSortSpecification;
import com.senla.bulletinboard.service.interfaces.BulletinService;
import com.senla.bulletinboard.utils.Translator;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@Service
public class BulletinServiceImpl extends AbstractService<BulletinDto, BulletinEntity, BulletinRepository> implements
                                                                                                          BulletinService {

    private final ObjectMapper mapper;
    private final BulletinDetailsDtoEntityMapper bulletinDetailsDtoEntityMapper;
    private final BulletinDtoEntityMapper bulletinDtoEntityMapper;
    private final UserRepository userRepository;

    public BulletinServiceImpl(final BulletinDetailsDtoEntityMapper bulletinDetailsDtoEntityMapper,
                               final BulletinDtoEntityMapper bulletinDtoEntityMapper,
                               final BulletinRepository bulletinRepository,
                               final ObjectMapper mapper,
                               final UserRepository userRepository
                              ) {
        super(bulletinDetailsDtoEntityMapper, bulletinRepository);
        this.bulletinDetailsDtoEntityMapper = bulletinDetailsDtoEntityMapper;
        this.bulletinDtoEntityMapper = bulletinDtoEntityMapper;
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    @Override
    @PreAuthorize("authentication.principal.id == #id")
    public List<BulletinBaseDto> findBulletinsByUserId(final Long id) throws NoSuchUserException {
        if (!userRepository.existsById(id)) {
            final String message = Translator.toLocale("no-such-user-id", id);
            log.error(message);
            throw new NoSuchUserException(message);
        }
        return repository.findAllBySellerId(id)
            .stream()
            .map(bulletinDetailsDtoEntityMapper::destinationToSource)
            .collect(Collectors.toList());
    }

    public BulletinDto findBulletinById(final Long id) throws EntityNotFoundException {
        return super.findDtoById(id);
    }

    private void checkBulletinExistence(final Long id) throws EntityNotFoundException {
        if (!super.isExists(id)) {
            final String message = Translator.toLocale("bulletin-not-exists", id);
            log.error(message);
            throw new EntityNotFoundException(message);
        }
    }

    @Override
    @PreAuthorize("authentication.principal.id == #id")
    public BulletinDto updateBulletin(final Long id, final BulletinDto bulletinDto) throws EntityNotFoundException {
        checkBulletinExistence(id);
        return super.update(id, bulletinDto);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or @bulletinServiceImpl.checkOwner(authentication.principal.id, #id)")
    public void deleteBulletin(final Long id) throws EntityNotFoundException {
        checkBulletinExistence(id);
        super.delete(id);
    }

    public boolean checkOwner(final Long userId, final Long bulletinId) throws EntityNotFoundException {
        BulletinEntity bulletinEntity = super.findEntityById(bulletinId);
        return userId.equals(bulletinEntity.getSeller().getId());
    }

    @Override
    public List<BulletinBaseDto> findAllBulletins(final String[] filters, final SortBulletin sort) {
        final FilterDto criteria = convertArrayToDto(filters);
        BulletinFilterSortSpecification bulletinSpecification = new BulletinFilterSortSpecification(criteria, sort);

        return repository.findAll(bulletinSpecification)
            .stream()
            .map(bulletinDtoEntityMapper::destinationToSource)
            .collect(Collectors.toList());
    }

    private FilterDto convertArrayToDto(String[] filters) {
        if (filters == null) {
            return new FilterDto();
        }
        Map<String, String> map = Arrays.stream(filters)
            .map(str -> str.split(":"))
            .collect(Collectors.toMap(a -> a[0], a -> a[1]));
        return mapper.convertValue(map, FilterDto.class);
    }

}

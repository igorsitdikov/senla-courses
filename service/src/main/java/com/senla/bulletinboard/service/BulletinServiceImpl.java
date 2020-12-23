package com.senla.bulletinboard.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.bulletinboard.dto.BulletinBaseDto;
import com.senla.bulletinboard.dto.BulletinDto;
import com.senla.bulletinboard.dto.FilterDto;
import com.senla.bulletinboard.dto.IdDto;
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
import com.senla.bulletinboard.utils.comparator.BulletinRatingAndDateTimeComparator;
import com.senla.bulletinboard.utils.comparator.BulletinPriceComparator;
import com.senla.bulletinboard.utils.comparator.BulletinSellerNameComparator;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                               final UserRepository userRepository,
                               final Translator translator) {
        super(bulletinDetailsDtoEntityMapper, bulletinRepository, translator);
        this.bulletinDetailsDtoEntityMapper = bulletinDetailsDtoEntityMapper;
        this.bulletinDtoEntityMapper = bulletinDtoEntityMapper;
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    @Override
    @PreAuthorize("authentication.principal.id == #id")
    public List<BulletinBaseDto> findBulletinsByUserId(final Long id, final Integer page, final Integer size) throws NoSuchUserException {
        if (!userRepository.existsById(id)) {
            final String message = translator.toLocale("no-such-user-id", id);
            throw new NoSuchUserException(message);
        }
        Pageable pageWithSize = PageRequest.of(page, size);
        return repository.findAllBySellerId(id, pageWithSize)
            .stream()
            .map(bulletinDetailsDtoEntityMapper::destinationToSource)
            .collect(Collectors.toList());
    }

    @Override
    public BulletinDto findBulletinById(final Long id) throws EntityNotFoundException {
        return super.findDtoById(id);
    }

    @Override
    @PreAuthorize("authentication.principal.id == #bulletinDto.getSellerId()")
    public IdDto createBulletin(final BulletinDto bulletinDto) {
        return super.post(bulletinDto);
    }

    @Override
    @Transactional
    @PreAuthorize("authentication.principal.id == #bulletinDto.getSellerId()")
    public BulletinDto updateBulletin(final Long id, final BulletinDto bulletinDto) throws EntityNotFoundException {
        BulletinEntity entity = repository.findById(id).orElseThrow(() -> {
            final String message = translator.toLocale("bulletin-not-exists", id);
            return new EntityNotFoundException(message);
        });
        entity.setStatus(bulletinDto.getStatus());
        entity.setPrice(bulletinDto.getPrice());
        entity.setText(bulletinDto.getDescription());
        entity.setTitle(bulletinDto.getTitle());
        final BulletinEntity savedBulletin = repository.save(entity);
        return bulletinDetailsDtoEntityMapper.destinationToSource(savedBulletin);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or @bulletinServiceImpl.checkOwner(authentication.principal.id, #id)")
    public void deleteBulletin(final Long id) throws EntityNotFoundException {
        checkBulletinExistence(id);
        super.delete(id);
    }

    private void checkBulletinExistence(final Long id) throws EntityNotFoundException {
        if (!super.isExists(id)) {
            final String message = translator.toLocale("bulletin-not-exists", id);
            throw new EntityNotFoundException(message);
        }
    }

    @Cacheable(value = "bulletinOwner", key = "#bulletinId")
    public boolean checkOwner(final Long userId, final Long bulletinId) throws EntityNotFoundException {
        BulletinEntity bulletinEntity = super.findEntityById(bulletinId);
        return userId.equals(bulletinEntity.getSeller().getId());
    }

    @Override
    public List<BulletinBaseDto> findAllBulletins(final String[] filters, SortBulletin sort, Integer page, Integer size) {
        final FilterDto criteria = convertArrayToDto(filters);
        BulletinFilterSortSpecification bulletinSpecification = new BulletinFilterSortSpecification(criteria, page, size);
        return repository.findAll(bulletinSpecification)
            .stream()
            .sorted(selectComparator(sort))
            .map(bulletinDtoEntityMapper::destinationToSource)
            .collect(Collectors.toList());
    }

    private Comparator<BulletinEntity> selectComparator(SortBulletin sort) {
        if (sort == null) {
            sort = SortBulletin.DEFAULT;
        }
        switch (sort) {
            case PRICE:
                return new BulletinPriceComparator();
            case AUTHOR:
                return new BulletinSellerNameComparator();
            default:
                return new BulletinRatingAndDateTimeComparator();
        }
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

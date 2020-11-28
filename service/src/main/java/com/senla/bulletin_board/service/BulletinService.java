package com.senla.bulletin_board.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.bulletin_board.dto.BulletinBaseDto;
import com.senla.bulletin_board.dto.BulletinDto;
import com.senla.bulletin_board.dto.FilterDto;
import com.senla.bulletin_board.entity.BulletinEntity;
import com.senla.bulletin_board.enumerated.SortBulletin;
import com.senla.bulletin_board.exception.EntityNotFoundException;
import com.senla.bulletin_board.exception.NoSuchUserException;
import com.senla.bulletin_board.mapper.interfaces.BulletinDtoEntityMapper;
import com.senla.bulletin_board.repository.BulletinRepository;
import com.senla.bulletin_board.repository.UserRepository;
import com.senla.bulletin_board.repository.specification.BulletinFilterSortSpecification;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@Service
public class BulletinService extends AbstractService<BulletinDto, BulletinEntity, BulletinRepository> {

    private final ObjectMapper mapper;
    private final BulletinDtoEntityMapper bulletinDtoEntityMapper;
    private final UserRepository userRepository;

    public BulletinService(final BulletinDtoEntityMapper bulletinDtoEntityMapper,
                           final BulletinRepository bulletinRepository,
                           final ObjectMapper mapper,
                           final UserRepository userRepository
                          ) {
        super(bulletinDtoEntityMapper, bulletinRepository);
        this.bulletinDtoEntityMapper = bulletinDtoEntityMapper;
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    public List<BulletinBaseDto> findBulletinsByUserId(final Long id) throws NoSuchUserException {
        if (!userRepository.existsById(id)) {
            final String message = String.format("User with such id %d does not exist", id);
            log.error(message);
            throw new NoSuchUserException(message);
        }
        return repository.findAllBySellerId(id)
            .stream()
            .map(bulletinDtoEntityMapper::destinationToSource)
            .collect(Collectors.toList());
    }

    private void checkBulletinExistence(final Long id) throws EntityNotFoundException {
        if (!super.isExists(id)) {
            final String message = String.format("Bulletin with such id %d does not exist", id);
            log.error(message);
            throw new EntityNotFoundException(message);
        }
    }

    public BulletinDto updateBulletin(final Long id, final BulletinDto bulletinDto) throws EntityNotFoundException {
        checkBulletinExistence(id);
        return super.update(id, bulletinDto);
    }

    public void deleteBulletin(final Long id) throws EntityNotFoundException {
        checkBulletinExistence(id);
        super.delete(id);
    }

    public List<BulletinBaseDto> findAllBulletins(final String[] filters, final SortBulletin sort) {
        final FilterDto criteria = convertArrayToDto(filters);
        BulletinFilterSortSpecification bulletinSpecification = new BulletinFilterSortSpecification(criteria, sort);

        return repository.findAll(bulletinSpecification)
            .stream()
            .map(bulletinDtoEntityMapper::destinationToSource)
            .collect(Collectors.toList());
//        return repository.findAllSortedByRating()
//            .stream()
//            .map(bulletinDtoEntityMapper::destinationToSource)
//            .collect(Collectors.toList());
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

package com.senla.bulletin_board.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.bulletin_board.dto.BulletinBaseDto;
import com.senla.bulletin_board.dto.BulletinDto;
import com.senla.bulletin_board.dto.FilterDto;
import com.senla.bulletin_board.entity.BulletinEntity;
import com.senla.bulletin_board.mapper.interfaces.BulletinDtoEntityMapper;
import com.senla.bulletin_board.repository.BulletinRepository;
import com.senla.bulletin_board.repository.specification.BulletinSpecification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BulletinService extends AbstractService<BulletinDto, BulletinEntity, BulletinRepository> {

    private final ObjectMapper mapper;
    private final BulletinDtoEntityMapper bulletinDtoEntityMapper;

    public BulletinService(final BulletinDtoEntityMapper bulletinDtoEntityMapper,
                           final BulletinRepository bulletinRepository,
                           final ObjectMapper mapper) {
        super(bulletinDtoEntityMapper, bulletinRepository);
        this.bulletinDtoEntityMapper = bulletinDtoEntityMapper;
        this.mapper = mapper;
    }

    public List<BulletinBaseDto> showAll() {
        final List<BulletinDto> bulletinDtos = findAllDto();
        return new ArrayList<>(bulletinDtos);
    }

    public List<BulletinBaseDto> findBulletinsByUserId(final Long id) {
        return repository.findAllBySellerId(id)
            .stream()
            .map(bulletinDtoEntityMapper::destinationToSource)
            .collect(Collectors.toList());

    }

    public List<BulletinBaseDto> findAllWithFilter(final String[] filters) {
        final FilterDto criteria = convertArrayToDto(filters);
        BulletinSpecification bulletinSpecification = new BulletinSpecification(criteria);

        return repository.findAll(bulletinSpecification)
            .stream()
            .map(bulletinDtoEntityMapper::destinationToSource)
            .collect(Collectors.toList());
    }

    private FilterDto convertArrayToDto(String[] filters) {
        Map<String, String> map = Arrays.stream(filters)
            .map(str -> str.split(":"))
            .collect(Collectors.toMap(a -> a[0], a -> a[1]));
        return mapper.convertValue(map, FilterDto.class);
    }

}

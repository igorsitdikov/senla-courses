package com.senla.bulletin_board.service;

import com.senla.bulletin_board.dto.BulletinBaseDto;
import com.senla.bulletin_board.dto.BulletinDto;
import com.senla.bulletin_board.entity.BulletinEntity;
import com.senla.bulletin_board.mapper.interfaces.BulletinDtoEntityMapper;
import com.senla.bulletin_board.repository.BulletinRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BulletinService extends AbstractService<BulletinDto, BulletinEntity, BulletinRepository> {

    private final BulletinDtoEntityMapper bulletinDtoEntityMapper;

    public BulletinService(final BulletinDtoEntityMapper bulletinDtoEntityMapper,
                           final BulletinRepository bulletinRepository) {
        super(bulletinDtoEntityMapper, bulletinRepository);
        this.bulletinDtoEntityMapper = bulletinDtoEntityMapper;
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

}

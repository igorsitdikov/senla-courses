package com.senla.bulletin_board.service;

import com.senla.bulletin_board.dto.BulletinDto;
import com.senla.bulletin_board.dto.BulletinBaseDto;
import com.senla.bulletin_board.entity.BulletinEntity;
import com.senla.bulletin_board.mapper.interfaces.BulletinDtoEntityMapper;
import com.senla.bulletin_board.repository.BulletinRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BulletinService extends AbstractService<BulletinDto, BulletinEntity, BulletinRepository> {

    public BulletinService(final BulletinDtoEntityMapper bulletinDetailsDtoEntityMapper,
                           final BulletinRepository bulletinRepository) {
        super(bulletinDetailsDtoEntityMapper, bulletinRepository);
    }

    public List<BulletinBaseDto> showAll() {
        final List<BulletinDto> bulletinDtos = findAllDto();
        return new ArrayList<>(bulletinDtos);
    }
}

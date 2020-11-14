package com.senla.bulletin_board.service;

import com.senla.bulletin_board.dto.TariffDto;
import com.senla.bulletin_board.entity.TariffEntity;
import com.senla.bulletin_board.mapper.interfaces.TariffDtoEntityMapper;
import com.senla.bulletin_board.repository.TariffRepository;
import org.springframework.stereotype.Service;

@Service
public class TariffService extends AbstractService<TariffDto, TariffEntity, TariffRepository> {

    public TariffService(TariffDtoEntityMapper tariffDtoEntityMapper, TariffRepository repository) {
        super(tariffDtoEntityMapper, repository);
    }

}

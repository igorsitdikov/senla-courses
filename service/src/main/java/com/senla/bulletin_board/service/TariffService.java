package com.senla.bulletin_board.service;

import com.senla.bulletin_board.dto.TariffDto;
import com.senla.bulletin_board.entity.TariffEntity;
import com.senla.bulletin_board.exception.EntityNotFoundException;
import com.senla.bulletin_board.mapper.interfaces.TariffDtoEntityMapper;
import com.senla.bulletin_board.repository.TariffRepository;
import org.springframework.stereotype.Service;

@Service
public class TariffService extends AbstractService<TariffDto, TariffEntity, TariffRepository> {

    public TariffService(TariffDtoEntityMapper tariffDtoEntityMapper, TariffRepository repository) {
        super(tariffDtoEntityMapper, repository);
    }

    public TariffDto updateTariff(final Long id, final TariffDto dto) throws EntityNotFoundException {
        if (!super.isExists(id)) {
            throw new EntityNotFoundException(String.format("Tariff with id %s was not found", id));
        }
        return super.update(id, dto);
    }
}

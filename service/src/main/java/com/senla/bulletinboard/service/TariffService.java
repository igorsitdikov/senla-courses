package com.senla.bulletinboard.service;

import com.senla.bulletinboard.dto.IdDto;
import com.senla.bulletinboard.dto.TariffDto;
import com.senla.bulletinboard.entity.TariffEntity;
import com.senla.bulletinboard.exception.EntityNotFoundException;
import com.senla.bulletinboard.mapper.interfaces.TariffDtoEntityMapper;
import com.senla.bulletinboard.repository.TariffRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class TariffService extends AbstractService<TariffDto, TariffEntity, TariffRepository> {

    public TariffService(TariffDtoEntityMapper tariffDtoEntityMapper, TariffRepository repository) {
        super(tariffDtoEntityMapper, repository);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public TariffDto updateTariff(final Long id, final TariffDto dto) throws EntityNotFoundException {
        if (!super.isExists(id)) {
            final String message = String.format("Tariff with id %s was not found", id);
            log.error(message);
            throw new EntityNotFoundException(message);
        }
        return super.update(id, dto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public IdDto post(final TariffDto tariffDto) {
        return super.post(tariffDto);
    }
}

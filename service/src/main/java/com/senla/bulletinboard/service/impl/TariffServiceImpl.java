package com.senla.bulletinboard.service.impl;

import com.senla.bulletinboard.dto.IdDto;
import com.senla.bulletinboard.dto.TariffDto;
import com.senla.bulletinboard.entity.TariffEntity;
import com.senla.bulletinboard.exception.EntityNotFoundException;
import com.senla.bulletinboard.mapper.interfaces.TariffDtoEntityMapper;
import com.senla.bulletinboard.repository.TariffRepository;
import com.senla.bulletinboard.service.TariffService;
import com.senla.bulletinboard.utils.Translator;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class TariffServiceImpl extends AbstractService<TariffDto, TariffEntity, TariffRepository> implements
                                                                                                  TariffService {

    public TariffServiceImpl(final TariffDtoEntityMapper tariffDtoEntityMapper,
                             final TariffRepository repository,
                             final Translator translator) {
        super(tariffDtoEntityMapper, repository, translator);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public TariffDto updateTariff(final Long id, final TariffDto dto) throws EntityNotFoundException {
        if (!super.isExists(id)) {
            final String message = translator.toLocale("tariff-not-exists", id);
            throw new EntityNotFoundException(message);
        }
        return super.update(id, dto);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public IdDto post(final TariffDto tariffDto) {
        return super.post(tariffDto);
    }
}

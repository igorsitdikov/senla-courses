package com.senla.bulletinboard.service.interfaces;

import com.senla.bulletinboard.dto.TariffDto;
import com.senla.bulletinboard.entity.TariffEntity;
import com.senla.bulletinboard.exception.EntityNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;

public interface TariffService extends CommonService<TariffDto, TariffEntity> {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    TariffDto updateTariff(Long id, TariffDto dto) throws EntityNotFoundException;
}

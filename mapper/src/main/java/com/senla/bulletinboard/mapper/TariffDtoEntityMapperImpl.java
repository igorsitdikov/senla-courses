package com.senla.bulletinboard.mapper;

import com.senla.bulletinboard.dto.TariffDto;
import com.senla.bulletinboard.entity.TariffEntity;
import com.senla.bulletinboard.mapper.interfaces.TariffDtoEntityMapper;
import org.springframework.stereotype.Component;

@Component
public class TariffDtoEntityMapperImpl implements TariffDtoEntityMapper {

    @Override
    public TariffEntity sourceToDestination(final TariffDto source) {
        TariffEntity destination = new TariffEntity();
        destination.setId(source.getId());
        destination.setPrice(source.getPrice());
        destination.setDescription(source.getDescription());
        destination.setTerm(source.getTerm());
        return destination;
    }

    @Override
    public TariffDto destinationToSource(final TariffEntity destination) {
        TariffDto source = new TariffDto();
        source.setId(destination.getId());
        source.setPrice(destination.getPrice());
        source.setDescription(destination.getDescription());
        source.setTerm(destination.getTerm());
        return source;
    }
}

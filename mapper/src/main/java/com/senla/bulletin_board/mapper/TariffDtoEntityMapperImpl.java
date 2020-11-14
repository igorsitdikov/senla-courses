package com.senla.bulletin_board.mapper;

import com.senla.bulletin_board.dto.TariffDto;
import com.senla.bulletin_board.entity.TariffEntity;
import com.senla.bulletin_board.mapper.interfaces.TariffDtoEntityMapper;
import org.springframework.stereotype.Component;

@Component
public class TariffDtoEntityMapperImpl implements TariffDtoEntityMapper {

    @Override
    public TariffEntity sourceToDestination(final TariffDto source) {
        TariffEntity destination = new TariffEntity();
        destination.setId(source.getId());
        destination.setValue(source.getPrice());
        destination.setDescription(source.getDescription());
        destination.setTerm(source.getTerm());
        return destination;
    }

    @Override
    public TariffDto destinationToSource(final TariffEntity destination) {
        TariffDto source = new TariffDto();
        source.setId(destination.getId());
        source.setPrice(destination.getValue());
        source.setDescription(destination.getDescription());
        source.setTerm(destination.getTerm());
        return source;
    }
}

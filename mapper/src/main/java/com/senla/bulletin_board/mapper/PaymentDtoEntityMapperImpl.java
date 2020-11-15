package com.senla.bulletin_board.mapper;

import com.senla.bulletin_board.dto.PaymentDto;
import com.senla.bulletin_board.entity.PaymentEntity;
import com.senla.bulletin_board.mapper.interfaces.PaymentDtoEntityMapper;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class PaymentDtoEntityMapperImpl implements PaymentDtoEntityMapper {

    @Override
    public PaymentEntity sourceToDestination(final PaymentDto source) {
        final PaymentEntity destination = new PaymentEntity();
        destination.setPayedAt(source.getPayedAt());
        destination.setUserId(source.getUserId());
        destination.setTariffId(source.getTariffId());
        return destination;
    }

    @Override
    public PaymentDto destinationToSource(final PaymentEntity destination) {
        final PaymentDto source = new PaymentDto();
        source.setPayedAt(destination.getPayedAt());
        source.setUserId(destination.getUserId());
        source.setTariffId(destination.getTariffId());
        return source;
    }
}

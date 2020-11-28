package com.senla.bulletinboard.mapper;

import com.senla.bulletinboard.dto.PaymentDto;
import com.senla.bulletinboard.entity.PaymentEntity;
import com.senla.bulletinboard.mapper.interfaces.PaymentDtoEntityMapper;
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
        destination.setPayment(source.getPayment());
        return destination;
    }

    @Override
    public PaymentDto destinationToSource(final PaymentEntity destination) {
        final PaymentDto source = new PaymentDto();
        source.setPayedAt(destination.getPayedAt());
        source.setUserId(destination.getUserId());
        source.setPayment(destination.getPayment());
        return source;
    }
}

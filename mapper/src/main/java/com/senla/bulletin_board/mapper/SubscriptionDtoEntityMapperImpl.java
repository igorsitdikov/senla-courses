package com.senla.bulletin_board.mapper;

import com.senla.bulletin_board.dto.SubscriptionDto;
import com.senla.bulletin_board.entity.SubscriptionEntity;
import com.senla.bulletin_board.mapper.interfaces.SubscriptionDtoEntityMapper;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class SubscriptionDtoEntityMapperImpl implements SubscriptionDtoEntityMapper {

    @Override
    public SubscriptionEntity sourceToDestination(final SubscriptionDto source) {
        SubscriptionEntity destination = new SubscriptionEntity();
        destination.setTariffId(source.getTariffId());
        destination.setUserId(source.getUserId());
        return destination;
    }

    @Override
    public SubscriptionDto destinationToSource(final SubscriptionEntity destination) {
        SubscriptionDto source = new SubscriptionDto();
        source.setTariffId(destination.getTariffId());
        source.setUserId(destination.getUserId());
        source.setSubscribedAt(destination.getSubscribedAt());
        return source;
    }
}

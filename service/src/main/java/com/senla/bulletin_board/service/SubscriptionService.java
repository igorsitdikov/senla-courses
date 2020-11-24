package com.senla.bulletin_board.service;

import com.senla.bulletin_board.dto.SubscriptionDto;
import com.senla.bulletin_board.entity.SubscriptionEntity;
import com.senla.bulletin_board.entity.TariffEntity;
import com.senla.bulletin_board.entity.UserEntity;
import com.senla.bulletin_board.enumerated.AutoSubscribeStatus;
import com.senla.bulletin_board.enumerated.PremiumStatus;
import com.senla.bulletin_board.exception.InsufficientFundsException;
import com.senla.bulletin_board.mapper.interfaces.DtoEntityMapper;
import com.senla.bulletin_board.repository.SubscriptionRepository;
import com.senla.bulletin_board.repository.TariffRepository;
import com.senla.bulletin_board.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Log4j2
@Service
public class SubscriptionService extends AbstractService<SubscriptionDto, SubscriptionEntity, SubscriptionRepository> {

    private final UserRepository userRepository;
    private final TariffRepository tariffRepository;

    public SubscriptionService(final DtoEntityMapper<SubscriptionDto, SubscriptionEntity> dtoEntityMapper,
                               final SubscriptionRepository repository,
                               final UserRepository userRepository,
                               final TariffRepository tariffRepository) {
        super(dtoEntityMapper, repository);
        this.userRepository = userRepository;
        this.tariffRepository = tariffRepository;
    }

    @Transactional
    public void addPremium(final SubscriptionDto subscriptionDto) throws InsufficientFundsException {
        final UserEntity userEntity = userRepository.getOne(subscriptionDto.getUserId());
        final TariffEntity tariffEntity = tariffRepository.getOne(subscriptionDto.getTariffId());
        addPremium(userEntity, tariffEntity);
    }

    @Transactional
    public void addPremium(final UserEntity userEntity, final TariffEntity tariffEntity)
        throws InsufficientFundsException {
        if (userEntity.getBalance().compareTo(tariffEntity.getPrice()) < 0) {
            final String message = String.format("There are not sufficient funds! Balance: %.2f, Withdrawal: %.2f",
                                                 userEntity.getBalance(),
                                                 tariffEntity.getPrice());
            userEntity.setAutoSubscribe(AutoSubscribeStatus.DISABLE);
            userRepository.save(userEntity);
            log.error(message);
            throw new InsufficientFundsException(message);
        }
        final BigDecimal updatedBalance = userEntity.getBalance().subtract(tariffEntity.getPrice());
        userEntity.setBalance(updatedBalance);
        userEntity.setPremium(PremiumStatus.ACTIVE);
        userRepository.save(userEntity);

        SubscriptionDto subscriptionDto = new SubscriptionDto();
        subscriptionDto.setUserId(userEntity.getId());
        subscriptionDto.setTariffId(tariffEntity.getId());
        super.post(subscriptionDto);
    }
}

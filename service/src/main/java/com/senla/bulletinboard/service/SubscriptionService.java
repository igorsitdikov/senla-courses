package com.senla.bulletinboard.service;

import com.senla.bulletinboard.dto.SubscriptionDto;
import com.senla.bulletinboard.entity.AbstractEntity;
import com.senla.bulletinboard.entity.SubscriptionEntity;
import com.senla.bulletinboard.entity.TariffEntity;
import com.senla.bulletinboard.entity.UserEntity;
import com.senla.bulletinboard.enumerated.AutoSubscribeStatus;
import com.senla.bulletinboard.enumerated.PremiumStatus;
import com.senla.bulletinboard.exception.InsufficientFundsException;
import com.senla.bulletinboard.mapper.interfaces.DtoEntityMapper;
import com.senla.bulletinboard.repository.SubscriptionRepository;
import com.senla.bulletinboard.repository.TariffRepository;
import com.senla.bulletinboard.repository.UserRepository;
import com.senla.bulletinboard.utils.DateTimeUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @PreAuthorize("authentication.principal.id == #subscriptionDto.getUserId()")
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

        SubscriptionEntity subscriptionEntity = new SubscriptionEntity();
        subscriptionEntity.setUserId(userEntity.getId());
        subscriptionEntity.setTariffId(tariffEntity.getId());

        if (userEntity.getPremium() == PremiumStatus.ACTIVE) {
            final String message = String.format("User with id %d has already premium status.", userEntity.getId());
            log.info(message);
            final Optional<SubscriptionEntity> lastSubscription =
                repository.findTopByUserIdOrderBySubscribedAt(userEntity.getId());

            if (lastSubscription.isPresent()) {
                final LocalDateTime subscribedAt = subscriptionEntity.getSubscribedAt();
                final Integer tariffTerm = subscriptionEntity.getTariff().getTerm();
                final LocalDateTime futureSubscription = DateTimeUtils.addDays(subscribedAt, tariffTerm);
                subscriptionEntity.setSubscribedAt(futureSubscription);
            }
        }
        super.save(subscriptionEntity);
    }
}

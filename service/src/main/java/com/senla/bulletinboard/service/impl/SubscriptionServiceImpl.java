package com.senla.bulletinboard.service.impl;

import com.senla.bulletinboard.dto.SubscriptionDto;
import com.senla.bulletinboard.entity.SubscriptionEntity;
import com.senla.bulletinboard.entity.TariffEntity;
import com.senla.bulletinboard.entity.UserEntity;
import com.senla.bulletinboard.enumerated.AutoSubscribeStatus;
import com.senla.bulletinboard.enumerated.PremiumStatus;
import com.senla.bulletinboard.exception.EntityNotFoundException;
import com.senla.bulletinboard.exception.InsufficientFundsException;
import com.senla.bulletinboard.exception.NoSuchUserException;
import com.senla.bulletinboard.mapper.interfaces.DtoEntityMapper;
import com.senla.bulletinboard.repository.SubscriptionRepository;
import com.senla.bulletinboard.repository.TariffRepository;
import com.senla.bulletinboard.repository.UserRepository;
import com.senla.bulletinboard.service.SubscriptionService;
import com.senla.bulletinboard.utils.DateTimeUtils;
import com.senla.bulletinboard.utils.Translator;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Log4j2
@Service
public class SubscriptionServiceImpl
    extends AbstractService<SubscriptionDto, SubscriptionEntity, SubscriptionRepository> implements
                                                                                         SubscriptionService {

    private final UserRepository userRepository;
    private final TariffRepository tariffRepository;

    public SubscriptionServiceImpl(final DtoEntityMapper<SubscriptionDto, SubscriptionEntity> dtoEntityMapper,
                                   final SubscriptionRepository repository,
                                   final UserRepository userRepository,
                                   final TariffRepository tariffRepository,
                                   final Translator translator) {
        super(dtoEntityMapper, repository, translator);
        this.userRepository = userRepository;
        this.tariffRepository = tariffRepository;
    }

    @Override
    @Transactional
    @PreAuthorize("authentication.principal.id == #subscriptionDto.getUserId()")
    public void addPremium(final SubscriptionDto subscriptionDto)
        throws InsufficientFundsException, EntityNotFoundException, NoSuchUserException {
        final UserEntity userEntity = userRepository.findById(subscriptionDto.getUserId())
            .orElseThrow(
                () -> new NoSuchUserException(translator.toLocale("no-such-user-id", subscriptionDto.getUserId())));
        final TariffEntity tariffEntity = tariffRepository.findById(subscriptionDto.getTariffId())
            .orElseThrow(
                () -> new EntityNotFoundException(
                    translator.toLocale("tariff-not-exists", subscriptionDto.getTariffId())));
        addPremium(userEntity, tariffEntity);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void addPremium(final UserEntity userEntity, final TariffEntity tariffEntity)
        throws InsufficientFundsException {
        if (userEntity.getBalance().compareTo(tariffEntity.getPrice()) < 0) {
            final String message = translator.toLocale("no-funds", userEntity.getBalance(), tariffEntity.getPrice());
            userEntity.setAutoSubscribe(AutoSubscribeStatus.DISABLE);
            userRepository.save(userEntity);
            throw new InsufficientFundsException(message);
        }

        SubscriptionEntity subscriptionEntity = new SubscriptionEntity();

        if (userEntity.getPremium().equals(PremiumStatus.ACTIVE)) {
            final String message = translator.toLocale("user-has-premium", userEntity.getId());
            log.info(message);
            final Optional<SubscriptionEntity> lastSubscription =
                repository.findTopByUserIdOrderBySubscribedAt(userEntity.getId());

            if (lastSubscription.isPresent()) {
                final LocalDateTime subscribedAt = lastSubscription.get().getSubscribedAt();
                final Integer tariffTerm = lastSubscription.get().getTariff().getTerm();
                final LocalDateTime futureSubscription = DateTimeUtils.addDays(subscribedAt, tariffTerm);
                subscriptionEntity.setSubscribedAt(futureSubscription);
            }
        }

        subscriptionEntity.setUserId(userEntity.getId());
        subscriptionEntity.setTariffId(tariffEntity.getId());

        final BigDecimal updatedBalance = userEntity
            .getBalance()
            .subtract(tariffEntity.getPrice());
        userEntity.setBalance(updatedBalance);
        userEntity.setPremium(PremiumStatus.ACTIVE);
        userRepository.save(userEntity);
        super.save(subscriptionEntity);
    }
}

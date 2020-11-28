package com.senla.bulletin_board.service.schedule;

import com.senla.bulletin_board.entity.AbstractEntity;
import com.senla.bulletin_board.entity.SubscriptionEntity;
import com.senla.bulletin_board.entity.TariffEntity;
import com.senla.bulletin_board.entity.UserEntity;
import com.senla.bulletin_board.enumerated.AutoSubscribeStatus;
import com.senla.bulletin_board.enumerated.PremiumStatus;
import com.senla.bulletin_board.exception.InsufficientFundsException;
import com.senla.bulletin_board.repository.SubscriptionRepository;
import com.senla.bulletin_board.repository.UserRepository;
import com.senla.bulletin_board.service.SubscriptionService;
import lombok.Data;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.senla.bulletin_board.utils.DateTimeUtils.isExpired;

@Data
@Service
public class ScheduledPremiumCheckerService {

    private final UserRepository userRepository;
    private final SubscriptionService subscriptionService;
    private final SubscriptionRepository subscriptionRepository;

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void performDailyChecker() throws InsufficientFundsException {
        List<UserEntity> usersWithActivePremium = userRepository.findAllByPremium(PremiumStatus.ACTIVE);
        List<Long> idUsersWithActivePremium = entitiesToIds(usersWithActivePremium);

        List<SubscriptionEntity> lastSubscriptionsForUsersWithActivePremium =
            subscriptionRepository.findLastSubscriptionsForUserIds(idUsersWithActivePremium);

        List<Long> userIds = findUsersWithExpiredSubscriptionPeriod(lastSubscriptionsForUsersWithActivePremium);

        userRepository.updatePremiumStatusForIds(PremiumStatus.DISABLE, userIds);

        List<UserEntity> usersForAutoSubscribing =
            userRepository.findAllByPremiumAndAutoSubscribe(PremiumStatus.DISABLE, AutoSubscribeStatus.ACTIVE);

        List<Long> idUsersForAutoSubscribing = entitiesToIds(usersForAutoSubscribing);

        List<SubscriptionEntity> lastSubscriptionsForUserIds =
            subscriptionRepository.findLastSubscriptionsForUserIds(idUsersForAutoSubscribing);

        addPremiumForUsersWithPositiveBalance(lastSubscriptionsForUserIds);
    }

    private List<Long> entitiesToIds(final List<? extends AbstractEntity> entities) {
        return entities
            .stream()
            .map(AbstractEntity::getId)
            .collect(Collectors.toList());
    }

    private List<Long> findUsersWithExpiredSubscriptionPeriod(final List<SubscriptionEntity> subscriptions) {
        List<Long> userIds = new ArrayList<>();
        for (SubscriptionEntity subscription : subscriptions) {
            final LocalDateTime subscribedAt = subscription.getSubscribedAt();
            final Integer tariffTerm = subscription.getTariff().getTerm();
            if (isExpired(subscribedAt, tariffTerm)) {
                userIds.add(subscription.getUserId());
            }
        }
        return userIds;
    }

    private void addPremiumForUsersWithPositiveBalance(final List<SubscriptionEntity> subscriptions)
        throws InsufficientFundsException {
        for (SubscriptionEntity subscriptionEntity : subscriptions) {
            final UserEntity userEntity = subscriptionEntity.getUser();
            final TariffEntity tariffEntity = subscriptionEntity.getTariff();
            subscriptionService.addPremium(userEntity, tariffEntity);
        }
    }
}

package com.senla.bulletin_board.service.schedule;

import com.senla.bulletin_board.entity.AbstractEntity;
import com.senla.bulletin_board.entity.SubscriptionEntity;
import com.senla.bulletin_board.entity.UserEntity;
import com.senla.bulletin_board.enumerated.PremiumStatus;
import com.senla.bulletin_board.repository.SubscriptionRepository;
import com.senla.bulletin_board.repository.UserRepository;
import lombok.Data;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Service
public class ScheduledPremiumCheckerService {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void performDailyChecker() {
        final LocalDate now = LocalDate.now();
        List<UserEntity> userEntities = userRepository.findAllByPremium(PremiumStatus.ACTIVE);
        List<Long> ids = userEntities.stream().map(AbstractEntity::getId).collect(Collectors.toList());
        List<SubscriptionEntity> subscriptionEntities = subscriptionRepository.findLastSubscriptionsForUserIds(ids);
        List<Long> userIds = new ArrayList<>();
        for (SubscriptionEntity subscriptionEntity : subscriptionEntities) {
            final LocalDateTime subscribedAt = subscriptionEntity.getSubscribedAt();
            final LocalDate expire = subscribedAt.plusDays(subscriptionEntity.getTariff().getTerm()).toLocalDate();

            if (expire.equals(now)) {
                userIds.add(subscriptionEntity.getUserId());
            }
        }
        if (userIds.size() > 0) {
            userRepository.updatePremiumStatusForIds(PremiumStatus.ACTIVE, userIds);
        }
    }
}

package com.senla.bulletin_board.controller;

import com.senla.bulletin_board.dto.SubscriptionDto;
import com.senla.bulletin_board.entity.AbstractEntity;
import com.senla.bulletin_board.entity.SubscriptionEntity;
import com.senla.bulletin_board.entity.UserEntity;
import com.senla.bulletin_board.enumerated.PremiumStatus;
import com.senla.bulletin_board.exception.InsufficientFundsException;
import com.senla.bulletin_board.repository.SubscriptionRepository;
import com.senla.bulletin_board.repository.UserRepository;
import com.senla.bulletin_board.service.SubscriptionService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@RestController
@RequestMapping(value = "/subscriptions")
public class SubscriptionController {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;

    private final SubscriptionService subscriptionService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void subscribe(@RequestBody final SubscriptionDto subscriptionDto) throws InsufficientFundsException {
        subscriptionService.addPremium(subscriptionDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public void test() {
        final LocalDate now = LocalDate.now().plusDays(1);
        List<UserEntity> userEntities = userRepository.findAll();
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
        userRepository.updatePremiumStatusForIds(PremiumStatus.ACTIVE, userIds);
    }
}

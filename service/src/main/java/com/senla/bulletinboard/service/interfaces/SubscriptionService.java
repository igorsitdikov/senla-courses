package com.senla.bulletinboard.service.interfaces;

import com.senla.bulletinboard.dto.SubscriptionDto;
import com.senla.bulletinboard.entity.SubscriptionEntity;
import com.senla.bulletinboard.entity.TariffEntity;
import com.senla.bulletinboard.entity.UserEntity;
import com.senla.bulletinboard.exception.EntityNotFoundException;
import com.senla.bulletinboard.exception.InsufficientFundsException;
import com.senla.bulletinboard.exception.NoSuchUserException;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.transaction.Transactional;

public interface SubscriptionService extends CommonService<SubscriptionDto, SubscriptionEntity> {

    @Transactional
    @PreAuthorize("authentication.principal.id == #subscriptionDto.getUserId()")
    void addPremium(SubscriptionDto subscriptionDto)
        throws InsufficientFundsException, EntityNotFoundException, NoSuchUserException;

    @Transactional
    void addPremium(UserEntity userEntity, TariffEntity tariffEntity)
        throws InsufficientFundsException;
}

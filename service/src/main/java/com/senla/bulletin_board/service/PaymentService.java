package com.senla.bulletin_board.service;

import com.senla.bulletin_board.dto.PaymentDto;
import com.senla.bulletin_board.entity.PaymentEntity;
import com.senla.bulletin_board.enumerated.PremiumStatus;
import com.senla.bulletin_board.mapper.interfaces.PaymentDtoEntityMapper;
import com.senla.bulletin_board.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentService extends AbstractService<PaymentDto, PaymentEntity, PaymentRepository> {

    private PaymentService(final PaymentDtoEntityMapper dtoEntityMapper,
                           final PaymentRepository repository) {
        super(dtoEntityMapper, repository);
    }

    public void addPremium(final Long userId, final Long tariffId) {
        final PaymentDto paymentDto = new PaymentDto();
        paymentDto.setTariffId(tariffId);
        paymentDto.setUserId(userId);
        paymentDto.setPremiumStatus(PremiumStatus.ACTIVE);
//        paymentDto.setBalance(BigDecimal.ZERO);
        post(paymentDto);
    }

}

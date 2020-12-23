package com.senla.bulletinboard.service;

import com.senla.bulletinboard.dto.IdDto;
import com.senla.bulletinboard.dto.PaymentDto;
import com.senla.bulletinboard.entity.PaymentEntity;
import com.senla.bulletinboard.exception.NoSuchUserException;

import javax.transaction.Transactional;
import java.util.List;

public interface PaymentService extends CommonService<PaymentDto, PaymentEntity> {

    @Transactional
    IdDto createPayment(PaymentDto paymentDto) throws NoSuchUserException;

    List<PaymentDto> showAllPaymentsByUserId(Long id);
}

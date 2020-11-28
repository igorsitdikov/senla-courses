package com.senla.bulletinboard.service;

import com.senla.bulletinboard.dto.PaymentDto;
import com.senla.bulletinboard.entity.PaymentEntity;
import com.senla.bulletinboard.entity.UserEntity;
import com.senla.bulletinboard.exception.NoSuchUserException;
import com.senla.bulletinboard.mapper.interfaces.PaymentDtoEntityMapper;
import com.senla.bulletinboard.repository.PaymentRepository;
import com.senla.bulletinboard.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class PaymentService extends AbstractService<PaymentDto, PaymentEntity, PaymentRepository> {

    private final UserRepository userRepository;
    private final PaymentDtoEntityMapper paymentDtoEntityMapper;

    public PaymentService(final PaymentDtoEntityMapper dtoEntityMapper,
                           final PaymentRepository repository,
                           final UserRepository userRepository) {
        super(dtoEntityMapper, repository);
        paymentDtoEntityMapper = dtoEntityMapper;
        this.userRepository = userRepository;
    }

    @Transactional
    public void createPayment(final PaymentDto paymentDto) throws NoSuchUserException {
        if (!userRepository.existsById(paymentDto.getUserId())) {
            final String message = String.format("User with such id %d does not exist", paymentDto.getUserId());
            log.error(message);
            throw new NoSuchUserException(message);
        }
        final UserEntity userEntity = userRepository.getOne(paymentDto.getUserId());
        final BigDecimal updatedBalance = userEntity.getBalance().add(paymentDto.getPayment());
        userEntity.setBalance(updatedBalance);
        userRepository.save(userEntity);
        super.post(paymentDto);
    }

    public List<PaymentDto> showAllPaymentsByUserId(final Long id) {
        return repository.findAllByUserId(id)
            .stream()
            .map(paymentDtoEntityMapper::destinationToSource)
            .collect(Collectors.toList());
    }

}
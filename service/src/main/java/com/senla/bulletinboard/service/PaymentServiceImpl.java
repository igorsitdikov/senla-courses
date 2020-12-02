package com.senla.bulletinboard.service;

import com.senla.bulletinboard.dto.IdDto;
import com.senla.bulletinboard.dto.PaymentDto;
import com.senla.bulletinboard.entity.PaymentEntity;
import com.senla.bulletinboard.entity.UserEntity;
import com.senla.bulletinboard.exception.NoSuchUserException;
import com.senla.bulletinboard.mapper.interfaces.PaymentDtoEntityMapper;
import com.senla.bulletinboard.repository.PaymentRepository;
import com.senla.bulletinboard.repository.UserRepository;
import com.senla.bulletinboard.service.interfaces.PaymentService;
import com.senla.bulletinboard.utils.Translator;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
public class PaymentServiceImpl extends AbstractService<PaymentDto, PaymentEntity, PaymentRepository> implements
                                                                                                      PaymentService {

    private final UserRepository userRepository;
    private final PaymentDtoEntityMapper paymentDtoEntityMapper;

    public PaymentServiceImpl(final PaymentDtoEntityMapper dtoEntityMapper,
                              final PaymentRepository repository,
                              final UserRepository userRepository) {
        super(dtoEntityMapper, repository);
        paymentDtoEntityMapper = dtoEntityMapper;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public IdDto createPayment(final PaymentDto paymentDto) throws NoSuchUserException {
        final Optional<UserEntity> userEntity = userRepository.findById(paymentDto.getUserId());
        if (!userEntity.isPresent()) {
            final String message = Translator.toLocale("no-such-user-id", paymentDto.getUserId());
            log.error(message);
            throw new NoSuchUserException(message);
        }
        final UserEntity user = userEntity.get();
        final BigDecimal updatedBalance = user.getBalance().add(paymentDto.getPayment());
        user.setBalance(updatedBalance);
        userRepository.save(user);
        return super.post(paymentDto);
    }

    @Override
    public List<PaymentDto> showAllPaymentsByUserId(final Long id) {
        return repository.findAllByUserId(id)
            .stream()
            .map(paymentDtoEntityMapper::destinationToSource)
            .collect(Collectors.toList());
    }

}

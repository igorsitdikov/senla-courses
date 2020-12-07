package com.senla.bulletinboard.controller;

import com.senla.bulletinboard.dto.ApiErrorDto;
import com.senla.bulletinboard.dto.IdDto;
import com.senla.bulletinboard.dto.PaymentDto;
import com.senla.bulletinboard.dto.UserDto;
import com.senla.bulletinboard.entity.PaymentEntity;
import com.senla.bulletinboard.entity.UserEntity;
import com.senla.bulletinboard.enumerated.ExceptionType;
import com.senla.bulletinboard.mapper.interfaces.PaymentDtoEntityMapper;
import com.senla.bulletinboard.mapper.interfaces.UserDtoEntityMapper;
import com.senla.bulletinboard.mock.UserMock;
import com.senla.bulletinboard.repository.PaymentRepository;
import com.senla.bulletinboard.utils.Translator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PaymentControllerTest extends AbstractControllerTest {

    @SpyBean
    private UserDtoEntityMapper userDtoEntityMapper;
    @MockBean
    private PaymentRepository paymentRepository;
    @SpyBean
    private PaymentDtoEntityMapper paymentDtoEntityMapper;

    @Test
    @WithMockUser(roles = "USER")
    public void createPaymentTest() throws Exception {
        final Long id = 1L;
        final Long userId = 1L;
        final PaymentDto paymentDto = new PaymentDto();
        paymentDto.setPayment(BigDecimal.valueOf(13));
        paymentDto.setUserId(userId);
        final PaymentEntity paymentEntity = paymentDtoEntityMapper.sourceToDestination(paymentDto);
        paymentEntity.setId(id);
        final UserDto userDtoById = UserMock.getUserDtoById(userId);
        final UserEntity userEntity = userDtoEntityMapper.sourceToDestination(userDtoById);
        userEntity.setBalance(BigDecimal.ZERO);

        willReturn(Optional.of(userEntity)).given(userRepository).findById(userId);
        willReturn(paymentEntity).given(paymentRepository).save(any(PaymentEntity.class));

        mockMvc.perform(post("/api/payments")
                            .contextPath(CONTEXT_PATH)
                            .content(objectMapper.writeValueAsString(paymentDto))
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(content().json(objectMapper.writeValueAsString(new IdDto(id))));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void createPaymentTest_NoSuchUserException() throws Exception {
        final Long id = 1L;
        final Long userId = 1L;
        final PaymentDto paymentDto = new PaymentDto();
        paymentDto.setPayment(BigDecimal.valueOf(13));
        paymentDto.setUserId(userId);
        final PaymentEntity paymentEntity = paymentDtoEntityMapper.sourceToDestination(paymentDto);
        paymentEntity.setId(id);
        final UserDto userDtoById = UserMock.getUserDtoById(userId);
        final UserEntity userEntity = userDtoEntityMapper.sourceToDestination(userDtoById);
        userEntity.setBalance(BigDecimal.ZERO);

        willReturn(Optional.empty()).given(userRepository).findById(userId);
        willReturn(paymentEntity).given(paymentRepository).save(any(PaymentEntity.class));

        String message = Translator.toLocale("no-such-user-id", userId);
        final ApiErrorDto expectedError =
            expectedErrorCreator(HttpStatus.NOT_FOUND, ExceptionType.BUSINESS_LOGIC, message);

        final String response = mockMvc.perform(post("/api/payments")
                                                    .contextPath(CONTEXT_PATH)
                                                    .content(objectMapper.writeValueAsString(paymentDto))
                                                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andReturn().getResponse().getContentAsString();
        assertErrorResponse(expectedError, response);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void showPaymentsTest() throws Exception {
        final Long id = 1L;
        final Long userId = 1L;
        final PaymentDto paymentDto = new PaymentDto();
        paymentDto.setPayment(BigDecimal.valueOf(13));
        paymentDto.setUserId(userId);

        final List<PaymentEntity> payments = new ArrayList<>();

        final PaymentEntity paymentEntity = paymentDtoEntityMapper.sourceToDestination(paymentDto);
        paymentEntity.setId(id);
        payments.add(paymentEntity);
        List<PaymentDto> paymentDtos = payments
            .stream()
            .map(paymentDtoEntityMapper::destinationToSource)
            .collect(Collectors.toList());

        willReturn(payments).given(paymentRepository).findAllByUserId(userId);

        mockMvc.perform(get("/api/payments/" + id)
                            .contextPath(CONTEXT_PATH)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(paymentDtos)));
    }
}

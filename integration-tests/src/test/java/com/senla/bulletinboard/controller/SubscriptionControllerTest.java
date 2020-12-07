package com.senla.bulletinboard.controller;

import com.senla.bulletinboard.dto.ApiErrorDto;
import com.senla.bulletinboard.dto.SubscriptionDto;
import com.senla.bulletinboard.entity.SubscriptionEntity;
import com.senla.bulletinboard.entity.TariffEntity;
import com.senla.bulletinboard.entity.UserEntity;
import com.senla.bulletinboard.enumerated.ExceptionType;
import com.senla.bulletinboard.mock.TariffMock;
import com.senla.bulletinboard.mock.UserMock;
import com.senla.bulletinboard.repository.SubscriptionRepository;
import com.senla.bulletinboard.repository.TariffRepository;
import com.senla.bulletinboard.utils.Translator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SubscriptionControllerTest extends AbstractControllerTest {

    @MockBean
    private SubscriptionRepository subscriptionRepository;
    @MockBean
    private TariffRepository tariffRepository;

    @Test
    public void subscribeTest() throws Exception {
        final Long tariffId = 2L;
        final String token = signInAsUser(USER_IVAN);
        final SubscriptionDto subscriptionDto = new SubscriptionDto();
        subscriptionDto.setUserId(USER_IVAN);
        subscriptionDto.setTariffId(tariffId);
        final UserEntity userEntity = UserMock.getEntityById(USER_IVAN);
        final TariffEntity tariffEntity = TariffMock.getEntityById(tariffId);

        willReturn(Optional.of(userEntity)).given(userRepository).findById(USER_IVAN);
        willReturn(Optional.of(tariffEntity)).given(tariffRepository).findById(tariffId);

        mockMvc.perform(post("/api/subscriptions")
                            .contextPath(CONTEXT_PATH)
                            .header("Authorization", token)
                            .content(objectMapper.writeValueAsString(subscriptionDto))
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());

        verify(subscriptionRepository, times(1)).save(any(SubscriptionEntity.class));
    }

    @Test
    public void subscribeTest_UserNotFoundException() throws Exception {
        final Long tariffId = 2L;
        final String token = signInAsUser(USER_IVAN);
        final SubscriptionDto subscriptionDto = new SubscriptionDto();
        subscriptionDto.setUserId(USER_IVAN);
        subscriptionDto.setTariffId(tariffId);

        willReturn(Optional.empty()).given(userRepository).findById(USER_IVAN);

        final String response = mockMvc.perform(post("/api/subscriptions")
                                                    .contextPath(CONTEXT_PATH)
                                                    .header("Authorization", token)
                                                    .content(objectMapper.writeValueAsString(subscriptionDto))
                                                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andReturn().getResponse().getContentAsString();

        final String message = Translator.toLocale("no-such-user-id", USER_IVAN);
        final ApiErrorDto expectedError = expectedErrorCreator(
            HttpStatus.NOT_FOUND,
            ExceptionType.BUSINESS_LOGIC,
            message);

        assertErrorResponse(expectedError, response);

        verify(subscriptionRepository, times(0)).save(any(SubscriptionEntity.class));
    }

    @Test
    public void subscribeTest_TariffNotFoundException() throws Exception {
        final String token = signInAsUser(USER_IVAN);
        final Long tariffId = 2L;
        final SubscriptionDto subscriptionDto = new SubscriptionDto();
        subscriptionDto.setUserId(USER_IVAN);
        subscriptionDto.setTariffId(tariffId);
        final UserEntity userEntity = UserMock.getEntityById(USER_IVAN);

        willReturn(Optional.of(userEntity)).given(userRepository).findById(USER_IVAN);
        willReturn(Optional.empty()).given(tariffRepository).findById(tariffId);
        final String response = mockMvc.perform(post("/api/subscriptions")
                                                    .contextPath(CONTEXT_PATH)
                                                    .header("Authorization", token)
                                                    .content(objectMapper.writeValueAsString(subscriptionDto))
                                                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andReturn().getResponse().getContentAsString();

        final String message = Translator.toLocale("tariff-not-exists", tariffId);
        final ApiErrorDto expectedError = expectedErrorCreator(
            HttpStatus.NOT_FOUND,
            ExceptionType.BUSINESS_LOGIC,
            message);

        assertErrorResponse(expectedError, response);

        verify(subscriptionRepository, times(0)).save(any(SubscriptionEntity.class));
    }

    @Test
    public void subscribeTest_InsufficientFundsException() throws Exception {
        final Long tariffId = 2L;
        final String token = signInAsUser(USER_PETR);
        final SubscriptionDto subscriptionDto = new SubscriptionDto();
        subscriptionDto.setUserId(USER_PETR);
        subscriptionDto.setTariffId(tariffId);
        final UserEntity userEntity = UserMock.getEntityById(USER_PETR);
        final TariffEntity tariffEntity = TariffMock.getEntityById(tariffId);
        final BigDecimal price = tariffEntity.getPrice();

        willReturn(Optional.of(userEntity)).given(userRepository).findById(USER_PETR);
        willReturn(Optional.of(tariffEntity)).given(tariffRepository).findById(tariffId);

        final String response = mockMvc.perform(post("/api/subscriptions")
                                                    .contextPath(CONTEXT_PATH)
                                                    .header("Authorization", token)
                                                    .content(objectMapper.writeValueAsString(subscriptionDto))
                                                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isPaymentRequired())
            .andReturn().getResponse().getContentAsString();

        final String message = Translator.toLocale("no-funds", userEntity.getBalance(), price);
        final ApiErrorDto expectedError = expectedErrorCreator(
            HttpStatus.PAYMENT_REQUIRED,
            ExceptionType.BUSINESS_LOGIC,
            message);

        assertErrorResponse(expectedError, response);

        verify(subscriptionRepository, times(0)).save(any(SubscriptionEntity.class));
    }
}

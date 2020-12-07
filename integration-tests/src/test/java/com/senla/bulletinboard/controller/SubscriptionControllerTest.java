package com.senla.bulletinboard.controller;

import com.senla.bulletinboard.dto.ApiErrorDto;
import com.senla.bulletinboard.dto.SubscriptionDto;
import com.senla.bulletinboard.dto.UserDto;
import com.senla.bulletinboard.entity.TariffEntity;
import com.senla.bulletinboard.entity.UserEntity;
import com.senla.bulletinboard.enumerated.ExceptionType;
import com.senla.bulletinboard.mapper.interfaces.UserDtoEntityMapper;
import com.senla.bulletinboard.mock.UserMock;
import com.senla.bulletinboard.repository.SubscriptionRepository;
import com.senla.bulletinboard.repository.TariffRepository;
import com.senla.bulletinboard.utils.Translator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SubscriptionControllerTest extends AbstractControllerTest {

    @SpyBean
    private UserDtoEntityMapper userDtoEntityMapper;
    @MockBean
    private SubscriptionRepository subscriptionRepository;
    @MockBean
    private TariffRepository tariffRepository;

    @Test
    public void subscribeTest() throws Exception {
        final Long userId = 1L;
        String token = signInAsUser(userId);
        final Long tariffId = 2L;
        final SubscriptionDto subscriptionDto = new SubscriptionDto();
        subscriptionDto.setUserId(userId);
        subscriptionDto.setTariffId(tariffId);
        final UserDto userDtoById = UserMock.getUserDtoById(userId);
        final UserEntity userEntity = userDtoEntityMapper.sourceToDestination(userDtoById);
        userEntity.setBalance(BigDecimal.valueOf(13));
        final TariffEntity tariffEntity = new TariffEntity();
        tariffEntity.setPrice(BigDecimal.valueOf(12.5));
        tariffEntity.setTerm(2);
        tariffEntity.setDescription("12.5$ за 2 дня");

        willReturn(Optional.of(userEntity)).given(userRepository).findById(userId);
        willReturn(Optional.of(tariffEntity)).given(tariffRepository).findById(tariffId);

        mockMvc.perform(post("/api/subscriptions")
                            .contextPath(CONTEXT_PATH)
                            .header("Authorization", token)
                            .content(objectMapper.writeValueAsString(subscriptionDto))
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
    }

    @Test
    public void subscribeTest_UserNotFoundException() throws Exception {
        final Long userId = 1L;
        String token = signInAsUser(userId);
        final Long tariffId = 2L;
        final SubscriptionDto subscriptionDto = new SubscriptionDto();
        subscriptionDto.setUserId(userId);
        subscriptionDto.setTariffId(tariffId);
        final UserDto userDtoById = UserMock.getUserDtoById(userId);
        final UserEntity userEntity = userDtoEntityMapper.sourceToDestination(userDtoById);
        userEntity.setBalance(BigDecimal.valueOf(13));
        final TariffEntity tariffEntity = new TariffEntity();
        tariffEntity.setPrice(BigDecimal.valueOf(12.5));
        tariffEntity.setTerm(2);
        tariffEntity.setDescription("12.5$ за 2 дня");

        willReturn(Optional.empty()).given(userRepository).findById(userId);
        willReturn(Optional.of(tariffEntity)).given(tariffRepository).findById(tariffId);

        String message = Translator.toLocale("no-such-user-id", userId);
        final ApiErrorDto expectedError =
            expectedErrorCreator(HttpStatus.NOT_FOUND, ExceptionType.BUSINESS_LOGIC, message);

        final String response = mockMvc.perform(post("/api/subscriptions")
                                                    .contextPath(CONTEXT_PATH)
                                                    .header("Authorization", token)
                                                    .content(objectMapper.writeValueAsString(subscriptionDto))
                                                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andReturn().getResponse().getContentAsString();
        assertErrorResponse(expectedError, response);
    }

    @Test
    public void subscribeTest_TariffNotFoundException() throws Exception {
        final Long userId = 1L;
        String token = signInAsUser(userId);
        final Long tariffId = 2L;
        final SubscriptionDto subscriptionDto = new SubscriptionDto();
        subscriptionDto.setUserId(userId);
        subscriptionDto.setTariffId(tariffId);
        final UserDto userDtoById = UserMock.getUserDtoById(userId);
        final UserEntity userEntity = userDtoEntityMapper.sourceToDestination(userDtoById);
        userEntity.setBalance(BigDecimal.valueOf(13));
        final TariffEntity tariffEntity = new TariffEntity();
        tariffEntity.setPrice(BigDecimal.valueOf(12.5));
        tariffEntity.setTerm(2);
        tariffEntity.setDescription("12.5$ за 2 дня");

        willReturn(Optional.of(userEntity)).given(userRepository).findById(userId);
        willReturn(Optional.empty()).given(tariffRepository).findById(tariffId);

        String message = Translator.toLocale("tariff-not-exists", tariffId);
        final ApiErrorDto expectedError =
            expectedErrorCreator(HttpStatus.NOT_FOUND, ExceptionType.BUSINESS_LOGIC, message);

        final String response = mockMvc.perform(post("/api/subscriptions")
                                                    .contextPath(CONTEXT_PATH)
                                                    .header("Authorization", token)
                                                    .content(objectMapper.writeValueAsString(subscriptionDto))
                                                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andReturn().getResponse().getContentAsString();
        assertErrorResponse(expectedError, response);
    }

    @Test
    public void subscribeTest_InsufficientFundsException() throws Exception {
        final Long userId = 1L;
        String token = signInAsUser(userId);
        final Long tariffId = 2L;
        final SubscriptionDto subscriptionDto = new SubscriptionDto();
        subscriptionDto.setUserId(userId);
        subscriptionDto.setTariffId(tariffId);
        final UserDto userDtoById = UserMock.getUserDtoById(userId);
        final UserEntity userEntity = userDtoEntityMapper.sourceToDestination(userDtoById);
        final BigDecimal balance = BigDecimal.valueOf(12);
        userEntity.setBalance(balance);
        final TariffEntity tariffEntity = new TariffEntity();
        final BigDecimal price = BigDecimal.valueOf(12.5);
        tariffEntity.setPrice(price);
        tariffEntity.setTerm(2);
        tariffEntity.setDescription("12.5$ за 2 дня");

        willReturn(Optional.of(userEntity)).given(userRepository).findById(userId);
        willReturn(Optional.of(tariffEntity)).given(tariffRepository).findById(tariffId);

        String message = Translator.toLocale("no-funds", balance, price);
        final ApiErrorDto expectedError =
            expectedErrorCreator(HttpStatus.PAYMENT_REQUIRED, ExceptionType.BUSINESS_LOGIC, message);

        final String response = mockMvc.perform(post("/api/subscriptions")
                                                    .contextPath(CONTEXT_PATH)
                                                    .header("Authorization", token)
                                                    .content(objectMapper.writeValueAsString(subscriptionDto))
                                                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isPaymentRequired())
            .andReturn().getResponse().getContentAsString();
        assertErrorResponse(expectedError, response);
    }
}

package com.senla.bulletinboard.controller;

import com.senla.bulletinboard.dto.SubscriptionDto;
import com.senla.bulletinboard.dto.UserDto;
import com.senla.bulletinboard.entity.TariffEntity;
import com.senla.bulletinboard.entity.UserEntity;
import com.senla.bulletinboard.mapper.interfaces.BulletinDtoEntityMapper;
import com.senla.bulletinboard.mapper.interfaces.SubscriptionDtoEntityMapper;
import com.senla.bulletinboard.mapper.interfaces.UserDtoEntityMapper;
import com.senla.bulletinboard.mock.UserMock;
import com.senla.bulletinboard.repository.BulletinRepository;
import com.senla.bulletinboard.repository.DialogRepository;
import com.senla.bulletinboard.repository.SubscriptionRepository;
import com.senla.bulletinboard.repository.TariffRepository;
import com.senla.bulletinboard.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SubscriptionControllerTest extends AbstractControllerTest {

    @MockBean
    private UserRepository userRepository;
    @SpyBean
    private UserDtoEntityMapper userDtoEntityMapper;
    @MockBean
    private DialogRepository dialogRepository;
    @MockBean
    private BulletinRepository bulletinRepository;
    @SpyBean
    private BulletinDtoEntityMapper bulletinDtoEntityMapper;
    @MockBean
    private SubscriptionRepository subscriptionRepository;
    @MockBean
    private TariffRepository tariffRepository;
    @SpyBean
    private SubscriptionDtoEntityMapper subscriptionDtoEntityMapper;

    @Test
    @WithMockUser(roles = "USER")
    public void subscribeTest() throws Exception {
        final Long userId = 1L;
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
                            .content(objectMapper.writeValueAsString(subscriptionDto))
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void subscribeTest_UserNotFoundException() throws Exception {
        final Long userId = 1L;
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

        mockMvc.perform(post("/api/subscriptions")
                            .content(objectMapper.writeValueAsString(subscriptionDto))
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content().json("{\"errorMessage\":\"User with such id " + userId + " does not exist\"}"));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void subscribeTest_TariffNotFoundException() throws Exception {
        final Long userId = 1L;
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

        mockMvc.perform(post("/api/subscriptions")
                            .content(objectMapper.writeValueAsString(subscriptionDto))
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content().json("{\"errorMessage\":\"Tariff with such id " + tariffId + " does not exist\"}"));
    }

}

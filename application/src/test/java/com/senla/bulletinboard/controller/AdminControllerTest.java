package com.senla.bulletinboard.controller;

import com.senla.bulletinboard.dto.ApiErrorDto;
import com.senla.bulletinboard.dto.IdDto;
import com.senla.bulletinboard.dto.TariffDto;
import com.senla.bulletinboard.dto.UserDto;
import com.senla.bulletinboard.entity.TariffEntity;
import com.senla.bulletinboard.entity.UserEntity;
import com.senla.bulletinboard.enumerated.ExceptionType;
import com.senla.bulletinboard.enumerated.UserRole;
import com.senla.bulletinboard.mapper.interfaces.TariffDtoEntityMapper;
import com.senla.bulletinboard.mapper.interfaces.UserDtoEntityMapper;
import mock.TariffMock;
import mock.UserMock;
import com.senla.bulletinboard.repository.TariffRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminControllerTest extends AbstractControllerTest {

    @MockBean
    private TariffRepository tariffRepository;
    @SpyBean
    private UserDtoEntityMapper userDtoEntityMapper;
    @SpyBean
    private TariffDtoEntityMapper tariffDtoEntityMapper;

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testShowAllUsers() throws Exception {
        final List<UserDto> userDtos = new UserMock()
            .findAllEntities()
            .stream()
            .map(userDtoEntityMapper::destinationToSource)
            .collect(Collectors.toList());
        final List<UserEntity> userEntities = new UserMock().findAllEntities();
        willReturn(userEntities).given(userRepository).findAll();
        final String content = objectMapper.writeValueAsString(userDtos);
        mockMvc.perform(get("/api/admin/users")
                            .contextPath(CONTEXT_PATH)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()).andExpect(content().json(content)).andReturn()
            .getResponse().getContentAsString();
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void testChangeUserRole() throws Exception {
        final Long userId = 1L;
        final UserEntity userEntity = new UserMock().getEntityById(userId);
        final UserEntity savedUser = new UserMock().getEntityById(userId);
        final UserDto userDto = userDtoEntityMapper.destinationToSource(savedUser);

        willReturn(Optional.of(userEntity)).given(userRepository).findById(userId);
        willReturn(savedUser).given(userRepository).saveAndFlush(any(UserEntity.class));
        final String content = objectMapper.writeValueAsString(userDto);
        mockMvc.perform(patch("/api/admin/users/" + userId)
                            .param("role", UserRole.ADMIN.name())
                            .contextPath(CONTEXT_PATH)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(content)).andReturn();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testChangeUserRole_NoSuchUser() throws Exception {
        final Long userId = 1L;
        willReturn(Optional.empty()).given(userRepository).findById(userId);
        mockMvc.perform(patch("/api/admin/users/" + userId)
                            .param("role", UserRole.ADMIN.name())
                            .contextPath(CONTEXT_PATH)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
        verify(userRepository, never()).saveAndFlush(any(UserEntity.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testAddTariff() throws Exception {
        final Long id = 3L;
        final TariffDto tariffDto = new TariffMock().getById(id);
        final TariffEntity tariffEntity = tariffDtoEntityMapper.sourceToDestination(new TariffMock().getById(id));

        willReturn(tariffEntity).given(tariffRepository).save(any(TariffEntity.class));
        final IdDto idDto = new IdDto(id);
        mockMvc.perform(post("/api/admin/tariffs/")
                            .contextPath(CONTEXT_PATH)
                            .content(objectMapper.writeValueAsString(tariffDto))
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(content().json(objectMapper.writeValueAsString(idDto)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateTariff() throws Exception {
        final Long id = 3L;
        final TariffDto tariffDto = new TariffMock().getById(id);
        tariffDto.setPrice(BigDecimal.valueOf(3.4));
        final TariffEntity tariffEntity = tariffDtoEntityMapper.sourceToDestination(new TariffMock().getById(id));
        tariffEntity.setPrice(BigDecimal.valueOf(3.4));

        willReturn(true).given(tariffRepository).existsById(id);
        willReturn(tariffEntity).given(tariffRepository).save(any(TariffEntity.class));

        mockMvc.perform(put("/api/admin/tariffs/" + id)
                            .contextPath(CONTEXT_PATH)
                            .content(objectMapper.writeValueAsString(tariffDto))
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(tariffDto)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateTariff_NotExistsException() throws Exception {
        final Long id = 3L;
        final TariffDto tariffDto = new TariffMock().getById(id);
        tariffDto.setPrice(BigDecimal.valueOf(3.4));
        final TariffEntity tariffEntity = tariffDtoEntityMapper.sourceToDestination(new TariffMock().getById(id));
        tariffEntity.setPrice(BigDecimal.valueOf(3.4));

        willReturn(tariffEntity).given(tariffRepository).save(any(TariffEntity.class));

        final String response = mockMvc.perform(put("/api/admin/tariffs/" + id)
                                                    .contextPath(CONTEXT_PATH)
                                                    .content(objectMapper.writeValueAsString(tariffDto))
                                                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andReturn().getResponse().getContentAsString();

        String message = translator.toLocale("tariff-not-exists", id);
        final ApiErrorDto expectedError = expectedErrorCreator(
            HttpStatus.NOT_FOUND,
            ExceptionType.BUSINESS_LOGIC,
            message);

        assertErrorResponse(expectedError, response);
    }
}

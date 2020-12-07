package com.senla.bulletinboard.controller;

import com.senla.bulletinboard.dto.ApiErrorDto;
import com.senla.bulletinboard.dto.IdDto;
import com.senla.bulletinboard.dto.TariffDto;
import com.senla.bulletinboard.entity.TariffEntity;
import com.senla.bulletinboard.enumerated.ExceptionType;
import com.senla.bulletinboard.mapper.interfaces.TariffDtoEntityMapper;
import com.senla.bulletinboard.mock.TariffMock;
import com.senla.bulletinboard.repository.TariffRepository;
import com.senla.bulletinboard.utils.Translator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TariffControllerTest extends AbstractControllerTest {

    @MockBean
    private TariffRepository tariffRepository;
    @SpyBean
    private TariffDtoEntityMapper tariffDtoEntityMapper;

    @Test
    public void testShowTariffs() throws Exception {
        final List<TariffDto> expected = TariffMock.getAll();
        final List<TariffEntity> entities = expected
            .stream()
            .map(tariffDtoEntityMapper::sourceToDestination)
            .collect(Collectors.toList());

        willReturn(entities).given(tariffRepository).findAll();

        mockMvc.perform(get("/api/tariffs/")
                            .contextPath(CONTEXT_PATH)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testAddTariff() throws Exception {
        final Long id = 3L;
        final TariffDto tariffDto = TariffMock.getById(id);
        final TariffEntity tariffEntity = tariffDtoEntityMapper.sourceToDestination(TariffMock.getById(id));

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
        final TariffDto tariffDto = TariffMock.getById(id);
        tariffDto.setPrice(BigDecimal.valueOf(3.4));
        final TariffEntity tariffEntity = tariffDtoEntityMapper.sourceToDestination(TariffMock.getById(id));
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
        final TariffDto tariffDto = TariffMock.getById(id);
        tariffDto.setPrice(BigDecimal.valueOf(3.4));
        final TariffEntity tariffEntity = tariffDtoEntityMapper.sourceToDestination(TariffMock.getById(id));
        tariffEntity.setPrice(BigDecimal.valueOf(3.4));

        willReturn(tariffEntity).given(tariffRepository).save(any(TariffEntity.class));

        final String response = mockMvc.perform(put("/api/admin/tariffs/" + id)
                                                    .contextPath(CONTEXT_PATH)
                                                    .content(objectMapper.writeValueAsString(tariffDto))
                                                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andReturn().getResponse().getContentAsString();

        String message = Translator.toLocale("tariff-not-exists", id);
        final ApiErrorDto expectedError = expectedErrorCreator(
            HttpStatus.NOT_FOUND,
            ExceptionType.BUSINESS_LOGIC,
            message);

        assertErrorResponse(expectedError, response);
    }
}

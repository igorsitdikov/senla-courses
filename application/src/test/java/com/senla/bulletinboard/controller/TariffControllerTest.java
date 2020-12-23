package com.senla.bulletinboard.controller;

import com.senla.bulletinboard.dto.TariffDto;
import com.senla.bulletinboard.entity.TariffEntity;
import com.senla.bulletinboard.mapper.interfaces.TariffDtoEntityMapper;
import mock.TariffMock;
import com.senla.bulletinboard.repository.TariffRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TariffControllerTest extends AbstractControllerTest {

    @MockBean
    private TariffRepository tariffRepository;
    @SpyBean
    private TariffDtoEntityMapper tariffDtoEntityMapper;

    @Test
    public void testShowTariffs() throws Exception {
        final List<TariffDto> expected = new TariffMock().getAll();
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
}

package com.senla.bulletin_board.controller;

import com.senla.bulletin_board.dto.IdDto;
import com.senla.bulletin_board.dto.TariffDto;
import com.senla.bulletin_board.entity.TariffEntity;
import com.senla.bulletin_board.mapper.interfaces.DtoEntityMapper;
import com.senla.bulletin_board.mapper.interfaces.TariffDtoEntityMapper;
import com.senla.bulletin_board.mock.TariffMock;
import com.senla.bulletin_board.repository.CommonRepository;
import com.senla.bulletin_board.repository.TariffRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void testAddTariff() throws Exception {
        final long id = 3L;
        final TariffDto tariffDto = TariffMock.getById(id);
        final TariffEntity tariffEntity = tariffDtoEntityMapper.sourceToDestination(TariffMock.getById(id));

        willReturn(tariffEntity).given(tariffRepository).save(any(TariffEntity.class));
        final IdDto idDto = new IdDto(id);
        mockMvc.perform(post("/api/tariffs/")
                            .content(objectMapper.writeValueAsString(tariffDto))
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(content().json(objectMapper.writeValueAsString(idDto)));
    }

    @Test
    public void testSubscribe() throws Exception {
        final long id = 3L;
        mockMvc.perform(get("/api/tariffs/" + id)
                            .param("user_id", "4")
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

}

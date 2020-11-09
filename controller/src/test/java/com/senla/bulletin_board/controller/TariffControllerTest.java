package com.senla.bulletin_board.controller;

import com.senla.bulletin_board.dto.IdDto;
import com.senla.bulletin_board.dto.TariffDto;
import com.senla.bulletin_board.dto.UserDto;
import mock.TariffMock;
import mock.UserMock;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TariffControllerTest extends AbstractControllerTest {

    @Test
    public void testShowTariffs() throws Exception {
        final List<TariffDto> expected = TariffMock.getAll();
        mockMvc.perform(get("/api/tariffs/")
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void testAddTariff() throws Exception {
        final long id = 3L;
        final TariffDto tariffDto = TariffMock.getById(id);
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

package com.senla.bulletin_board.controller;

import com.senla.bulletin_board.dto.DialogRequestDto;
import com.senla.bulletin_board.dto.IdDto;
import com.senla.bulletin_board.dto.MessageDto;
import com.senla.bulletin_board.mock.DialogMock;
import com.senla.bulletin_board.mock.MessageMock;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DialogControllerTest extends AbstractControllerTest {

    @Test
    public void showMessagesTest() throws Exception {
        final long id = 3L;
        final List<MessageDto> expected = MessageMock.getAll();

        mockMvc.perform(get("/api/dialogs/" + id + "/messages")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));

    }

    @Test
    public void createDialogTest() throws Exception {
        final DialogRequestDto bulletinDto = DialogMock.getRequestById(1L);
        final String request = objectMapper.writeValueAsString(bulletinDto);
        final String response = objectMapper.writeValueAsString(new IdDto(1L));

        mockMvc.perform(post("/api/dialogs/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        )
                .andExpect(status().isCreated())
                .andExpect(content().json(response));
    }

    @Test
    public void deleteDialogTest() throws Exception {
        final long id = 3L;

        mockMvc.perform(delete("/api/dialogs/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}

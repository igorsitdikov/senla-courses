package com.senla.bulletin_board.controller;

import com.senla.bulletin_board.dto.BulletinDto;
import com.senla.bulletin_board.dto.IdDto;
import com.senla.bulletin_board.mock.BulletinMock;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BulletinControllerTest extends AbstractControllerTest {

    @Test
    public void showBulletinsTest() throws Exception {
        final List<BulletinDto> expected = BulletinMock.getAll();

        final String response = objectMapper.writeValueAsString(expected);
        mockMvc.perform(get("/api/bulletins/")
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(response));
    }

    @Test
    public void createBulletinTest() throws Exception {
        final BulletinDto bulletinDto = BulletinMock.getById(4L);
        final String request = objectMapper.writeValueAsString(bulletinDto);
        final String response = objectMapper.writeValueAsString(new IdDto(4L));

        mockMvc.perform(post("/api/bulletins/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request)
                       )
            .andExpect(status().isCreated())
            .andExpect(content().json(response));
    }

    @Test
    public void updateBulletinTest() throws Exception {
        final long id = 4L;
        final BulletinDto bulletinDto = BulletinMock.getById(id);
        final String request = objectMapper.writeValueAsString(bulletinDto);

        mockMvc.perform(put("/api/bulletins/" + id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request)
                       )
            .andExpect(status().isOk());
    }

    @Test
    public void deleteBulletinTest() throws Exception {
        final long id = 4L;

        mockMvc.perform(delete("/api/bulletins/" + id)
                            .contentType(MediaType.APPLICATION_JSON)
                       )
            .andExpect(status().isOk());
    }
}

package com.senla.bulletin_board.controller;

import com.senla.bulletin_board.dto.IdDto;
import com.senla.bulletin_board.dto.StarRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StarControllerTest extends AbstractControllerTest {

    @Test
    public void addStarToSellerTest() throws Exception {
        final long id = 1L;
        final long userId = 3L;
        final long bulletinId = 4L;
        final Integer stars = 5;
        final StarRequestDto starRequestDto = new StarRequestDto(userId, bulletinId, stars);
        final String response = objectMapper.writeValueAsString(new IdDto(id));
        mockMvc.perform(post("/api/stars/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(starRequestDto)))
            .andExpect(status().isCreated())
            .andExpect(content().json(response));
    }
}

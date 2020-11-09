package com.senla.bulletin_board.controller;

import com.senla.bulletin_board.dto.CommentRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CommentControllerTest extends AbstractControllerTest {

    @Test
    public void createCommentTest() throws Exception {
        final long userId = 3L;
        final long bulletinId = 4L;
        final CommentRequestDto commentRequestDto = new CommentRequestDto(userId, bulletinId, "Отличный картофель");
        mockMvc.perform(post("/api/comments/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(commentRequestDto)))
            .andExpect(status().isCreated());
    }
}

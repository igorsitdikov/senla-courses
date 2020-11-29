package com.senla.bulletinboard.controller;

import com.senla.bulletinboard.dto.CommentDto;
import com.senla.bulletinboard.dto.IdDto;
import com.senla.bulletinboard.entity.BulletinEntity;
import com.senla.bulletinboard.entity.CommentEntity;
import com.senla.bulletinboard.enumerated.BulletinStatus;
import com.senla.bulletinboard.mapper.interfaces.BulletinDtoEntityMapper;
import com.senla.bulletinboard.mapper.interfaces.CommentDtoEntityMapper;
import com.senla.bulletinboard.mock.BulletinDetailsMock;
import com.senla.bulletinboard.mock.CommentMock;
import com.senla.bulletinboard.repository.BulletinRepository;
import com.senla.bulletinboard.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CommentControllerTest extends AbstractControllerTest {

    @MockBean
    private BulletinRepository bulletinRepository;
    @MockBean
    private CommentRepository commentRepository;
    @SpyBean
    private BulletinDtoEntityMapper bulletinDtoEntityMapper;
    @SpyBean
    private CommentDtoEntityMapper commentDtoEntityMapper;

    @Test
    public void createCommentTest() throws Exception {
        final long id = 1L;
        final long bulletinId = 4L;
        final IdDto response = new IdDto(1L);
        final CommentDto commentDto = CommentMock.getById(id);
        final CommentEntity commentEntity = commentDtoEntityMapper.sourceToDestination(commentDto);
        final BulletinEntity bulletinEntity =
            bulletinDtoEntityMapper.sourceToDestination(BulletinDetailsMock.getById(bulletinId));
        willReturn(Optional.of(bulletinEntity)).given(bulletinRepository).findById(bulletinId);
        willReturn(commentEntity).given(commentRepository).save(any(CommentEntity.class));

        final String content = objectMapper.writeValueAsString(commentDto);
        mockMvc.perform(post("/api/comments/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(content))
            .andExpect(status().isCreated())
            .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    public void createCommentTest_BulletinIsClosedException() throws Exception {
        final long id = 1L;
        final long bulletinId = 4L;
        final CommentDto commentDto = CommentMock.getById(id);
        final CommentEntity commentEntity = commentDtoEntityMapper.sourceToDestination(commentDto);
        final BulletinEntity bulletinEntity =
            bulletinDtoEntityMapper.sourceToDestination(BulletinDetailsMock.getById(bulletinId));
        bulletinEntity.setStatus(BulletinStatus.CLOSE);
        willReturn(Optional.of(bulletinEntity)).given(bulletinRepository).findById(bulletinId);
        willReturn(commentEntity).given(commentRepository).save(any(CommentEntity.class));

        final String content = objectMapper.writeValueAsString(commentDto);
        mockMvc.perform(post("/api/comments/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(content))
            .andExpect(status().isBadRequest())
            .andExpect(content().json("{\"errorMessage\":\"Bulletin with id " + bulletinId + " is closed\"}"));
    }

    @Test
    public void createCommentTest_BulletinNotFoundException() throws Exception {
        final long id = 1L;
        final long bulletinId = 4L;
        final CommentDto commentDto = CommentMock.getById(id);
        final CommentEntity commentEntity = commentDtoEntityMapper.sourceToDestination(commentDto);

        willReturn(Optional.empty()).given(bulletinRepository).findById(bulletinId);
        willReturn(commentEntity).given(commentRepository).save(any(CommentEntity.class));

        final String content = objectMapper.writeValueAsString(commentDto);
        mockMvc.perform(post("/api/comments/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(content))
            .andExpect(status().isNotFound())
            .andExpect(content().json("{\"errorMessage\":\"Bulletin with id " + bulletinId + " does not exist\"}"));
    }
}

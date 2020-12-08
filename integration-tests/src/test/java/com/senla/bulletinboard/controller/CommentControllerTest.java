package com.senla.bulletinboard.controller;

import com.senla.bulletinboard.dto.ApiErrorDto;
import com.senla.bulletinboard.dto.CommentDto;
import com.senla.bulletinboard.dto.IdDto;
import com.senla.bulletinboard.entity.BulletinEntity;
import com.senla.bulletinboard.entity.CommentEntity;
import com.senla.bulletinboard.enumerated.ExceptionType;
import com.senla.bulletinboard.mapper.interfaces.CommentDtoEntityMapper;
import com.senla.bulletinboard.mock.BulletinMock;
import com.senla.bulletinboard.mock.CommentMock;
import com.senla.bulletinboard.repository.BulletinRepository;
import com.senla.bulletinboard.repository.CommentRepository;
import com.senla.bulletinboard.utils.Translator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
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
    private CommentDtoEntityMapper commentDtoEntityMapper;

    @Test
    public void createCommentTest() throws Exception {
        final Long id = 1L;
        final Long bulletinId = 4L;
        final String token = signInAsUser(USER_PETR);
        final IdDto response = new IdDto(1L);
        final CommentDto commentDto = new CommentMock().getById(id);

        final CommentEntity commentEntity = new CommentMock().getEntityById(id);
        final BulletinEntity bulletinEntity = new BulletinMock().getEntityById(bulletinId);

        willReturn(Optional.of(bulletinEntity)).given(bulletinRepository).findById(bulletinId);
        willReturn(commentEntity).given(commentRepository).save(any(CommentEntity.class));

        final String content = objectMapper.writeValueAsString(commentDto);
        mockMvc.perform(post("/api/comments/")
                            .contextPath(CONTEXT_PATH)
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(content))
            .andExpect(status().isCreated())
            .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    public void createCommentTest_BulletinIsClosedException() throws Exception {
        final Long id = 2L;
        final Long bulletinId = 3L;
        final String token = signInAsUser(USER_ANTON);
        final CommentDto commentDto = new CommentMock().getById(id);
        final CommentEntity commentEntity = new CommentMock().getEntityById(id);
        final BulletinEntity bulletinEntity = new BulletinMock().getEntityById(bulletinId);

        willReturn(Optional.of(bulletinEntity)).given(bulletinRepository).findById(bulletinId);
        willReturn(commentEntity).given(commentRepository).save(any(CommentEntity.class));

        final String content = objectMapper.writeValueAsString(commentDto);

        final String response = mockMvc.perform(post("/api/comments/")
                                                    .contextPath(CONTEXT_PATH)
                                                    .header("Authorization", token)
                                                    .contentType(MediaType.APPLICATION_JSON)
                                                    .content(content))
            .andExpect(status().isBadRequest())
            .andReturn().getResponse().getContentAsString();

        final String message = Translator.toLocale("bulletin-closed", bulletinId);
        final ApiErrorDto expectedError = expectedErrorCreator(
            HttpStatus.BAD_REQUEST,
            ExceptionType.BUSINESS_LOGIC,
            message);

        assertErrorResponse(expectedError, response);
    }

    @Test
    public void createCommentTest_BulletinNotFoundException() throws Exception {
        final Long id = 1L;
        final Long bulletinId = 4L;
        final String token = signInAsUser(USER_PETR);
        final CommentDto commentDto = new CommentMock().getById(id);
        final CommentEntity commentEntity = commentDtoEntityMapper.sourceToDestination(commentDto);

        willReturn(Optional.empty()).given(bulletinRepository).findById(bulletinId);
        willReturn(commentEntity).given(commentRepository).save(any(CommentEntity.class));

        final String content = objectMapper.writeValueAsString(commentDto);

        final String response = mockMvc.perform(post("/api/comments/")
                                                    .contextPath(CONTEXT_PATH)
                                                    .header("Authorization", token)
                                                    .contentType(MediaType.APPLICATION_JSON)
                                                    .content(content))
            .andExpect(status().isNotFound())
            .andReturn().getResponse().getContentAsString();

        final String message = Translator.toLocale("bulletin-not-exists", bulletinId);
        final ApiErrorDto expectedError = expectedErrorCreator(
            HttpStatus.NOT_FOUND,
            ExceptionType.BUSINESS_LOGIC,
            message);

        assertErrorResponse(expectedError, response);
    }
}

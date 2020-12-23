package com.senla.bulletinboard.controller;

import com.senla.bulletinboard.dto.ApiErrorDto;
import com.senla.bulletinboard.dto.DialogDto;
import com.senla.bulletinboard.dto.MessageDto;
import com.senla.bulletinboard.entity.BulletinEntity;
import com.senla.bulletinboard.entity.DialogEntity;
import com.senla.bulletinboard.entity.MessageEntity;
import com.senla.bulletinboard.enumerated.ExceptionType;
import com.senla.bulletinboard.mapper.interfaces.DialogDtoEntityMapper;
import com.senla.bulletinboard.mapper.interfaces.MessageDtoEntityMapper;
import mock.BulletinMock;
import mock.DialogMock;
import mock.MessageMock;
import com.senla.bulletinboard.repository.BulletinRepository;
import com.senla.bulletinboard.repository.DialogRepository;
import com.senla.bulletinboard.repository.MessageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MessageControllerTest extends AbstractControllerTest {

    @MockBean
    private DialogRepository dialogRepository;
    @SpyBean
    private DialogDtoEntityMapper dialogDtoEntityMapper;
    @MockBean
    private BulletinRepository bulletinRepository;
    @MockBean
    private MessageRepository messageRepository;
    @SpyBean
    private MessageDtoEntityMapper messageDtoEntityMapper;

    @Test
    public void createMessageTest_WrongMessageRecipientException() throws Exception {
        final String token = signInAsUser(USER_IVAN);
        final MessageDto messageDto = new MessageMock().getById(2L);

        final String response = mockMvc.perform(post("/api/messages")
                                                    .contextPath(CONTEXT_PATH)
                                                    .content(objectMapper.writeValueAsString(messageDto))
                                                    .header("Authorization", token)
                                                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isForbidden())
            .andReturn().getResponse().getContentAsString();

        final String message = translator.toLocale("send-message-forbidden");
        final ApiErrorDto expectedError = expectedErrorCreator(
            HttpStatus.FORBIDDEN,
            ExceptionType.BUSINESS_LOGIC,
            message);

        assertErrorResponse(expectedError, response);
    }

    @Test
    public void createMessageTest_WrongRecipientException() throws Exception {
        final String token = signInAsUser(USER_IVAN);
        final Long recipientId = 3L;
        final Long dialogId = 3L;
        final MessageDto messageDto = new MessageMock().getById(3L);

        final BulletinEntity bulletinEntity = new BulletinMock().getEntityById(1L);

        final DialogDto dialogDto = new DialogMock().getById(1L);
        final DialogEntity dialogEntity = dialogDtoEntityMapper.sourceToDestination(dialogDto);
        dialogEntity.setBulletinId(bulletinEntity.getId());
        dialogEntity.setBulletin(bulletinEntity);

        willReturn(Optional.of(dialogEntity)).given(dialogRepository).findById(dialogId);
        willReturn(Optional.of(bulletinEntity)).given(bulletinRepository).findById(bulletinEntity.getId());

        final String response = mockMvc.perform(post("/api/messages")
                                                    .contextPath(CONTEXT_PATH)
                                                    .content(objectMapper.writeValueAsString(messageDto))
                                                    .header("Authorization", token)
                                                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andReturn().getResponse().getContentAsString();

        final String message = translator.toLocale("wrong-recipient", recipientId);
        final ApiErrorDto expectedError = expectedErrorCreator(
            HttpStatus.BAD_REQUEST,
            ExceptionType.BUSINESS_LOGIC,
            message);

        assertErrorResponse(expectedError, response);
    }

    @Test
    public void createMessageTest_WrongSenderException() throws Exception {
        final String token = signInAsUser(USER_IVAN);
        final Long dialogId = 3L;
        final MessageDto messageDto = new MessageMock().getById(3L);

        final BulletinEntity bulletinEntity = new BulletinMock().getEntityById(1L);
        final DialogEntity dialogEntity = new DialogMock().getEntityById(2L);
        dialogEntity.setBulletin(bulletinEntity);

        willReturn(Optional.of(dialogEntity)).given(dialogRepository).findById(dialogId);
        willReturn(Optional.of(bulletinEntity)).given(bulletinRepository).findById(bulletinEntity.getId());

        final String response = mockMvc.perform(post("/api/messages")
                                                    .contextPath(CONTEXT_PATH)
                                                    .content(objectMapper.writeValueAsString(messageDto))
                                                    .header("Authorization", token)
                                                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andReturn().getResponse().getContentAsString();

        final String message = translator.toLocale("wrong-sender", USER_IVAN);
        final ApiErrorDto expectedError = expectedErrorCreator(
            HttpStatus.BAD_REQUEST,
            ExceptionType.BUSINESS_LOGIC,
            message);

        assertErrorResponse(expectedError, response);
    }

    @Test
    public void createMessageTest() throws Exception {
        final String token = signInAsUser(USER_IVAN);
        final Long recipientId = 2L;
        final Long dialogId = 3L;
        final MessageDto messageDto = new MessageMock().getById(4L);

        final BulletinEntity bulletinEntity = new BulletinMock().getEntityById(4L);

        final DialogDto dialogDto = new DialogMock().getById(1L);
        final DialogEntity dialogEntity = dialogDtoEntityMapper.sourceToDestination(dialogDto);
        dialogEntity.setCustomerId(recipientId);
        dialogEntity.setBulletinId(bulletinEntity.getId());
        dialogEntity.setBulletin(bulletinEntity);
        final MessageEntity messageEntity = messageDtoEntityMapper.sourceToDestination(messageDto);

        willReturn(Optional.of(dialogEntity)).given(dialogRepository).findById(dialogId);
        willReturn(Optional.of(bulletinEntity)).given(bulletinRepository).findById(bulletinEntity.getId());
        willReturn(messageEntity).given(messageRepository).save(any(MessageEntity.class));

        mockMvc.perform(post("/api/messages")
                            .contextPath(CONTEXT_PATH)
                            .content(objectMapper.writeValueAsString(messageDto))
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
    }

    @Test
    public void createMessageTest_DialogNotFoundException() throws Exception {
        final String token = signInAsUser(USER_IVAN);
        final Long dialogId = 3L;
        final MessageDto messageDto = new MessageMock().getById(4L);

        final BulletinEntity bulletinEntity = new BulletinMock().getEntityById(1L);

        willReturn(Optional.empty()).given(dialogRepository).findById(dialogId);
        willReturn(Optional.of(bulletinEntity)).given(bulletinRepository).findById(bulletinEntity.getId());

        final String response = mockMvc.perform(post("/api/messages")
                                                    .contextPath(CONTEXT_PATH)
                                                    .content(objectMapper.writeValueAsString(messageDto))
                                                    .header("Authorization", token)
                                                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andReturn().getResponse().getContentAsString();

        final String message = translator.toLocale("dialog-not-exists", dialogId);
        final ApiErrorDto expectedError = expectedErrorCreator(
            HttpStatus.NOT_FOUND,
            ExceptionType.BUSINESS_LOGIC,
            message);

        assertErrorResponse(expectedError, response);
    }

    @Test
    public void createMessageTest_BulletinNotFoundException() throws Exception {
        final String token = signInAsUser(USER_IVAN);
        final Long dialogId = 1L;
        final Long bulletinId = 4L;
        final MessageDto messageDto = new MessageMock().getById(1L);

        final DialogEntity dialogEntity = new DialogMock().getEntityById(dialogId);

        willReturn(Optional.of(dialogEntity)).given(dialogRepository).findById(dialogId);
        willReturn(Optional.empty()).given(bulletinRepository).findById(bulletinId);

        final String response = mockMvc.perform(post("/api/messages")
                                                    .contextPath(CONTEXT_PATH)
                                                    .content(objectMapper.writeValueAsString(messageDto))
                                                    .header("Authorization", token)
                                                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andReturn().getResponse().getContentAsString();

        final String message = translator.toLocale("bulletin-not-exists", bulletinId);
        final ApiErrorDto expectedError = expectedErrorCreator(
            HttpStatus.NOT_FOUND,
            ExceptionType.BUSINESS_LOGIC,
            message);

        assertErrorResponse(expectedError, response);
    }
}

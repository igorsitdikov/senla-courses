package com.senla.bulletinboard.controller;

import com.senla.bulletinboard.dto.ApiErrorDto;
import com.senla.bulletinboard.dto.BulletinDto;
import com.senla.bulletinboard.dto.DialogDto;
import com.senla.bulletinboard.dto.MessageDto;
import com.senla.bulletinboard.entity.BulletinEntity;
import com.senla.bulletinboard.entity.DialogEntity;
import com.senla.bulletinboard.entity.MessageEntity;
import com.senla.bulletinboard.entity.UserEntity;
import com.senla.bulletinboard.enumerated.ExceptionType;
import com.senla.bulletinboard.mapper.interfaces.BulletinDtoEntityMapper;
import com.senla.bulletinboard.mapper.interfaces.DialogDtoEntityMapper;
import com.senla.bulletinboard.mapper.interfaces.MessageDtoEntityMapper;
import com.senla.bulletinboard.mock.BulletinDetailsMock;
import com.senla.bulletinboard.mock.DialogMock;
import com.senla.bulletinboard.repository.BulletinRepository;
import com.senla.bulletinboard.repository.DialogRepository;
import com.senla.bulletinboard.repository.MessageRepository;
import com.senla.bulletinboard.utils.Translator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

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
    @SpyBean
    private BulletinDtoEntityMapper bulletinDtoEntityMapper;
    @MockBean
    private MessageRepository messageRepository;
    @SpyBean
    private MessageDtoEntityMapper messageDtoEntityMapper;

    @Test
    @WithMockUser(roles = "USER")
    public void createMessageTest_WrongMessageRecipientException() throws Exception {
        final Long senderId = 1L;
        final Long recipientId = 1L;
        final Long dialogId = 3L;
        final MessageDto messageDto = new MessageDto();
        messageDto.setDialogId(dialogId);
        messageDto.setRecipientId(recipientId);
        messageDto.setSenderId(senderId);

        String message = Translator.toLocale("send-message-forbidden");
        final ApiErrorDto expectedError =
            expectedErrorCreator(HttpStatus.FORBIDDEN, ExceptionType.BUSINESS_LOGIC, message);

        final String response = mockMvc.perform(post("/api/messages")
                                                    .contextPath(CONTEXT_PATH)
                                                    .content(objectMapper.writeValueAsString(messageDto))
                                                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isForbidden())
            .andReturn().getResponse().getContentAsString();
        assertErrorResponse(expectedError, response);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void createMessageTest_WrongRecipientException() throws Exception {
        final Long senderId = 1L;
        final Long recipientId = 2L;
        final Long dialogId = 3L;
        final MessageDto messageDto = new MessageDto();
        messageDto.setDialogId(dialogId);
        messageDto.setRecipientId(recipientId);
        messageDto.setSenderId(senderId);

        final BulletinDto expected = BulletinDetailsMock.getById(1L);
        final BulletinEntity bulletinEntity = bulletinDtoEntityMapper.sourceToDestination(expected);

        final DialogDto dialogDto = DialogMock.getById(1L);
        final DialogEntity dialogEntity = dialogDtoEntityMapper.sourceToDestination(dialogDto);
        dialogEntity.setBulletinId(bulletinEntity.getId());
        dialogEntity.setBulletin(bulletinEntity);

        willReturn(Optional.of(dialogEntity)).given(dialogRepository).findById(dialogId);
        willReturn(Optional.of(bulletinEntity)).given(bulletinRepository).findById(bulletinEntity.getId());

        String message = Translator.toLocale("wrong-recipient", recipientId);
        final ApiErrorDto expectedError =
            expectedErrorCreator(HttpStatus.BAD_REQUEST, ExceptionType.BUSINESS_LOGIC, message);

        final String response = mockMvc.perform(post("/api/messages")
                                                    .contextPath(CONTEXT_PATH)
                                                    .content(objectMapper.writeValueAsString(messageDto))
                                                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andReturn().getResponse().getContentAsString();
        assertErrorResponse(expectedError, response);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void createMessageTest_WrongSenderException() throws Exception {
        final Long senderId = 5L;
        final Long recipientId = 2L;
        final Long dialogId = 3L;
        final MessageDto messageDto = new MessageDto();
        messageDto.setDialogId(dialogId);
        messageDto.setRecipientId(recipientId);
        messageDto.setSenderId(senderId);

        final BulletinDto expected = BulletinDetailsMock.getById(1L);
        final BulletinEntity bulletinEntity = bulletinDtoEntityMapper.sourceToDestination(expected);

        final DialogDto dialogDto = DialogMock.getById(1L);
        final DialogEntity dialogEntity = dialogDtoEntityMapper.sourceToDestination(dialogDto);
        dialogEntity.setCustomerId(recipientId);
        dialogEntity.setBulletinId(bulletinEntity.getId());
        dialogEntity.setBulletin(bulletinEntity);

        willReturn(Optional.of(dialogEntity)).given(dialogRepository).findById(dialogId);
        willReturn(Optional.of(bulletinEntity)).given(bulletinRepository).findById(bulletinEntity.getId());

        String message = Translator.toLocale("wrong-sender", senderId);
        final ApiErrorDto expectedError =
            expectedErrorCreator(HttpStatus.BAD_REQUEST, ExceptionType.BUSINESS_LOGIC, message);

        final String response = mockMvc.perform(post("/api/messages")
                                                    .contextPath(CONTEXT_PATH)
                                                    .content(objectMapper.writeValueAsString(messageDto))
                                                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andReturn().getResponse().getContentAsString();
        assertErrorResponse(expectedError, response);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void createMessageTest() throws Exception {
        final Long senderId = 5L;
        final Long recipientId = 2L;
        final Long dialogId = 3L;
        final MessageDto messageDto = new MessageDto();
        messageDto.setDialogId(dialogId);
        messageDto.setRecipientId(recipientId);
        messageDto.setSenderId(senderId);

        final BulletinDto expected = BulletinDetailsMock.getById(1L);
        final BulletinEntity bulletinEntity = bulletinDtoEntityMapper.sourceToDestination(expected);
        final UserEntity seller = bulletinEntity.getSeller();
        seller.setId(senderId);
        bulletinEntity.setSeller(seller);

        final DialogDto dialogDto = DialogMock.getById(1L);
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
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void createMessageTest_DialogNotFoundException() throws Exception {
        final Long senderId = 5L;
        final Long recipientId = 2L;
        final Long dialogId = 3L;
        final MessageDto messageDto = new MessageDto();
        messageDto.setDialogId(dialogId);
        messageDto.setRecipientId(recipientId);
        messageDto.setSenderId(senderId);

        final BulletinDto expected = BulletinDetailsMock.getById(1L);
        final BulletinEntity bulletinEntity = bulletinDtoEntityMapper.sourceToDestination(expected);
        final UserEntity seller = bulletinEntity.getSeller();
        seller.setId(senderId);
        bulletinEntity.setSeller(seller);

        final DialogDto dialogDto = DialogMock.getById(1L);
        final DialogEntity dialogEntity = dialogDtoEntityMapper.sourceToDestination(dialogDto);
        dialogEntity.setCustomerId(recipientId);
        dialogEntity.setBulletinId(bulletinEntity.getId());
        dialogEntity.setBulletin(bulletinEntity);
        final MessageEntity messageEntity = messageDtoEntityMapper.sourceToDestination(messageDto);

        willReturn(Optional.empty()).given(dialogRepository).findById(dialogId);
        willReturn(Optional.of(bulletinEntity)).given(bulletinRepository).findById(bulletinEntity.getId());
        willReturn(messageEntity).given(messageRepository).save(any(MessageEntity.class));

        String message = Translator.toLocale("dialog-not-exists", dialogId);
        final ApiErrorDto expectedError =
            expectedErrorCreator(HttpStatus.NOT_FOUND, ExceptionType.BUSINESS_LOGIC, message);

        final String response = mockMvc.perform(post("/api/messages")
                                                    .contextPath(CONTEXT_PATH)
                                                    .content(objectMapper.writeValueAsString(messageDto))
                                                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andReturn().getResponse().getContentAsString();

        assertErrorResponse(expectedError, response);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void createMessageTest_BulletinNotFoundException() throws Exception {
        final Long senderId = 5L;
        final Long recipientId = 2L;
        final Long dialogId = 3L;
        final MessageDto messageDto = new MessageDto();
        messageDto.setDialogId(dialogId);
        messageDto.setRecipientId(recipientId);
        messageDto.setSenderId(senderId);

        final BulletinDto expected = BulletinDetailsMock.getById(1L);
        final BulletinEntity bulletinEntity = bulletinDtoEntityMapper.sourceToDestination(expected);
        final UserEntity seller = bulletinEntity.getSeller();
        seller.setId(senderId);
        bulletinEntity.setSeller(seller);

        final DialogDto dialogDto = DialogMock.getById(1L);
        final DialogEntity dialogEntity = dialogDtoEntityMapper.sourceToDestination(dialogDto);
        dialogEntity.setCustomerId(recipientId);
        dialogEntity.setBulletinId(bulletinEntity.getId());
        dialogEntity.setBulletin(bulletinEntity);
        final MessageEntity messageEntity = messageDtoEntityMapper.sourceToDestination(messageDto);

        willReturn(Optional.of(dialogEntity)).given(dialogRepository).findById(dialogId);
        willReturn(Optional.empty()).given(bulletinRepository).findById(bulletinEntity.getId());
        willReturn(messageEntity).given(messageRepository).save(any(MessageEntity.class));

        String message = Translator.toLocale("bulletin-not-exists", bulletinEntity.getId());
        final ApiErrorDto expectedError =
            expectedErrorCreator(HttpStatus.NOT_FOUND, ExceptionType.BUSINESS_LOGIC, message);

        final String response = mockMvc.perform(post("/api/messages")
                                                    .contextPath(CONTEXT_PATH)
                                                    .content(objectMapper.writeValueAsString(messageDto))
                                                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andReturn().getResponse().getContentAsString();
        assertErrorResponse(expectedError, response);
    }
}

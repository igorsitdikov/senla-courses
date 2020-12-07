package com.senla.bulletinboard.controller;

import com.senla.bulletinboard.dto.ApiErrorDto;
import com.senla.bulletinboard.dto.DialogDto;
import com.senla.bulletinboard.dto.IdDto;
import com.senla.bulletinboard.dto.MessageDto;
import com.senla.bulletinboard.entity.BulletinEntity;
import com.senla.bulletinboard.entity.DialogEntity;
import com.senla.bulletinboard.entity.MessageEntity;
import com.senla.bulletinboard.enumerated.ExceptionType;
import com.senla.bulletinboard.mapper.interfaces.DialogDtoEntityMapper;
import com.senla.bulletinboard.mapper.interfaces.MessageDtoEntityMapper;
import com.senla.bulletinboard.mock.BulletinMock;
import com.senla.bulletinboard.mock.DialogMock;
import com.senla.bulletinboard.mock.MessageMock;
import com.senla.bulletinboard.repository.BulletinRepository;
import com.senla.bulletinboard.repository.DialogRepository;
import com.senla.bulletinboard.repository.MessageRepository;
import com.senla.bulletinboard.utils.Translator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DialogControllerTest extends AbstractControllerTest {

    @MockBean
    private DialogRepository dialogRepository;
    @MockBean
    private MessageRepository messageRepository;
    @SpyBean
    private MessageDtoEntityMapper messageDtoEntityMapper;
    @SpyBean
    private DialogDtoEntityMapper dialogDtoEntityMapper;
    @MockBean
    private BulletinRepository bulletinRepository;

    @Test
    public void showMessagesTest() throws Exception {
        final long id = 3L;
        final List<MessageDto> expected = MessageMock.getAll();
        final List<MessageEntity> entities = expected
            .stream()
            .map(messageDtoEntityMapper::sourceToDestination)
            .collect(Collectors.toList());

        final String token = signInAsUser(USER_ANTON);

        willReturn(BigInteger.ONE).given(dialogRepository).findByIdAndOwnerId(id, USER_ANTON);
        willReturn(entities).given(messageRepository).findAllByDialogId(id);

        mockMvc.perform(get("/api/dialogs/" + id + "/messages")
                            .contextPath(CONTEXT_PATH)
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void createDialogTest() throws Exception {
        final long bulletinId = 4L;
        final String token = signInAsUser(USER_ANTON);
        final DialogDto dialogDto = DialogMock.getById(1L);
        dialogDto.setCustomerId(USER_ANTON);
        final DialogEntity dialogEntity = dialogDtoEntityMapper.sourceToDestination(dialogDto);
        final String request = objectMapper.writeValueAsString(dialogDto);
        final String response = objectMapper.writeValueAsString(new IdDto(1L));

        final BulletinEntity bulletinEntity = BulletinMock.getEntityById(bulletinId);

        willReturn(Optional.of(bulletinEntity)).given(bulletinRepository).findById(bulletinId);
        willReturn(false).given(dialogRepository)
            .existsByBulletin_IdAndCustomerId(dialogDto.getBulletinId(), dialogDto.getCustomerId());
        willReturn(dialogEntity).given(dialogRepository).save(any(DialogEntity.class));

        mockMvc.perform(post("/api/dialogs")
                            .contextPath(CONTEXT_PATH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", token)
                            .content(request))
            .andExpect(status().isCreated())
            .andExpect(content().json(response));
    }

    @Test
    public void createDialogTest_DialogAlreadyExists() throws Exception {
        final long id = 4L;
        final long bulletinId = 4L;
        String token = signInAsUser(id);
        final DialogDto dialogDto = DialogMock.getById(1L);
        dialogDto.setCustomerId(id);
        final String request = objectMapper.writeValueAsString(dialogDto);

        final DialogEntity dialogEntity = dialogDtoEntityMapper.sourceToDestination(dialogDto);
        final BulletinEntity bulletinEntity = BulletinMock.getEntityById(bulletinId);

        willReturn(Optional.of(bulletinEntity)).given(bulletinRepository).findById(bulletinId);
        willReturn(true).given(dialogRepository)
            .existsByBulletin_IdAndCustomerId(dialogDto.getBulletinId(), dialogDto.getCustomerId());
        willReturn(dialogEntity).given(dialogRepository).save(any(DialogEntity.class));

        final String message = Translator.toLocale(
                "dialog-already-exists",
                dialogDto.getBulletinId(),
                dialogDto.getCustomerId());
        final ApiErrorDto expectedError = expectedErrorCreator(
                HttpStatus.CONFLICT,
                ExceptionType.BUSINESS_LOGIC,
                message);

        final String response = mockMvc.perform(post("/api/dialogs")
                                                    .contextPath(CONTEXT_PATH)
                                                    .contentType(MediaType.APPLICATION_JSON)
                                                    .header("Authorization", token)
                                                    .content(request))
            .andExpect(status().isConflict())
            .andReturn().getResponse().getContentAsString();
        assertErrorResponse(expectedError, response);
    }

    @Test
    public void deleteDialogTest() throws Exception {
        final long bulletinId = 4L;
        final long dialogId = 1L;
        final String token = signInAsUser(USER_PETR);
        final DialogDto dialogDto = new DialogDto();
        dialogDto.setCustomerId(USER_PETR);
        dialogDto.setBulletinId(bulletinId);

        final DialogEntity dialogEntity = DialogMock.getEntityById(dialogId);
        final BulletinEntity bulletinEntity = BulletinMock.getEntityById(bulletinId);

        willReturn(Optional.of(dialogEntity)).given(dialogRepository).findById(dialogId);
        willReturn(Optional.of(bulletinEntity)).given(bulletinRepository).findById(bulletinId);

        mockMvc.perform(delete("/api/dialogs/" + dialogId)
                            .contextPath(CONTEXT_PATH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", token))
            .andExpect(status().isOk());

        verify(dialogRepository, times(1)).deleteById(dialogId);
    }
}

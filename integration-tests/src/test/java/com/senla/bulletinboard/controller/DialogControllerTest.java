package com.senla.bulletinboard.controller;

import com.senla.bulletinboard.dto.DialogDto;
import com.senla.bulletinboard.dto.IdDto;
import com.senla.bulletinboard.dto.MessageDto;
import com.senla.bulletinboard.entity.DialogEntity;
import com.senla.bulletinboard.entity.MessageEntity;
import com.senla.bulletinboard.mapper.interfaces.DialogDtoEntityMapper;
import com.senla.bulletinboard.mapper.interfaces.MessageDtoEntityMapper;
import com.senla.bulletinboard.mock.DialogMock;
import com.senla.bulletinboard.mock.MessageMock;
import com.senla.bulletinboard.repository.DialogRepository;
import com.senla.bulletinboard.repository.MessageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Test
    public void showMessagesTest() throws Exception {
        final long id = 3L;
        final List<MessageDto> expected = MessageMock.getAll();
        final List<MessageEntity> entities = expected
            .stream()
            .map(messageDtoEntityMapper::sourceToDestination)
            .collect(Collectors.toList());
        willReturn(entities).given(messageRepository).findAllByDialogId(id);

        mockMvc.perform(get("/api/dialogs/" + id + "/messages")
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void createDialogTest() throws Exception {
        final DialogDto dialogDto = DialogMock.getById(1L);
        final DialogEntity dialogEntity = dialogDtoEntityMapper.sourceToDestination(dialogDto);
        final String request = objectMapper.writeValueAsString(dialogDto);
        final String response = objectMapper.writeValueAsString(new IdDto(1L));

        willReturn(false).given(dialogRepository)
            .existsByBulletin_IdAndCustomerId(dialogDto.getBulletinId(), dialogDto.getCustomerId());
        willReturn(dialogEntity).given(dialogRepository).save(any(DialogEntity.class));

        mockMvc.perform(post("/api/dialogs")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request))
            .andExpect(status().isCreated())
            .andExpect(content().json(response));
    }

    @Test
    public void createDialogTest_DialogAlreadyExists() throws Exception {
        final DialogDto dialogDto = DialogMock.getById(1L);
        final String request = objectMapper.writeValueAsString(dialogDto);

        final DialogEntity dialogEntity = dialogDtoEntityMapper.sourceToDestination(dialogDto);

        willReturn(true).given(dialogRepository)
            .existsByBulletin_IdAndCustomerId(dialogDto.getBulletinId(), dialogDto.getCustomerId());
        willReturn(dialogEntity).given(dialogRepository).save(any(DialogEntity.class));

        mockMvc.perform(post("/api/dialogs")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request))
            .andExpect(status().isConflict())
            .andExpect(content().json("{\n" +
                                      "    \"errorMessage\": \"Dialog with bulletin id " + dialogDto.getBulletinId() +
                                      " and customer id " + dialogDto.getCustomerId() + " already exists\"\n" +
                                      "}"));
    }

    @Test
    public void deleteDialogTest() throws Exception {
        final long id = 3L;

        mockMvc.perform(delete("/api/dialogs/" + id)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(dialogRepository, times(1)).deleteById(id);
    }
}

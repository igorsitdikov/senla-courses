package com.senla.bulletinboard.controller;

import com.senla.bulletinboard.dto.BulletinDto;
import com.senla.bulletinboard.dto.DialogDto;
import com.senla.bulletinboard.dto.PasswordDto;
import com.senla.bulletinboard.dto.UserDto;
import com.senla.bulletinboard.entity.BulletinEntity;
import com.senla.bulletinboard.entity.DialogEntity;
import com.senla.bulletinboard.entity.UserEntity;
import com.senla.bulletinboard.mapper.interfaces.BulletinDtoEntityMapper;
import com.senla.bulletinboard.mapper.interfaces.DialogDtoEntityMapper;
import com.senla.bulletinboard.mapper.interfaces.UserDtoEntityMapper;
import com.senla.bulletinboard.mock.BulletinDetailsMock;
import com.senla.bulletinboard.mock.DialogMock;
import com.senla.bulletinboard.mock.UserMock;
import com.senla.bulletinboard.repository.BulletinRepository;
import com.senla.bulletinboard.repository.DialogRepository;
import com.senla.bulletinboard.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends AbstractControllerTest {

    @MockBean
    private UserRepository userRepository;
    @SpyBean
    private UserDtoEntityMapper userDtoEntityMapper;
    @MockBean
    private DialogRepository dialogRepository;
    @MockBean
    private BulletinRepository bulletinRepository;
    @SpyBean
    private BulletinDtoEntityMapper bulletinDtoEntityMapper;

    @Test
    public void testGetUser() throws Exception {
        final long id = 1L;
        final UserDto expected = UserMock.getUserDtoById(id);
        final UserEntity userEntity = userDtoEntityMapper.sourceToDestination(expected);

        willReturn(Optional.of(userEntity)).given(userRepository).findById(id);

        mockMvc.perform(get("/api/users/" + id)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void testGetUser_UserNotFoundException() throws Exception {
        final long id = 1L;

        willReturn(Optional.empty()).given(userRepository).findById(id);

        mockMvc.perform(get("/api/users/" + id)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content().json("{\"errorMessage\":\"Entity UserEntity with id " + id + " was not found\"}"));
    }

    @Test
    public void testShowDialogs() throws Exception {
        final long id = 3L;
        final List<DialogDto> dialogDtos = DialogMock.getAll();
        final List<DialogEntity> dialogEntities = dialogDtos
            .stream()
            .map(dialogDto -> {
                DialogEntity dialogEntity = new DialogEntity();
                dialogEntity.setId(dialogDto.getId());
                dialogEntity.setBulletin(bulletinDtoEntityMapper.sourceToDestination(dialogDto.getBulletin()));
                dialogEntity.setBulletinId(dialogDto.getBulletinId());
                dialogEntity.setCustomerId(dialogDto.getCustomerId());
                dialogEntity.setCreatedAt(dialogDto.getCreatedAt());
                return dialogEntity;
            })
            .collect(Collectors.toList());
        final List<DialogDto> expected = dialogDtos
            .stream()
            .map(dialogDto -> {
                DialogDto dialog = new DialogDto();
                dialog.setId(dialogDto.getId());
                dialog.setTitle(dialogDto.getTitle());
                dialog.setBulletinId(dialogDto.getBulletinId());
                dialog.setCreatedAt(dialogDto.getCreatedAt());
                dialog.setCustomerId(dialogDto.getCustomerId());
                return dialog;
            })
            .collect(Collectors.toList());
        willReturn(dialogEntities).given(dialogRepository).findAllByBulletin_SellerIdOrCustomerId(id, id);

        final String content = objectMapper.writeValueAsString(expected);
        mockMvc.perform(get("/api/users/" + id + "/dialogs")
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(content));
    }

    @Test
    public void testShowBulletins() throws Exception {
        final long id = 4L;
        final List<BulletinDto> expected = BulletinDetailsMock.getAll()
            .stream()
            .peek(el -> el.setComments(new ArrayList<>()))
            .collect(Collectors.toList());
        final List<BulletinEntity> bulletinEntities = expected
            .stream()
            .map(bulletinDtoEntityMapper::sourceToDestination)
            .collect(Collectors.toList());

        willReturn(true).given(userRepository).existsById(id);
        willReturn(bulletinEntities).given(bulletinRepository).findAllBySellerId(id);

        mockMvc.perform(get("/api/users/" + id + "/bulletins")
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void testShowBulletins_UserNotFoundException() throws Exception {
        final long id = 4L;
        final List<BulletinDto> expected = BulletinDetailsMock.getAll()
            .stream()
            .peek(el -> el.setComments(new ArrayList<>()))
            .collect(Collectors.toList());
        final List<BulletinEntity> bulletinEntities = expected
            .stream()
            .map(bulletinDtoEntityMapper::sourceToDestination)
            .collect(Collectors.toList());

        willReturn(false).given(userRepository).existsById(id);
        willReturn(bulletinEntities).given(bulletinRepository).findAllBySellerId(id);

        mockMvc.perform(get("/api/users/" + id + "/bulletins")
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content().json("{\"errorMessage\":\"User with such id " + id + " does not exist\"}"));
    }

    @Test
    public void testUpdateUser() throws Exception {
        final long id = 1L;
        final UserDto expected = UserMock.getUserDtoById(id);
        final UserEntity userEntity = userDtoEntityMapper.sourceToDestination(expected);
        willReturn(userEntity).given(userRepository).save(any(UserEntity.class));
        mockMvc.perform(put("/api/users/" + id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(expected)))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void testChangePassword() throws Exception {
        final long id = 1L;
        PasswordDto password = new PasswordDto();
        password.setOldPassword("123456");
        password.setNewPassword("111111");
        password.setConfirmPassword("111111");

        mockMvc.perform(patch("/api/users/" + id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(password))
                       )
            .andExpect(status().isOk());
    }

}

package com.senla.bulletinboard.controller;

import com.senla.bulletinboard.dto.BulletinDto;
import com.senla.bulletinboard.dto.DialogDto;
import com.senla.bulletinboard.dto.PasswordDto;
import com.senla.bulletinboard.dto.UserDto;
import com.senla.bulletinboard.entity.BulletinEntity;
import com.senla.bulletinboard.entity.DialogEntity;
import com.senla.bulletinboard.entity.UserEntity;
import com.senla.bulletinboard.enumerated.UserRole;
import com.senla.bulletinboard.mapper.interfaces.BulletinDtoEntityMapper;
import com.senla.bulletinboard.mapper.interfaces.UserDtoEntityMapper;
import com.senla.bulletinboard.mock.BulletinDetailsMock;
import com.senla.bulletinboard.mock.DialogMock;
import com.senla.bulletinboard.mock.UserMock;
import com.senla.bulletinboard.repository.BulletinRepository;
import com.senla.bulletinboard.repository.DialogRepository;
import com.senla.bulletinboard.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
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

    @BeforeEach
    public void initAuthorizedUser() {
        final Long userId = 4L;
        final UserEntity user = userDtoEntityMapper.sourceToDestination(UserMock.getById(userId));
        final String password = UserMock.getById(userId).getPassword();
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(UserRole.USER);
        willReturn(Optional.of(user)).given(userRepository).findByEmail(user.getEmail());
        willReturn(Optional.of(user)).given(userRepository).findById(userId);
    }

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
        final long id = 4L;
        final long userId = 4L;
        String token = signInAsUser(userId);
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
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(content));
    }

    @Test
    public void testShowBulletins() throws Exception {
        final long id = 4L;
        String token = signInAsUser(id);
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
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void testShowBulletins_UserNotFoundException() throws Exception {
        final long id = 4L;
        String token = signInAsUser(id);
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
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content().json("{\"errorMessage\":\"No user with id " + id + " was found.\"}"));
    }

    @Test
    public void testUpdateUser() throws Exception {
        final long userId = 4L;
        String token = signInAsUser(userId);
        final UserDto expected = UserMock.getUserDtoById(userId);
        final UserEntity userEntity = userDtoEntityMapper.sourceToDestination(expected);
        willReturn(userEntity).given(userRepository).save(any(UserEntity.class));
        mockMvc.perform(put("/api/users/" + userId)
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(expected)))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void testChangePassword() throws Exception {
        final long id = 4L;
        String token = signInAsUser(id);
        PasswordDto passwordDto = new PasswordDto();
        passwordDto.setOldPassword("anton");
        passwordDto.setNewPassword("111111");
        passwordDto.setConfirmPassword("111111");

        mockMvc.perform(patch("/api/users/" + id)
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(passwordDto))                       )
            .andExpect(status().isOk());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    public void testChangePassword_WrongOldPassword() throws Exception {
        final long id = 4L;
        String token = signInAsUser(id);
        PasswordDto passwordDto = new PasswordDto();
        passwordDto.setOldPassword("222222");
        passwordDto.setNewPassword("111111");
        passwordDto.setConfirmPassword("111111");

        mockMvc.perform(patch("/api/users/" + id)
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(passwordDto))                       )
            .andExpect(status().isOk());
        verify(userRepository, times(0)).save(any(UserEntity.class));
    }

    @Test
    public void testChangePassword_NoSuchUserException() throws Exception {
        final long id = 4L;
        String token = signInAsUser(id);
        PasswordDto passwordDto = new PasswordDto();
        passwordDto.setOldPassword("123456");
        passwordDto.setNewPassword("111111");
        passwordDto.setConfirmPassword("111111");
        final Long userId = 4L;
        final UserEntity user = userDtoEntityMapper.sourceToDestination(UserMock.getById(userId));
        final String password = UserMock.getById(userId).getPassword();
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(UserRole.USER);
        willReturn(Optional.empty()).given(userRepository).findById(userId);

        mockMvc.perform(patch("/api/users/" + id)
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(passwordDto))                       )
            .andExpect(status().isNotFound());
    }
}

package com.senla.bulletinboard.controller;

import com.senla.bulletinboard.dto.ApiErrorDto;
import com.senla.bulletinboard.dto.BulletinDto;
import com.senla.bulletinboard.dto.DialogDto;
import com.senla.bulletinboard.dto.PasswordDto;
import com.senla.bulletinboard.dto.UserDto;
import com.senla.bulletinboard.entity.BulletinEntity;
import com.senla.bulletinboard.entity.DialogEntity;
import com.senla.bulletinboard.entity.UserEntity;
import com.senla.bulletinboard.enumerated.ExceptionType;
import com.senla.bulletinboard.mapper.interfaces.BulletinDetailsDtoEntityMapper;
import com.senla.bulletinboard.mapper.interfaces.UserDtoEntityMapper;
import mock.BulletinMock;
import mock.DialogMock;
import mock.UserMock;
import com.senla.bulletinboard.repository.BulletinRepository;
import com.senla.bulletinboard.repository.DialogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends AbstractControllerTest {

    @SpyBean
    private UserDtoEntityMapper userDtoEntityMapper;
    @MockBean
    private DialogRepository dialogRepository;
    @MockBean
    private BulletinRepository bulletinRepository;
    @SpyBean
    private BulletinDetailsDtoEntityMapper bulletinDtoEntityMapper;

    @BeforeEach
    public void initAuthorizedUser() {
        final UserEntity user = new UserMock().getEntityById(USER_ANTON);
        willReturn(Optional.of(user)).given(userRepository).findById(USER_ANTON);
    }

    @Test
    public void testGetUser() throws Exception {
        final String token = signInAsUser(USER_ANTON);
        final UserDto expected = new UserMock().getUserDtoById(USER_ANTON);
        final UserEntity userEntity = userDtoEntityMapper.sourceToDestination(expected);

        willReturn(Optional.of(userEntity)).given(userRepository).findById(USER_ANTON);

        mockMvc.perform(get("/api/users/" + USER_ANTON)
                            .contextPath(CONTEXT_PATH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", token))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void testGetUser_UserNotFoundException() throws Exception {
        final String token = signInAsUser(USER_ANTON);

        willReturn(Optional.empty()).given(userRepository).findById(USER_ANTON);

        final String response = mockMvc.perform(get("/api/users/" + USER_ANTON)
                                                    .contextPath(CONTEXT_PATH)
                                                    .contentType(MediaType.APPLICATION_JSON)
                                                    .header("Authorization", token))
            .andExpect(status().isNotFound())
            .andReturn().getResponse().getContentAsString();

        String message = translator.toLocale("entity-not-found", UserEntity.class.getSimpleName(), USER_ANTON);
        final ApiErrorDto expectedError = expectedErrorCreator(
            HttpStatus.NOT_FOUND,
            ExceptionType.BUSINESS_LOGIC,
            message);

        assertErrorResponse(expectedError, response);
    }

    @Test
    public void testShowDialogs() throws Exception {
        final Long id = 4L;
        final String token = signInAsUser(USER_ANTON);
        final List<DialogDto> dialogDtos = new DialogMock().getAll();
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

        willReturn(dialogEntities).given(dialogRepository).findAllByBulletinSellerIdOrCustomerId(id, id);

        final String content = objectMapper.writeValueAsString(expected);
        mockMvc.perform(get("/api/users/" + id + "/dialogs")
                            .contextPath(CONTEXT_PATH)
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(content));
    }

    @Test
    public void testShowBulletins() throws Exception {
        final String token = signInAsUser(USER_ANTON);
        final int page = 0;
        final int size = 10;
        final Pageable pageWithSize = PageRequest.of(page, size);
        final List<BulletinDto> expected = new BulletinMock().getAll()
            .stream()
            .peek(el -> el.setComments(new ArrayList<>()))
            .collect(Collectors.toList());
        final List<BulletinEntity> bulletinEntities = expected
            .stream()
            .map(bulletinDtoEntityMapper::sourceToDestination)
            .collect(Collectors.toList());
        willReturn(true).given(userRepository).existsById(USER_ANTON);
        willReturn(bulletinEntities).given(bulletinRepository).findAllBySellerId(USER_ANTON, pageWithSize);

        mockMvc.perform(get("/api/users/" + USER_ANTON + "/bulletins")
                            .contextPath(CONTEXT_PATH)
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void testShowBulletins_UserNotFoundException() throws Exception {
        final String token = signInAsUser(USER_ANTON);
        final int page = 0;
        final int size = 2;
        final Pageable pageWithSize = PageRequest.of(page, size);
        final List<BulletinDto> expected = new BulletinMock().getAll()
            .stream()
            .peek(el -> el.setComments(new ArrayList<>()))
            .collect(Collectors.toList());
        final List<BulletinEntity> bulletinEntities = expected
            .stream()
            .map(bulletinDtoEntityMapper::sourceToDestination)
            .collect(Collectors.toList());

        willReturn(false).given(userRepository).existsById(USER_ANTON);
        willReturn(bulletinEntities).given(bulletinRepository).findAllBySellerId(USER_ANTON, pageWithSize);

        final String response = mockMvc.perform(get("/api/users/" + USER_ANTON + "/bulletins")
                                                    .contextPath(CONTEXT_PATH)
                                                    .header("Authorization", token)
                                                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andReturn().getResponse().getContentAsString();

        String message = translator.toLocale("no-such-user-id", USER_ANTON);
        final ApiErrorDto expectedError = expectedErrorCreator(
            HttpStatus.NOT_FOUND,
            ExceptionType.BUSINESS_LOGIC,
            message);

        assertErrorResponse(expectedError, response);
    }

    @Test
    public void testUpdateUser() throws Exception {
        final String token = signInAsUser(USER_ANTON);
        final UserDto expected = new UserMock().getUserDtoById(USER_ANTON);
        final UserEntity userEntity = userDtoEntityMapper.sourceToDestination(expected);
        willReturn(userEntity).given(userRepository).save(any(UserEntity.class));
        mockMvc.perform(put("/api/users/" + USER_ANTON)
                            .contextPath(CONTEXT_PATH)
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(expected)))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void testChangePassword() throws Exception {
        final String token = signInAsUser(USER_ANTON);
        PasswordDto passwordDto = new PasswordDto();
        passwordDto.setOldPassword("anton12");
        passwordDto.setNewPassword("111111");
        passwordDto.setConfirmPassword("111111");

        mockMvc.perform(patch("/api/users/" + USER_ANTON)
                            .contextPath(CONTEXT_PATH)
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(passwordDto)))
            .andExpect(status().isOk());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    public void testChangePassword_WrongOldPassword() throws Exception {
        final String token = signInAsUser(USER_ANTON);
        PasswordDto passwordDto = new PasswordDto();
        passwordDto.setOldPassword("222222");
        passwordDto.setNewPassword("111111");
        passwordDto.setConfirmPassword("111111");

        mockMvc.perform(patch("/api/users/" + USER_ANTON)
                            .contextPath(CONTEXT_PATH)
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(passwordDto)))
            .andExpect(status().isOk());
        verify(userRepository, times(0)).save(any(UserEntity.class));
    }

    @Test
    public void testChangePassword_NoSuchUserException() throws Exception {
        final String token = signInAsUser(USER_ANTON);
        PasswordDto passwordDto = new PasswordDto();
        passwordDto.setOldPassword("123456");
        passwordDto.setNewPassword("111111");
        passwordDto.setConfirmPassword("111111");

        willReturn(Optional.empty()).given(userRepository).findById(USER_ANTON);

        mockMvc.perform(patch("/api/users/" + USER_ANTON)
                            .contextPath(CONTEXT_PATH)
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(passwordDto)))
            .andExpect(status().isNotFound());
    }
}

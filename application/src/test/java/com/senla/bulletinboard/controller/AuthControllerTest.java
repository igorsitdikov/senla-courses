package com.senla.bulletinboard.controller;

import com.senla.bulletinboard.dto.ApiErrorDto;
import com.senla.bulletinboard.dto.TokenDto;
import com.senla.bulletinboard.dto.UserRequestDto;
import com.senla.bulletinboard.entity.TokenBlacklistEntity;
import com.senla.bulletinboard.entity.UserEntity;
import com.senla.bulletinboard.enumerated.ExceptionType;
import mock.UserMock;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest extends AbstractControllerTest {

    @Test
    public void signUpTest() throws Exception {
        final UserRequestDto request = new UserMock().getById(USER_IVAN);
        final UserEntity user = new UserMock().getEntityById(USER_IVAN);

        willReturn(user).given(userRepository).save(any(UserEntity.class));
        willReturn(Optional.empty()).given(userRepository).findByEmail(request.getEmail());

        final String response = mockMvc.perform(post("/api/sign-up")
                            .contextPath(CONTEXT_PATH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andReturn().getResponse().getContentAsString();

        TokenDto tokenDto = objectMapper.readValue(response, TokenDto.class);
        assertEquals(USER_IVAN, tokenDto.getUserId());
    }

    @Test
    public void signUpTest_SuchUserAlreadyExistsException() throws Exception {
        final UserRequestDto request = new UserMock().getById(USER_IVAN);
        final UserEntity user = new UserMock().getEntityById(USER_IVAN);

        willReturn(user).given(userRepository).save(any(UserEntity.class));
        willReturn(Optional.of(user)).given(userRepository).findByEmail(request.getEmail());

        final String response = mockMvc.perform(post("/api/sign-up")
                                                    .contextPath(CONTEXT_PATH)
                                                    .contentType(MediaType.APPLICATION_JSON)
                                                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isConflict())
            .andReturn().getResponse().getContentAsString();

        final String message = translator.toLocale("user-already-exists", user.getEmail());
        final ApiErrorDto expectedError = expectedErrorCreator(
            HttpStatus.CONFLICT,
            ExceptionType.BUSINESS_LOGIC,
            message);

        assertErrorResponse(expectedError, response);
    }

    @Test
    public void signInTest_NoSuchUserException() throws Exception {
        final UserRequestDto request = new UserMock().getById(USER_IVAN);

        willReturn(Optional.empty()).given(userRepository).findByEmail(request.getEmail());

        final String response = mockMvc.perform(post("/api/sign-in")
                                                    .contextPath(CONTEXT_PATH)
                                                    .contentType(MediaType.APPLICATION_JSON)
                                                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isNotFound())
            .andReturn().getResponse().getContentAsString();

        final String message = translator.toLocale("no-such-user", request.getEmail());
        final ApiErrorDto expectedError = expectedErrorCreator(
            HttpStatus.NOT_FOUND,
            ExceptionType.BUSINESS_LOGIC,
            message);

        assertErrorResponse(expectedError, response);
    }

    @Test
    public void logoutTest() throws Exception {
        final String token = signInAsUser(USER_PETR);
        mockMvc.perform(delete("/api/log-out")
                            .contextPath(CONTEXT_PATH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", token))
            .andExpect(status().isOk());
        verify(tokenBlacklistRepository, times(1)).save(any(TokenBlacklistEntity.class));
    }
}

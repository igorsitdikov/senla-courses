package com.senla.bulletinboard.controller;

import com.senla.bulletinboard.dto.ApiErrorDto;
import com.senla.bulletinboard.dto.UserRequestDto;
import com.senla.bulletinboard.entity.UserEntity;
import com.senla.bulletinboard.enumerated.ExceptionType;
import com.senla.bulletinboard.enumerated.UserRole;
import com.senla.bulletinboard.mapper.interfaces.UserDtoEntityMapper;
import com.senla.bulletinboard.mock.UserMock;
import com.senla.bulletinboard.repository.UserRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest extends AbstractControllerTest {

    @MockBean
    private UserRepository userRepository;
    @SpyBean
    private UserDtoEntityMapper userDtoEntityMapper;

    @Test
    public void signUpTest() throws Exception {
        final long userId = 1L;
        final UserRequestDto request = UserMock.getById(userId);
        final UserEntity user = userDtoEntityMapper.sourceToDestination(UserMock.getById(userId));
        final String password = UserMock.getById(userId).getPassword();
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(UserRole.USER);

        willReturn(user).given(userRepository).save(any(UserEntity.class));
        willReturn(Optional.empty()).given(userRepository).findByEmail(request.getEmail());

        mockMvc.perform(post("/api/sign-up")
                            .contextPath(CONTEXT_PATH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void signUpTest_SuchUserAlreadyExistsException() throws Exception {
        final long userId = 1L;
        final UserRequestDto request = UserMock.getById(userId);
        final UserEntity user = userDtoEntityMapper.sourceToDestination(UserMock.getById(userId));
        final String password = UserMock.getById(userId).getPassword();
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(UserRole.USER);

        willReturn(user).given(userRepository).save(any(UserEntity.class));
        willReturn(Optional.of(user)).given(userRepository).findByEmail(request.getEmail());

        String message = Translator.toLocale("user-already-exists", user.getEmail());
        final ApiErrorDto expectedError =
            expectedErrorCreator(HttpStatus.CONFLICT, ExceptionType.BUSINESS_LOGIC, message);

        final String response = mockMvc.perform(post("/api/sign-up")
                                                    .contextPath(CONTEXT_PATH)
                                                    .contentType(MediaType.APPLICATION_JSON)
                                                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isConflict())
            .andReturn().getResponse().getContentAsString();
        assertErrorResponse(expectedError, response);
    }

    @Test
    public void signInTest_NoSuchUserException() throws Exception {
        final long userId = 1L;
        final UserRequestDto request = UserMock.getById(userId);

        willReturn(Optional.empty()).given(userRepository).findByEmail(request.getEmail());

        String message = Translator.toLocale("no-such-user", request.getEmail());
        final ApiErrorDto expectedError =
            expectedErrorCreator(HttpStatus.NOT_FOUND, ExceptionType.BUSINESS_LOGIC, message);

        final String response = mockMvc.perform(post("/api/sign-in")
                                                    .contextPath(CONTEXT_PATH)
                                                    .contentType(MediaType.APPLICATION_JSON)
                                                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isNotFound())
            .andReturn().getResponse().getContentAsString();
        assertErrorResponse(expectedError, response);
    }
}

package com.senla.bulletinboard.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.bulletinboard.dto.ApiErrorDto;
import com.senla.bulletinboard.dto.SignInDto;
import com.senla.bulletinboard.dto.TokenDto;
import com.senla.bulletinboard.entity.TokenBlacklistEntity;
import com.senla.bulletinboard.entity.UserEntity;
import com.senla.bulletinboard.enumerated.ExceptionType;
import com.senla.bulletinboard.mock.UserMock;
import com.senla.bulletinboard.repository.TokenBlacklistRepository;
import com.senla.bulletinboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public abstract class AbstractControllerTest {

    private final static String TOKEN_PREFIX = "Bearer ";
    protected final static Long USER_IVAN = 1L;
    protected final static Long USER_PETR = 2L;
    protected final static Long ADMIN_ALEX = 3L;
    protected final static Long USER_ANTON = 4L;
    protected final static String CONTEXT_PATH = "/api";
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected PasswordEncoder passwordEncoder;
    @MockBean
    protected UserRepository userRepository;
    @MockBean
    protected TokenBlacklistRepository tokenBlacklistRepository;

    protected String signInAsUser(final Long userId) throws Exception {
        final UserEntity user = new UserMock().getEntityById(userId);
        final String password = new UserMock().getById(userId).getPassword();
        final SignInDto request = new SignInDto();
        request.setEmail(new UserMock().getById(userId).getEmail());
        request.setPassword(password);

        willReturn(Optional.of(user)).given(userRepository).findByEmail(user.getEmail());

        final String response = mockMvc.perform(
            post("/api/sign-in")
                .contextPath(CONTEXT_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

        String token = TOKEN_PREFIX + objectMapper.readValue(response, TokenDto.class).getToken();
        TokenBlacklistEntity tokenBlacklistEntity = new TokenBlacklistEntity();
        tokenBlacklistEntity.setToken(token);

        willReturn(Optional.empty()).given(tokenBlacklistRepository).findByToken(token);
        return token;
    }

    protected void assertErrorResponse(final ApiErrorDto expectedError, final String response)
        throws JsonProcessingException {
        final ApiErrorDto actual = objectMapper.readValue(response, ApiErrorDto.class);
        assertEquals(expectedError.getStatus(), actual.getStatus());
        assertEquals(expectedError.getErrors(), actual.getErrors());
        assertEquals(expectedError.getMessage(), actual.getMessage());
    }

    protected ApiErrorDto expectedErrorCreator(final HttpStatus httpStatus,
                                               final ExceptionType exceptionType,
                                               final String... message) {
        final List<String> errors = new ArrayList<>(Arrays.asList(message));
        return new ApiErrorDto(
            LocalDateTime.now(),
            httpStatus,
            exceptionType.getMessage(),
            errors);
    }
}

package com.senla.bulletinboard.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.bulletinboard.dto.ApiErrorDto;
import com.senla.bulletinboard.dto.SignInDto;
import com.senla.bulletinboard.dto.TokenDto;
import com.senla.bulletinboard.enumerated.ExceptionType;
import com.senla.bulletinboard.mock.UserMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public abstract class AbstractControllerTest {

    protected final static String CONTEXT_PATH = "/api";
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected PasswordEncoder passwordEncoder;

    protected String signInAsUser(final Long userId) throws Exception {
        final String password = UserMock.getById(userId).getPassword();
        final SignInDto request = new SignInDto();
        request.setEmail(UserMock.getById(userId).getEmail());
        request.setPassword(password);

        final String response = mockMvc.perform(
            post("/api/sign-in")
                .contextPath(CONTEXT_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();
        return "Bearer " + objectMapper.readValue(response, TokenDto.class).getToken();
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

package com.senla.bulletinboard.controller;

import com.senla.bulletinboard.dto.UserRequestDto;
import com.senla.bulletinboard.entity.UserEntity;
import com.senla.bulletinboard.enumerated.UserRole;
import com.senla.bulletinboard.mapper.interfaces.BulletinDtoEntityMapper;
import com.senla.bulletinboard.mapper.interfaces.UserDtoEntityMapper;
import com.senla.bulletinboard.mock.UserMock;
import com.senla.bulletinboard.repository.BulletinRepository;
import com.senla.bulletinboard.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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

        mockMvc.perform(post("/api/sign-up")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isConflict())
            .andExpect(content().json("{\"errorMessage\":\"User with email " + user.getEmail() + " already exists\"}"));
    }

    @Test
    public void signInTest_NoSuchUserException() throws Exception {
        final long userId = 1L;
        final UserRequestDto request = UserMock.getById(userId);

        willReturn(Optional.empty()).given(userRepository).findByEmail(request.getEmail());

        mockMvc.perform(post("/api/sign-in")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isNotFound())
            .andExpect(content().json("{\"errorMessage\":\"No user with email = " + request.getEmail() + " was found.\"}"));
    }
}

package com.senla.bulletinboard.controller;

import com.senla.bulletinboard.dto.ApiErrorDto;
import com.senla.bulletinboard.dto.IdDto;
import com.senla.bulletinboard.dto.SellerVoteDto;
import com.senla.bulletinboard.entity.BulletinEntity;
import com.senla.bulletinboard.entity.SellerVoteEntity;
import com.senla.bulletinboard.entity.UserEntity;
import com.senla.bulletinboard.enumerated.BulletinStatus;
import com.senla.bulletinboard.enumerated.ExceptionType;
import com.senla.bulletinboard.enumerated.UserRole;
import com.senla.bulletinboard.mapper.interfaces.BulletinDetailsDtoEntityMapper;
import com.senla.bulletinboard.mapper.interfaces.UserDtoEntityMapper;
import com.senla.bulletinboard.mock.BulletinMock;
import com.senla.bulletinboard.mock.UserMock;
import com.senla.bulletinboard.repository.BulletinRepository;
import com.senla.bulletinboard.repository.SellerVoteRepository;
import com.senla.bulletinboard.security.AuthUser;
import com.senla.bulletinboard.utils.Translator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VoteControllerTest extends AbstractControllerTest {

    @MockBean
    private BulletinRepository bulletinRepository;
    @MockBean
    private SellerVoteRepository sellerVoteRepository;
    @SpyBean
    private BulletinDetailsDtoEntityMapper bulletinDtoEntityMapper;
    @SpyBean
    private UserDtoEntityMapper userDtoEntityMapper;

    @Test
    public void addVoteToSellerTest() throws Exception {
        final long userId = 3L;
        final String token = signInAsUser(userId);
        final long id = 1L;
        final long bulletinId = 4L;
        final Integer stars = 5;
        SellerVoteEntity sellerVoteEntity = new SellerVoteEntity();
        sellerVoteEntity.setBulletinId(bulletinId);
        sellerVoteEntity.setVoterId(userId);
        sellerVoteEntity.setVote(5);
        sellerVoteEntity.setId(id);
        final SellerVoteDto sellerVoteDto = new SellerVoteDto(1L, userId, bulletinId, stars);
        final BulletinEntity bulletinEntity =
            bulletinDtoEntityMapper.sourceToDestination(BulletinMock.getById(bulletinId));
        willReturn(Optional.of(bulletinEntity)).given(bulletinRepository).findById(bulletinId);
        willReturn(sellerVoteEntity).given(sellerVoteRepository).save(any(SellerVoteEntity.class));

        final String response = objectMapper.writeValueAsString(new IdDto(id));
        mockMvc.perform(post("/api/votes/")
                            .contextPath(CONTEXT_PATH)
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(sellerVoteDto)))
            .andExpect(status().isCreated())
            .andExpect(content().json(response));
    }

    @Test
    public void addVoteToSellerTest_BulletinIsClosedException() throws Exception {
        final long userId = 3L;
        final String token = signInAsUser(userId);
        final long id = 1L;
        final long bulletinId = 4L;
        final Integer stars = 5;
        SellerVoteEntity sellerVoteEntity = new SellerVoteEntity();
        sellerVoteEntity.setBulletinId(bulletinId);
        sellerVoteEntity.setVoterId(userId);
        sellerVoteEntity.setVote(5);
        sellerVoteEntity.setId(id);
        final SellerVoteDto sellerVoteDto = new SellerVoteDto(1L, userId, bulletinId, stars);
        final BulletinEntity bulletinEntity =
            bulletinDtoEntityMapper.sourceToDestination(BulletinMock.getById(bulletinId));
        bulletinEntity.setStatus(BulletinStatus.CLOSE);
        willReturn(Optional.of(bulletinEntity)).given(bulletinRepository).findById(bulletinId);
        willReturn(sellerVoteEntity).given(sellerVoteRepository).save(any(SellerVoteEntity.class));
        String message = Translator.toLocale("bulletin-closed", bulletinId);
        final ApiErrorDto expectedError =
            expectedErrorCreator(HttpStatus.BAD_REQUEST, ExceptionType.BUSINESS_LOGIC, message);

        final String response = mockMvc.perform(post("/api/votes/")
                                                    .contextPath(CONTEXT_PATH)
                                                    .header("Authorization", token)
                                                    .contentType(MediaType.APPLICATION_JSON)
                                                    .content(objectMapper.writeValueAsString(sellerVoteDto)))
            .andExpect(status().isBadRequest())
            .andReturn().getResponse().getContentAsString();
        assertErrorResponse(expectedError, response);
    }

    @Test
    public void addVoteToSellerTest_BulletinNotExists() throws Exception {
        final long userId = 3L;
        final String token = signInAsUser(userId);
        final long id = 1L;
        final long bulletinId = 4L;
        final Integer stars = 5;
        SellerVoteEntity sellerVoteEntity = new SellerVoteEntity();
        sellerVoteEntity.setBulletinId(bulletinId);
        sellerVoteEntity.setVoterId(userId);
        sellerVoteEntity.setVote(5);
        sellerVoteEntity.setId(id);
        final SellerVoteDto sellerVoteDto = new SellerVoteDto(1L, userId, bulletinId, stars);

        willReturn(Optional.empty()).given(bulletinRepository).findById(bulletinId);
        willReturn(sellerVoteEntity).given(sellerVoteRepository).save(any(SellerVoteEntity.class));
        String message = Translator.toLocale("bulletin-not-exists", bulletinId);
        final ApiErrorDto expectedError =
            expectedErrorCreator(HttpStatus.NOT_FOUND, ExceptionType.BUSINESS_LOGIC, message);

        final String response = mockMvc.perform(post("/api/votes/")
                                                    .contextPath(CONTEXT_PATH)
                                                    .header("Authorization", token)
                                                    .contentType(MediaType.APPLICATION_JSON)
                                                    .content(objectMapper.writeValueAsString(sellerVoteDto)))
            .andExpect(status().isNotFound())
            .andReturn().getResponse().getContentAsString();
        assertErrorResponse(expectedError, response);
    }

    @Test
    public void addVoteToSellerTest_WrongVoterException() throws Exception {
        final long userId = 1L;
        final UserEntity user = userDtoEntityMapper.sourceToDestination(UserMock.getById(userId));
        final String password = UserMock.getById(userId).getPassword();
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(UserRole.USER);
        willReturn(Optional.of(user)).given(userRepository).findByEmail(user.getEmail());
        final String token = signInAsUser(userId);
        final long id = 1L;
        final long bulletinId = 4L;
        final Integer stars = 5;
        SellerVoteEntity sellerVoteEntity = new SellerVoteEntity();
        sellerVoteEntity.setBulletinId(bulletinId);
        sellerVoteEntity.setVoterId(userId);
        sellerVoteEntity.setVote(5);
        sellerVoteEntity.setId(id);
        final SellerVoteDto sellerVoteDto = new SellerVoteDto(1L, userId, bulletinId, stars);
        final BulletinEntity bulletinEntity =
            bulletinDtoEntityMapper.sourceToDestination(BulletinMock.getById(bulletinId));

        bulletinEntity.setSeller(user);
        willReturn(Optional.of(bulletinEntity)).given(bulletinRepository).findById(bulletinId);
        willReturn(sellerVoteEntity).given(sellerVoteRepository).save(any(SellerVoteEntity.class));
        String message = Translator.toLocale("vote-forbidden");
        final ApiErrorDto expectedError =
            expectedErrorCreator(HttpStatus.BAD_REQUEST, ExceptionType.BUSINESS_LOGIC, message);

        final String response = mockMvc.perform(post("/api/votes/")
                                                    .contextPath(CONTEXT_PATH)
                                                    .header("Authorization", token)
                                                    .contentType(MediaType.APPLICATION_JSON)
                                                    .content(objectMapper.writeValueAsString(sellerVoteDto)))
            .andExpect(status().isBadRequest())
            .andReturn().getResponse().getContentAsString();
        assertErrorResponse(expectedError, response);
    }

    @Test
    public void addVoteToSellerTest_VoteAlreadyExistsException() throws Exception {
        final long id = 1L;
        final long userId = 3L;
        final long bulletinId = 4L;
        final Integer stars = 5;
        AuthUser authUser = new AuthUser("user", "password", Collections.emptyList(), userId);
        SellerVoteEntity sellerVoteEntity = new SellerVoteEntity();
        sellerVoteEntity.setBulletinId(bulletinId);
        sellerVoteEntity.setVoterId(userId);
        sellerVoteEntity.setVote(5);
        sellerVoteEntity.setId(id);
        final SellerVoteDto sellerVoteDto = new SellerVoteDto(1L, userId, bulletinId, stars);
        final BulletinEntity bulletinEntity =
            bulletinDtoEntityMapper.sourceToDestination(BulletinMock.getById(bulletinId));
        willReturn(Optional.of(bulletinEntity)).given(bulletinRepository).findById(bulletinId);
        willReturn(true).given(sellerVoteRepository).existsByVoterIdAndBulletinId(userId, bulletinId);

        String message = Translator.toLocale("user-already-voted", userId, bulletinId);
        final ApiErrorDto expectedError =
            expectedErrorCreator(HttpStatus.BAD_REQUEST, ExceptionType.BUSINESS_LOGIC, message);

        final String response = mockMvc.perform(post("/api/votes/")
                                                    .contextPath(CONTEXT_PATH)
                                                    .with(user(authUser))
                                                    .contentType(MediaType.APPLICATION_JSON)
                                                    .content(objectMapper.writeValueAsString(sellerVoteDto)))
            .andExpect(status().isBadRequest())
            .andReturn().getResponse().getContentAsString();
        assertErrorResponse(expectedError, response);
    }
}

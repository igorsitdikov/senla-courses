package com.senla.bulletinboard.controller;

import com.senla.bulletinboard.dto.ApiErrorDto;
import com.senla.bulletinboard.dto.IdDto;
import com.senla.bulletinboard.dto.SellerVoteDto;
import com.senla.bulletinboard.entity.BulletinEntity;
import com.senla.bulletinboard.entity.SellerVoteEntity;
import com.senla.bulletinboard.enumerated.ExceptionType;
import com.senla.bulletinboard.mock.BulletinMock;
import com.senla.bulletinboard.mock.VoteMock;
import com.senla.bulletinboard.repository.BulletinRepository;
import com.senla.bulletinboard.repository.SellerVoteRepository;
import com.senla.bulletinboard.utils.Translator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VoteControllerTest extends AbstractControllerTest {

    @MockBean
    private BulletinRepository bulletinRepository;
    @MockBean
    private SellerVoteRepository sellerVoteRepository;

    @Test
    public void addVoteToSellerTest() throws Exception {
        final Long id = 1L;
        final Long bulletinId = 4L;
        final String token = signInAsUser(ADMIN_ALEX);
        final SellerVoteEntity sellerVoteEntity = VoteMock.getEntityById(id);
        final SellerVoteDto sellerVoteDto = VoteMock.getById(id);
        final BulletinEntity bulletinEntity = BulletinMock.getEntityById(bulletinId);

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
        final Long id = 3L;
        final Long bulletinId = 3L;
        final String token = signInAsUser(ADMIN_ALEX);
        final SellerVoteEntity sellerVoteEntity = VoteMock.getEntityById(id);
        final SellerVoteDto sellerVoteDto = VoteMock.getById(id);
        final BulletinEntity bulletinEntity = BulletinMock.getEntityById(bulletinId);

        willReturn(Optional.of(bulletinEntity)).given(bulletinRepository).findById(bulletinId);
        willReturn(sellerVoteEntity).given(sellerVoteRepository).save(any(SellerVoteEntity.class));

        final String message = Translator.toLocale("bulletin-closed", bulletinId);
        final ApiErrorDto expectedError = expectedErrorCreator(
            HttpStatus.BAD_REQUEST,
            ExceptionType.BUSINESS_LOGIC,
            message);

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
        final Long id = 1L;
        final Long bulletinId = 4L;
        final String token = signInAsUser(ADMIN_ALEX);
        final SellerVoteEntity sellerVoteEntity = VoteMock.getEntityById(id);
        final SellerVoteDto sellerVoteDto = VoteMock.getById(id);

        willReturn(Optional.empty()).given(bulletinRepository).findById(bulletinId);
        willReturn(sellerVoteEntity).given(sellerVoteRepository).save(any(SellerVoteEntity.class));

        final String message = Translator.toLocale("bulletin-not-exists", bulletinId);
        final ApiErrorDto expectedError = expectedErrorCreator(
            HttpStatus.NOT_FOUND,
            ExceptionType.BUSINESS_LOGIC,
            message);

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
        final Long id = 2L;
        final Long bulletinId = 4L;
        final String token = signInAsUser(USER_IVAN);
        final SellerVoteEntity sellerVoteEntity = VoteMock.getEntityById(id);
        final SellerVoteDto sellerVoteDto = VoteMock.getById(id);
        final BulletinEntity bulletinEntity = BulletinMock.getEntityById(bulletinId);

        willReturn(Optional.of(bulletinEntity)).given(bulletinRepository).findById(bulletinId);
        willReturn(sellerVoteEntity).given(sellerVoteRepository).save(any(SellerVoteEntity.class));

        final String message = Translator.toLocale("vote-forbidden");
        final ApiErrorDto expectedError = expectedErrorCreator(
            HttpStatus.BAD_REQUEST,
            ExceptionType.BUSINESS_LOGIC,
            message);

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
        final Long id = 1L;
        final Long bulletinId = 4L;
        final String token = signInAsUser(ADMIN_ALEX);
        final SellerVoteDto sellerVoteDto = VoteMock.getById(id);
        final BulletinEntity bulletinEntity = BulletinMock.getEntityById(bulletinId);

        willReturn(Optional.of(bulletinEntity)).given(bulletinRepository).findById(bulletinId);
        willReturn(true).given(sellerVoteRepository).existsByVoterIdAndBulletinId(ADMIN_ALEX, bulletinId);

        final String message = Translator.toLocale("user-already-voted", ADMIN_ALEX, bulletinId);
        final ApiErrorDto expectedError = expectedErrorCreator(
            HttpStatus.BAD_REQUEST,
            ExceptionType.BUSINESS_LOGIC,
            message);

        final String response = mockMvc.perform(post("/api/votes/")
                                                    .contextPath(CONTEXT_PATH)
                                                    .header("Authorization", token)
                                                    .contentType(MediaType.APPLICATION_JSON)
                                                    .content(objectMapper.writeValueAsString(sellerVoteDto)))
            .andExpect(status().isBadRequest())
            .andReturn().getResponse().getContentAsString();
        assertErrorResponse(expectedError, response);
    }
}

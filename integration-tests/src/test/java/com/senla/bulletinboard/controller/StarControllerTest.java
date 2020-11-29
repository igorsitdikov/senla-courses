package com.senla.bulletinboard.controller;

import com.senla.bulletinboard.dto.IdDto;
import com.senla.bulletinboard.dto.SellerVoteDto;
import com.senla.bulletinboard.dto.StarRequestDto;
import com.senla.bulletinboard.entity.BulletinEntity;
import com.senla.bulletinboard.entity.SellerVoteEntity;
import com.senla.bulletinboard.enumerated.BulletinStatus;
import com.senla.bulletinboard.mapper.interfaces.BulletinDtoEntityMapper;
import com.senla.bulletinboard.mock.BulletinDetailsMock;
import com.senla.bulletinboard.repository.BulletinRepository;
import com.senla.bulletinboard.repository.SellerVoteRepository;
import com.senla.bulletinboard.security.AuthUser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StarControllerTest extends AbstractControllerTest {

    @MockBean
    private BulletinRepository bulletinRepository;
    @MockBean
    private SellerVoteRepository sellerVoteRepository;
    @SpyBean
    private BulletinDtoEntityMapper bulletinDtoEntityMapper;

    @Test
    public void addStarToSellerTest() throws Exception {
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
        final StarRequestDto starRequestDto = new StarRequestDto(userId, bulletinId, stars);
        final BulletinEntity bulletinEntity =
            bulletinDtoEntityMapper.sourceToDestination(BulletinDetailsMock.getById(bulletinId));
        willReturn(Optional.of(bulletinEntity)).given(bulletinRepository).findById(bulletinId);
        willReturn(sellerVoteEntity).given(sellerVoteRepository).save(any(SellerVoteEntity.class));

        final String response = objectMapper.writeValueAsString(new IdDto(id));
        mockMvc.perform(post("/api/stars/")
                            .with(user(authUser))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(starRequestDto)))
            .andExpect(status().isCreated())
            .andExpect(content().json(response));
    }

    @Test
    public void addStarToSellerTest_BulletinIsClosedException() throws Exception {
        final long id = 1L;
        final long userId = 3L;
        final long bulletinId = 4L;
        final Integer stars = 5;
        SellerVoteEntity sellerVoteEntity = new SellerVoteEntity();
        sellerVoteEntity.setBulletinId(bulletinId);
        sellerVoteEntity.setVoterId(userId);
        sellerVoteEntity.setVote(5);
        sellerVoteEntity.setId(id);
        final StarRequestDto starRequestDto = new StarRequestDto(userId, bulletinId, stars);
        final BulletinEntity bulletinEntity =
            bulletinDtoEntityMapper.sourceToDestination(BulletinDetailsMock.getById(bulletinId));
        bulletinEntity.setStatus(BulletinStatus.CLOSE);
        willReturn(Optional.of(bulletinEntity)).given(bulletinRepository).findById(bulletinId);
        willReturn(sellerVoteEntity).given(sellerVoteRepository).save(any(SellerVoteEntity.class));
        mockMvc.perform(post("/api/stars/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(starRequestDto)))
            .andExpect(status().isBadRequest())
            .andExpect(content().json("{\"errorMessage\":\"Bulletin with id " + bulletinId + " is closed\"}"));
    }

    @Test
    public void addStarToSellerTest_BulletinNotExists() throws Exception {
        final long id = 1L;
        final long userId = 3L;
        final long bulletinId = 4L;
        final Integer stars = 5;
        SellerVoteEntity sellerVoteEntity = new SellerVoteEntity();
        sellerVoteEntity.setBulletinId(bulletinId);
        sellerVoteEntity.setVoterId(userId);
        sellerVoteEntity.setVote(5);
        sellerVoteEntity.setId(id);
        final StarRequestDto starRequestDto = new StarRequestDto(userId, bulletinId, stars);

        willReturn(Optional.empty()).given(bulletinRepository).findById(bulletinId);
        willReturn(sellerVoteEntity).given(sellerVoteRepository).save(any(SellerVoteEntity.class));
        mockMvc.perform(post("/api/stars/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(starRequestDto)))
            .andExpect(status().isNotFound())
            .andExpect(content().json("{\"errorMessage\":\"Bulletin with id " + bulletinId + " does not exist\"}"));
    }

    @Test
    public void addStarToSellerTest_WrongVoterException() throws Exception {
        final long id = 1L;
        final long userId = 1L;
        final long bulletinId = 4L;
        final Integer stars = 5;
        SellerVoteEntity sellerVoteEntity = new SellerVoteEntity();
        sellerVoteEntity.setBulletinId(bulletinId);
        sellerVoteEntity.setVoterId(userId);
        sellerVoteEntity.setVote(5);
        sellerVoteEntity.setId(id);
        final SellerVoteDto starRequestDto = new SellerVoteDto(1L, userId, bulletinId, stars);

        final BulletinEntity bulletinEntity =
            bulletinDtoEntityMapper.sourceToDestination(BulletinDetailsMock.getById(bulletinId));
        willReturn(Optional.of(bulletinEntity)).given(bulletinRepository).findById(bulletinId);
        willReturn(sellerVoteEntity).given(sellerVoteRepository).save(any(SellerVoteEntity.class));
        mockMvc.perform(post("/api/stars/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(starRequestDto)))
            .andExpect(status().isBadRequest())
            .andExpect(content().json("{\"errorMessage\":\"Forbidden to vote for yourself\"}"));
    }

    @Test
    public void addStarToSellerTest_VoteAlreadyExistsException() throws Exception {
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
        final SellerVoteDto starRequestDto = new SellerVoteDto(1L, userId, bulletinId, stars);
        final BulletinEntity bulletinEntity =
            bulletinDtoEntityMapper.sourceToDestination(BulletinDetailsMock.getById(bulletinId));
        willReturn(Optional.of(bulletinEntity)).given(bulletinRepository).findById(bulletinId);
        when(sellerVoteRepository.save(any(SellerVoteEntity.class))).thenThrow(DataIntegrityViolationException.class);

        mockMvc.perform(post("/api/stars/")
                            .with(user(authUser))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(starRequestDto)))
            .andExpect(status().isBadRequest())
            .andExpect(content().json(
                "{\"errorMessage\":\"User with id " + userId + " already voted for bulletin with id " + bulletinId +
                "\"}"));
    }
}

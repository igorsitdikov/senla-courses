package com.senla.bulletinboard.controller;

import com.senla.bulletinboard.dto.BulletinDto;
import com.senla.bulletinboard.dto.IdDto;
import com.senla.bulletinboard.entity.BulletinEntity;
import com.senla.bulletinboard.entity.UserEntity;
import com.senla.bulletinboard.enumerated.UserRole;
import com.senla.bulletinboard.mapper.interfaces.BulletinDtoEntityMapper;
import com.senla.bulletinboard.mapper.interfaces.UserDtoEntityMapper;
import com.senla.bulletinboard.mock.BulletinDetailsMock;
import com.senla.bulletinboard.mock.UserMock;
import com.senla.bulletinboard.repository.BulletinRepository;
import com.senla.bulletinboard.repository.UserRepository;
import com.senla.bulletinboard.repository.specification.BulletinFilterSortSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BulletinControllerTest extends AbstractControllerTest {

    @MockBean
    private BulletinRepository bulletinRepository;
    @SpyBean
    private BulletinDtoEntityMapper bulletinDtoEntityMapper;
    @MockBean
    private UserRepository userRepository;
    @SpyBean
    private UserDtoEntityMapper userDtoEntityMapper;

    @BeforeEach
    public void initAuthorizedUser() {
        final Long userId = 4L;
        final UserEntity user = userDtoEntityMapper.sourceToDestination(UserMock.getById(userId));
        final String password = UserMock.getById(userId).getPassword();
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(UserRole.USER);
        willReturn(Optional.of(user)).given(userRepository).findByEmail(user.getEmail());
    }

    @Test
    public void showBulletinsTest() throws Exception {
        final List<BulletinDto> expected = BulletinDetailsMock.getAll()
            .stream()
            .peek(bulletinDto -> bulletinDto.setComments(new ArrayList<>()))
            .collect(Collectors.toList());

        final List<BulletinEntity> entities = expected
            .stream()
            .map(bulletinDtoEntityMapper::sourceToDestination)
            .collect(Collectors.toList());

        willReturn(entities).given(bulletinRepository).findAll(any(BulletinFilterSortSpecification.class));
        final String response = objectMapper.writeValueAsString(expected);
        mockMvc.perform(get("/api/bulletins/")
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(response));
    }

    @Test
    public void showBulletinDetailsTest() throws Exception {
        final long id = 4;
        final BulletinDto expected = BulletinDetailsMock.getById(id);
        final BulletinEntity entity = bulletinDtoEntityMapper.sourceToDestination(expected);

        willReturn(Optional.of(entity)).given(bulletinRepository).findById(id);
        final String response = objectMapper.writeValueAsString(expected);

        mockMvc.perform(get("/api/bulletins/" + id)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(response));
    }

    @Test
    public void createBulletinTest() throws Exception {
        final long id = 4L;
        final BulletinDto bulletinDto = BulletinDetailsMock.getById(id);
        final BulletinEntity entity = bulletinDtoEntityMapper.sourceToDestination(bulletinDto);
        final String request = objectMapper.writeValueAsString(bulletinDto);
        final String response = objectMapper.writeValueAsString(new IdDto(id));

        willReturn(entity).given(bulletinRepository).save(any(BulletinEntity.class));

        mockMvc.perform(post("/api/bulletins/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request))
            .andExpect(status().isCreated())
            .andExpect(content().json(response));
    }

    @Test
    public void updateBulletinTest() throws Exception {
        final long id = 4L;
        String token = signInAsUser(id);
        final BulletinDto bulletinDto = BulletinDetailsMock.getById(id);
        final String request = objectMapper.writeValueAsString(bulletinDto);
        final BulletinEntity entity = bulletinDtoEntityMapper.sourceToDestination(bulletinDto);
        willReturn(true).given(bulletinRepository).existsById(id);
        willReturn(entity).given(bulletinRepository).save(any(BulletinEntity.class));

        mockMvc.perform(put("/api/bulletins/" + id)
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(bulletinDto)));
    }

    @Test
    public void updateBulletinTest_BulletinNotFound() throws Exception {
        final long id = 4L;
        String token = signInAsUser(id);
        final BulletinDto bulletinDto = BulletinDetailsMock.getById(id);
        final String request = objectMapper.writeValueAsString(bulletinDto);
        final BulletinEntity entity = bulletinDtoEntityMapper.sourceToDestination(bulletinDto);
        willReturn(false).given(bulletinRepository).existsById(id);
        willReturn(entity).given(bulletinRepository).save(any(BulletinEntity.class));

        mockMvc.perform(put("/api/bulletins/" + id)
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request))
            .andExpect(status().isNotFound())
            .andExpect(content().json("{\"errorMessage\":\"Bulletin with such id " + id + " does not exist.\"}"));
    }

    @Test
    public void deleteBulletinTest() throws Exception {
        final long id = 4L;
        String token = signInAsUser(id);

        final BulletinDto bulletinDto = BulletinDetailsMock.getById(id);
        final BulletinEntity entity = bulletinDtoEntityMapper.sourceToDestination(bulletinDto);
        final UserEntity user = userDtoEntityMapper.sourceToDestination(UserMock.getById(id));
        final String password = UserMock.getById(id).getPassword();
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(UserRole.USER);
        entity.setSeller(user);

        willReturn(Optional.of(entity)).given(bulletinRepository).findById(id);
        willReturn(true).given(bulletinRepository).existsById(id);

        mockMvc.perform(delete("/api/bulletins/" + id)
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(bulletinRepository, times(1)).existsById(id);
        verify(bulletinRepository, times(1)).deleteById(id);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void deleteBulletinTest_AsAdmin() throws Exception {
        final long id = 4L;

        willReturn(true).given(bulletinRepository).existsById(id);

        mockMvc.perform(delete("/api/bulletins/" + id)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(bulletinRepository, times(1)).existsById(id);
        verify(bulletinRepository, times(1)).deleteById(id);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void deleteBulletinTest_BulletinNotFoundException() throws Exception {
        final long id = 4L;
        willReturn(false).given(bulletinRepository).existsById(id);

        mockMvc.perform(delete("/api/bulletins/" + id)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content().json("{\"errorMessage\":\"Bulletin with such id " + id + " does not exist.\"}"));

        verify(bulletinRepository, times(1)).existsById(id);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void deleteBulletinTest_BulletinNotFoundException_AsAdmin() throws Exception {
        final long id = 4L;
        String token = signInAsUser(id);

        final BulletinDto bulletinDto = BulletinDetailsMock.getById(id);
        final BulletinEntity entity = bulletinDtoEntityMapper.sourceToDestination(bulletinDto);
        final UserEntity user = userDtoEntityMapper.sourceToDestination(UserMock.getById(id));
        final String password = UserMock.getById(id).getPassword();
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(UserRole.USER);
        entity.setSeller(user);

        willReturn(Optional.of(entity)).given(bulletinRepository).findById(id);
        willReturn(false).given(bulletinRepository).existsById(id);

        mockMvc.perform(delete("/api/bulletins/" + id)
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content().json("{\"errorMessage\":\"Bulletin with such id " + id + " does not exist.\"}"));

        verify(bulletinRepository, times(1)).existsById(id);
    }
}

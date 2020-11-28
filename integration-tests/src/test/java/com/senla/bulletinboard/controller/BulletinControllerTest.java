package com.senla.bulletinboard.controller;

import com.senla.bulletinboard.dto.BulletinBaseDto;
import com.senla.bulletinboard.dto.BulletinDto;
import com.senla.bulletinboard.dto.IdDto;
import com.senla.bulletinboard.entity.BulletinEntity;
import com.senla.bulletinboard.mapper.interfaces.BulletinDtoEntityMapper;
import com.senla.bulletinboard.mock.BulletinDetailsMock;
import com.senla.bulletinboard.mock.BulletinMock;
import com.senla.bulletinboard.repository.BulletinRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
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

    @Test
    public void showBulletinsTest() throws Exception {
        final List<BulletinDto> expected = BulletinDetailsMock.getAll();
        final List<BulletinEntity> entities = expected
            .stream()
            .map(bulletinDtoEntityMapper::sourceToDestination)
            .collect(Collectors.toList());

        willReturn(entities).given(bulletinRepository).findAll();
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
        final BulletinDto bulletinDto = BulletinDetailsMock.getById(4L);
        final BulletinEntity entity = bulletinDtoEntityMapper.sourceToDestination(bulletinDto);
        final String request = objectMapper.writeValueAsString(bulletinDto);
        final String response = objectMapper.writeValueAsString(new IdDto(4L));

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
        final BulletinBaseDto bulletinDto = BulletinMock.getById(id);
        final String request = objectMapper.writeValueAsString(bulletinDto);

        mockMvc.perform(put("/api/bulletins/" + id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request))
            .andExpect(status().isOk());
    }

    @Test
    public void deleteBulletinTest() throws Exception {
        final long id = 4L;

        mockMvc.perform(delete("/api/bulletins/" + id)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
}

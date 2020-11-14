package com.senla.bulletin_board.controller;

import com.senla.bulletin_board.dto.BulletinDto;
import com.senla.bulletin_board.dto.BulletinBaseDto;
import com.senla.bulletin_board.dto.IdDto;
import com.senla.bulletin_board.entity.BulletinEntity;
import com.senla.bulletin_board.mapper.interfaces.BulletinDtoEntityMapper;
import com.senla.bulletin_board.mock.BulletinDetailsMock;
import com.senla.bulletin_board.mock.BulletinMock;
import com.senla.bulletin_board.repository.BulletinRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.stream.Collectors;

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

        final String response = objectMapper.writeValueAsString(expected);
        mockMvc.perform(get("/api/bulletins/" + id)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(response));
    }

    @Test
    public void createBulletinTest() throws Exception {
        final BulletinBaseDto bulletinDto = BulletinMock.getById(4L);
        final String request = objectMapper.writeValueAsString(bulletinDto);
        final String response = objectMapper.writeValueAsString(new IdDto(4L));

        mockMvc.perform(post("/api/bulletins/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request)
                       )
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
                            .content(request)
                       )
            .andExpect(status().isOk());
    }

    @Test
    public void deleteBulletinTest() throws Exception {
        final long id = 4L;

        mockMvc.perform(delete("/api/bulletins/" + id)
                            .contentType(MediaType.APPLICATION_JSON)
                       )
            .andExpect(status().isOk());
    }
}

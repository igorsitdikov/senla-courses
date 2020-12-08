package com.senla.bulletinboard.controller;

import com.senla.bulletinboard.dto.ApiErrorDto;
import com.senla.bulletinboard.dto.BulletinBaseDto;
import com.senla.bulletinboard.dto.BulletinDto;
import com.senla.bulletinboard.dto.IdDto;
import com.senla.bulletinboard.entity.BulletinEntity;
import com.senla.bulletinboard.enumerated.ExceptionType;
import com.senla.bulletinboard.mapper.interfaces.BulletinDetailsDtoEntityMapper;
import com.senla.bulletinboard.mock.BulletinMock;
import com.senla.bulletinboard.repository.BulletinRepository;
import com.senla.bulletinboard.repository.specification.BulletinFilterSortSpecification;
import com.senla.bulletinboard.utils.Translator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

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
    private BulletinDetailsDtoEntityMapper bulletinDetailsDtoEntityMapper;

    @Test
    public void showBulletinsTest() throws Exception {
        final List<BulletinBaseDto> expected = new BulletinMock().getAllBase()
            .stream()
            .peek(el -> el.setSeller(null))
            .collect(Collectors.toList());

        final List<BulletinEntity> entities = new BulletinMock().getAllEntities();

        willReturn(entities).given(bulletinRepository).findAll(any(BulletinFilterSortSpecification.class));
        final String response = objectMapper.writeValueAsString(expected);
        mockMvc.perform(get("/api/bulletins/")
                            .contextPath(CONTEXT_PATH)
                            .param("sort", "average")
                            .param("filter", "price_gte:20")
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(response));
    }

    @Test
    public void showBulletinDetailsTest() throws Exception {
        final Long id = 1L;
        final BulletinEntity entity = new BulletinMock().getEntityById(id);
        final BulletinDto expected = bulletinDetailsDtoEntityMapper.destinationToSource(entity);

        willReturn(Optional.of(entity)).given(bulletinRepository).findById(id);
        final String response = objectMapper.writeValueAsString(expected);

        mockMvc.perform(get("/api/bulletins/" + id)
                            .contextPath(CONTEXT_PATH)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(response));
    }

    @Test
    public void createBulletinTest() throws Exception {
        final Long bulletinId = 1L;
        final String token = signInAsUser(USER_PETR);
        final BulletinDto bulletinDto = new BulletinMock().getById(bulletinId);
        final BulletinEntity entity = new BulletinMock().getEntityById(bulletinId);
        final String request = objectMapper.writeValueAsString(bulletinDto);
        final String response = objectMapper.writeValueAsString(new IdDto(bulletinId));

        willReturn(entity).given(bulletinRepository).save(any(BulletinEntity.class));

        mockMvc.perform(post("/api/bulletins/")
                            .contextPath(CONTEXT_PATH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", token)
                            .content(request))
            .andExpect(status().isCreated())
            .andExpect(content().json(response));
    }

    @Test
    public void updateBulletinTest() throws Exception {
        final Long id = 1L;
        final String token = signInAsUser(USER_PETR);
        final BulletinEntity entity = new BulletinMock().getEntityById(id);
        final BulletinDto bulletinDto = bulletinDetailsDtoEntityMapper.destinationToSource(entity);
        final String request = objectMapper.writeValueAsString(bulletinDto);

        willReturn(Optional.of(entity)).given(bulletinRepository).findById(id);
        willReturn(entity).given(bulletinRepository).save(any(BulletinEntity.class));

        mockMvc.perform(put("/api/bulletins/" + id)
                            .contextPath(CONTEXT_PATH)
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(bulletinDto)));

        verify(bulletinRepository, times(1)).save(any(BulletinEntity.class));
    }

    @Test
    public void updateBulletinTest_BulletinNotFound() throws Exception {
        final Long id = 1L;
        final String token = signInAsUser(USER_PETR);
        final BulletinDto bulletinDto = new BulletinMock().getById(id);
        final String request = objectMapper.writeValueAsString(bulletinDto);
        willReturn(false).given(bulletinRepository).existsById(id);

        final String response = mockMvc.perform(put("/api/bulletins/" + id)
                                                    .contextPath(CONTEXT_PATH)
                                                    .header("Authorization", token)
                                                    .contentType(MediaType.APPLICATION_JSON)
                                                    .content(request))
            .andExpect(status().isNotFound())
            .andReturn().getResponse().getContentAsString();

        final String message = Translator.toLocale("bulletin-not-exists", id);
        final ApiErrorDto expectedError = expectedErrorCreator(
            HttpStatus.NOT_FOUND,
            ExceptionType.BUSINESS_LOGIC,
            message);

        assertErrorResponse(expectedError, response);
    }

    @Test
    public void deleteBulletinTest() throws Exception {
        final Long bulletinId = 1L;
        final String token = signInAsUser(USER_PETR);
        final BulletinEntity entity = new BulletinMock().getEntityById(bulletinId);

        willReturn(Optional.of(entity)).given(bulletinRepository).findById(bulletinId);
        willReturn(true).given(bulletinRepository).existsById(bulletinId);

        mockMvc.perform(delete("/api/bulletins/" + bulletinId)
                            .contextPath(CONTEXT_PATH)
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(bulletinRepository, times(1)).existsById(bulletinId);
        verify(bulletinRepository, times(1)).deleteById(bulletinId);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void deleteBulletinTest_AsAdmin() throws Exception {
        final Long bulletinId = 4L;
        willReturn(true).given(bulletinRepository).existsById(bulletinId);

        mockMvc.perform(delete("/api/bulletins/" + bulletinId)
                            .contextPath(CONTEXT_PATH)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(bulletinRepository, times(1)).existsById(bulletinId);
        verify(bulletinRepository, times(1)).deleteById(bulletinId);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void deleteBulletinTest_BulletinNotFoundException() throws Exception {
        final Long id = 4L;
        willReturn(false).given(bulletinRepository).existsById(id);

        final String response = mockMvc.perform(delete("/api/bulletins/" + id)
                                                    .contextPath(CONTEXT_PATH)
                                                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andReturn().getResponse().getContentAsString();

        final String message = Translator.toLocale("bulletin-not-exists", id);
        final ApiErrorDto expectedError = expectedErrorCreator(
            HttpStatus.NOT_FOUND,
            ExceptionType.BUSINESS_LOGIC,
            message);

        assertErrorResponse(expectedError, response);

        verify(bulletinRepository, times(1)).existsById(id);
    }

    @Test
    public void deleteBulletinTest_BulletinNotFoundException_AsAdmin() throws Exception {
        final Long id = 1L;
        final String token = signInAsUser(USER_PETR);
        final BulletinEntity bulletinEntity = new BulletinMock().getEntityById(id);

        willReturn(Optional.of(bulletinEntity)).given(bulletinRepository).findById(id);
        willReturn(false).given(bulletinRepository).existsById(id);

        final String response = mockMvc.perform(delete("/api/bulletins/" + id)
                                                    .contextPath(CONTEXT_PATH)
                                                    .header("Authorization", token)
                                                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andReturn().getResponse().getContentAsString();

        final String message = Translator.toLocale("bulletin-not-exists", id);
        final ApiErrorDto expectedError = expectedErrorCreator(
            HttpStatus.NOT_FOUND,
            ExceptionType.BUSINESS_LOGIC,
            message);

        assertErrorResponse(expectedError, response);

        verify(bulletinRepository, times(1)).existsById(id);
    }
}

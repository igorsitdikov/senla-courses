package com.senla.bulletin_board.controller;

import com.senla.bulletin_board.dto.PasswordDto;
import com.senla.bulletin_board.dto.UserDto;
import com.senla.bulletin_board.mock.UserMock;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends AbstractControllerTest {

    @Test
    public void testGetUser() throws Exception {
        final long id = 1L;
        final UserDto expected = UserMock.getUserDtoById(id);
        mockMvc.perform(get("/api/users/" + id)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void testUpdateUser() throws Exception {
        final long id = 1L;
        final UserDto expected = UserMock.getUserDtoById(id);

        mockMvc.perform(put("/api/users/" + id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(expected))
                       )
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void testChangePassword() throws Exception {
        final long id = 1L;
        PasswordDto password = new PasswordDto();
        password.setOldPassword("123456");
        password.setNewPassword("111111");
        password.setConfirmPassword("111111");

        mockMvc.perform(patch("/api/users/" + id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(password))
                       )
            .andExpect(status().isOk());
    }

}

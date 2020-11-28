package com.senla.bulletinboard.controller;

import com.senla.bulletinboard.dto.BulletinBaseDto;
import com.senla.bulletinboard.dto.DialogDto;
import com.senla.bulletinboard.dto.PasswordDto;
import com.senla.bulletinboard.dto.UserDto;
import com.senla.bulletinboard.mock.BulletinMock;
import com.senla.bulletinboard.mock.DialogMock;
import com.senla.bulletinboard.mock.UserMock;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

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
    public void testShowDialogs() throws Exception {
        final long id = 3L;
        final List<DialogDto> expected = DialogMock.getAll();
        mockMvc.perform(get("/api/users/" + id + "/dialogs")
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void testShowBulletins() throws Exception {
        final long id = 4L;
        final List<BulletinBaseDto> expected = BulletinMock.getAll();
        mockMvc.perform(get("/api/users/" + id + "/bulletins")
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

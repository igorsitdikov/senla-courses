package com.senla.bulletin_board.controller;

import com.senla.bulletin_board.dto.BulletinBaseDto;
import com.senla.bulletin_board.dto.DialogDto;
import com.senla.bulletin_board.dto.PasswordDto;
import com.senla.bulletin_board.dto.UserDto;
import com.senla.bulletin_board.mock.BulletinMock;
import com.senla.bulletin_board.mock.DialogMock;
import com.senla.bulletin_board.mock.UserMock;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUser(@PathVariable final Long id) {
        return UserMock.getUserDtoById(id);
    }

    @GetMapping(value = "/{id}/dialogs")
    @ResponseStatus(HttpStatus.OK)
    public List<DialogDto> showDialogs(@PathVariable final Long id) {
        return DialogMock.getAll();
    }

    @GetMapping(value = "/{id}/bulletins")
    @ResponseStatus(HttpStatus.OK)
    public List<BulletinBaseDto> showBulletins(@PathVariable final Long id) {
        return BulletinMock.getAll();
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto updateUser(@PathVariable final Long id, @RequestBody final UserDto request) {
        final UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setEmail(request.getEmail());
        userDto.setFirstName(request.getFirstName());
        userDto.setLastName(request.getLastName());
        userDto.setPhone(request.getPhone());
        return userDto;
    }

    @PatchMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@PathVariable final Long id, @RequestBody final PasswordDto passwordDto) {
        PasswordDto password = new PasswordDto();
        password.setOldPassword("123456");
        password.setNewPassword("111111");
        password.setConfirmPassword("111111");
    }
}

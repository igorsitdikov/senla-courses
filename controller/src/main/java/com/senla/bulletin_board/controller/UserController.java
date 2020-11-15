package com.senla.bulletin_board.controller;

import com.senla.bulletin_board.dto.BulletinBaseDto;
import com.senla.bulletin_board.dto.DialogDto;
import com.senla.bulletin_board.dto.PasswordDto;
import com.senla.bulletin_board.dto.UserDto;
import com.senla.bulletin_board.mock.BulletinMock;
import com.senla.bulletin_board.mock.DialogMock;
import com.senla.bulletin_board.mock.UserMock;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Data
@RestController
@RequestMapping(value = "/users")
public class UserController {

//    private final DialogService dialogService;

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUser(@PathVariable final Long id) {
        return UserMock.getUserDtoById(id);
    }

    @GetMapping(value = "/{id}/dialogs")
    @ResponseStatus(HttpStatus.OK)
    public List<DialogDto> showDialogs(@PathVariable final Long id) {
//        return dialogService.findAllDto();
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

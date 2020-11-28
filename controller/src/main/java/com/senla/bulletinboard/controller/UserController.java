package com.senla.bulletinboard.controller;

import com.senla.bulletinboard.dto.BulletinBaseDto;
import com.senla.bulletinboard.dto.DialogDto;
import com.senla.bulletinboard.dto.PasswordDto;
import com.senla.bulletinboard.dto.UserDto;
import com.senla.bulletinboard.exception.EntityNotFoundException;
import com.senla.bulletinboard.exception.NoSuchUserException;
import com.senla.bulletinboard.service.BulletinService;
import com.senla.bulletinboard.service.DialogService;
import com.senla.bulletinboard.service.UserService;
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

    private final DialogService dialogService;
    private final UserService userService;
    private final BulletinService bulletinService;

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUser(@PathVariable final Long id) throws EntityNotFoundException {
        return userService.findDtoById(id);
    }

    @GetMapping(value = "/{id}/dialogs")
    @ResponseStatus(HttpStatus.OK)
    public List<DialogDto> showDialogs(@PathVariable final Long id) {
        return dialogService.findDialogsByUserId(id);
    }

    @GetMapping(value = "/{id}/bulletins")
    @ResponseStatus(HttpStatus.OK)
    public List<BulletinBaseDto> showBulletins(@PathVariable final Long id) throws NoSuchUserException {
        return bulletinService.findBulletinsByUserId(id);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto updateUser(@PathVariable final Long id, @RequestBody final UserDto user) throws Exception {
        return userService.update(id, user);
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
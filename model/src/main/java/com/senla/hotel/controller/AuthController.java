package com.senla.hotel.controller;

import com.senla.hotel.dto.AuthRequestDto;
import com.senla.hotel.dto.UserDto;
import com.senla.hotel.dto.UserTokenDto;
import com.senla.hotel.exceptions.NoSuchUserException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.exceptions.SuchUserAlreadyExistException;
import com.senla.hotel.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
public class AuthController {

    private final AuthService authService;

    private AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public UserTokenDto singIn(@RequestBody final AuthRequestDto request) throws NoSuchUserException, PersistException {
        return authService.signIn(request);
    }

    @PostMapping(value = "/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public UserTokenDto singUp(
            @RequestBody final UserDto user)
            throws SuchUserAlreadyExistException, NoSuchUserException, PersistException {
        return authService.signUp(user);
    }
}

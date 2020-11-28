package com.senla.bulletinboard.controller;

import com.senla.bulletinboard.dto.SignInDto;
import com.senla.bulletinboard.dto.TokenDto;
import com.senla.bulletinboard.dto.UserRequestDto;
import com.senla.bulletinboard.exception.NoSuchUserException;
import com.senla.bulletinboard.exception.SuchUserAlreadyExistsException;
import com.senla.bulletinboard.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public TokenDto singIn(@RequestBody final SignInDto request) throws NoSuchUserException {
        return authService.signIn(request);
    }

    @PostMapping(value = "/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenDto singUp(@RequestBody final UserRequestDto user) throws SuchUserAlreadyExistsException {
        return authService.signUp(user);
    }
}

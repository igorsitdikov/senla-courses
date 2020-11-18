package com.senla.bulletin_board.controller;

import com.senla.bulletin_board.dto.SignInDto;
import com.senla.bulletin_board.dto.TokenDto;
import com.senla.bulletin_board.dto.UserRequestDto;
import com.senla.bulletin_board.exception.NoSuchUserException;
import com.senla.bulletin_board.exception.SuchUserAlreadyExistException;
import com.senla.bulletin_board.service.AuthService;
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
    public TokenDto singUp(@RequestBody final UserRequestDto user) throws SuchUserAlreadyExistException {
        return authService.signUp(user);
    }
}

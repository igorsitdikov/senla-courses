package com.senla.bulletinboard.controller;

import com.senla.bulletinboard.dto.SignInDto;
import com.senla.bulletinboard.dto.TokenDto;
import com.senla.bulletinboard.dto.UserRequestDto;
import com.senla.bulletinboard.exception.NoSuchUserException;
import com.senla.bulletinboard.exception.SuchUserAlreadyExistsException;
import com.senla.bulletinboard.service.interfaces.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@Validated
@Tag(name = "Authentication Management Controller")
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/sign-in")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Sign-in user", description = "Sign-in user")
    public TokenDto singIn(@RequestBody final SignInDto request) throws NoSuchUserException {
        return authService.signIn(request);
    }

    @PostMapping(value = "/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Sign-up user", description = "Sign-up user")
    public TokenDto singUp(@Valid @RequestBody final UserRequestDto user) throws SuchUserAlreadyExistsException {
        return authService.signUp(user);
    }

    @DeleteMapping(value = "/log-out")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Logout user", description = "Logout user")
    public void logout(@RequestHeader(name = "Authorization") final String token) {
        authService.logout(token);
    }
}

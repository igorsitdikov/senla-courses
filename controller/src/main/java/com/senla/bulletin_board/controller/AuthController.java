package com.senla.bulletin_board.controller;

import com.senla.bulletin_board.dto.SignInDto;
import com.senla.bulletin_board.dto.TokenDto;
import com.senla.bulletin_board.dto.UserRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class AuthController {

    @PostMapping(value = "/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public TokenDto singIn(@RequestBody final SignInDto request) {
        return new TokenDto("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4LmFsZXhlZXZvdkB5YW5kZXgucnUiLCJleHAiOjE1ODI2ODA3MjgsImlhdCI6MTU4MjY0NDcyOH0.oxNyf3jOPRoTuywoe2-oibyVxcisvOaPTWCaX56v9-0");
    }

    @PostMapping(value = "/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenDto singUp(@RequestBody final UserRequestDto user) {
        return new TokenDto("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4LmFsZXhlZXZvdkB5YW5kZXgucnUiLCJleHAiOjE1ODI2ODA3MjgsImlhdCI6MTU4MjY0NDcyOH0.oxNyf3jOPRoTuywoe2-oibyVxcisvOaPTWCaX56v9-0");
    }
}

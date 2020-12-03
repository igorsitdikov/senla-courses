package com.senla.bulletinboard.service.interfaces;

import com.senla.bulletinboard.dto.SignInDto;
import com.senla.bulletinboard.dto.TokenDto;
import com.senla.bulletinboard.dto.UserRequestDto;
import com.senla.bulletinboard.exception.NoSuchUserException;
import com.senla.bulletinboard.exception.SuchUserAlreadyExistsException;

public interface AuthService {

    TokenDto signUp(UserRequestDto userDto) throws SuchUserAlreadyExistsException;

    TokenDto signIn(SignInDto signInDto) throws NoSuchUserException;

    void logout(String token);
}

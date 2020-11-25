package com.senla.bulletin_board.service;

import com.senla.bulletin_board.dto.SignInDto;
import com.senla.bulletin_board.dto.TokenDto;
import com.senla.bulletin_board.dto.UserRequestDto;
import com.senla.bulletin_board.entity.UserEntity;
import com.senla.bulletin_board.exception.NoSuchUserException;
import com.senla.bulletin_board.exception.SuchUserAlreadyExistsException;
import com.senla.bulletin_board.mapper.interfaces.UserDtoEntityMapper;
import com.senla.bulletin_board.repository.UserRepository;
import com.senla.bulletin_board.security.JwtUtil;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Log4j2
@Data
@Service
public class AuthService {

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserDtoEntityMapper userDtoEntityMapper;

    public TokenDto signUp(final UserRequestDto userDto) throws SuchUserAlreadyExistsException {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            final String message = String.format("User with email %s already exists", userDto.getEmail());
            log.error(message);
            throw new SuchUserAlreadyExistsException(message);
        }
        final UserEntity userEntity = saveUserWithEncodedPassword(userDto);
        final User user = getUserDetails(userEntity);
        return new TokenDto(jwtUtil.generateToken(user));
    }

    private UserEntity saveUserWithEncodedPassword(final UserRequestDto userDto) {
        final UserEntity userEntity = userDtoEntityMapper.sourceToDestination(userDto);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userRepository.save(userEntity);
    }

    public TokenDto signIn(final SignInDto signInDto) throws NoSuchUserException {
        final UserEntity userEntity = userRepository
            .findByEmail(signInDto.getEmail())
            .orElseThrow(
                () -> {
                    final String message = String.format("No user with email = %s was found.", signInDto.getEmail());
                    log.error(message);
                    return new NoSuchUserException(message);
                });
        final User user = getUserDetails(userEntity);
        return new TokenDto(jwtUtil.generateToken(user));
    }

    private User getUserDetails(final UserEntity userEntity) {
        final String email = userEntity.getEmail();
        final String password = userEntity.getPassword();
        final List<SimpleGrantedAuthority> authorities =
            Collections.singletonList(new SimpleGrantedAuthority(userEntity.getRole().name()));
        return new User(email, password, authorities);
    }
}

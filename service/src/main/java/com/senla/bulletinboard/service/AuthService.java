package com.senla.bulletinboard.service;

import com.senla.bulletinboard.dto.SignInDto;
import com.senla.bulletinboard.dto.TokenDto;
import com.senla.bulletinboard.dto.UserRequestDto;
import com.senla.bulletinboard.entity.UserEntity;
import com.senla.bulletinboard.exception.NoSuchUserException;
import com.senla.bulletinboard.exception.SuchUserAlreadyExistsException;
import com.senla.bulletinboard.mapper.interfaces.UserDtoEntityMapper;
import com.senla.bulletinboard.repository.UserRepository;
import com.senla.bulletinboard.security.AuthUser;
import com.senla.bulletinboard.security.JwtUtil;
import com.senla.bulletinboard.utils.Translator;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Log4j2
@Data
@Service
@Transactional
public class AuthService {

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserDtoEntityMapper userDtoEntityMapper;

    public TokenDto signUp(final UserRequestDto userDto) throws SuchUserAlreadyExistsException {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            final String message = Translator.toLocale("user-already-exists", userDto.getEmail());
            log.error(message);
            throw new SuchUserAlreadyExistsException(message);
        }
        final UserEntity userEntity = saveUserWithEncodedPassword(userDto);
        final AuthUser user = getUserDetails(userEntity);
        return new TokenDto(jwtUtil.generateToken(user));
    }

    private UserEntity saveUserWithEncodedPassword(final UserRequestDto userDto) {
        final UserEntity userEntity = userDtoEntityMapper.sourceToDestination(userDto);
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userRepository.save(userEntity);
    }

    public TokenDto signIn(final SignInDto signInDto) throws NoSuchUserException {
        final UserEntity userEntity = userRepository
            .findByEmail(signInDto.getEmail())
            .orElseThrow(
                () -> {
                    final String message = Translator.toLocale("no-such-user", signInDto.getEmail());
                    log.error(message);
                    return new NoSuchUserException(message);
                });
        final AuthUser user = getUserDetails(userEntity);
        return new TokenDto(jwtUtil.generateToken(user));
    }

    private AuthUser getUserDetails(final UserEntity userEntity) {
        final Long id = userEntity.getId();
        final String email = userEntity.getEmail();
        final String password = userEntity.getPassword();
        final String role = userEntity.getRole().name();
        final List<SimpleGrantedAuthority> authorities =
            Collections.singletonList(new SimpleGrantedAuthority(role));
        return new AuthUser(email, password, authorities, id);
    }
}

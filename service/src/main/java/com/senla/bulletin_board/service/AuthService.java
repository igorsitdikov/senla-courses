package com.senla.bulletin_board.service;

import com.senla.bulletin_board.dto.SignInDto;
import com.senla.bulletin_board.dto.TokenDto;
import com.senla.bulletin_board.dto.UserRequestDto;
import com.senla.bulletin_board.entity.UserEntity;
import com.senla.bulletin_board.exception.NoSuchUserException;
import com.senla.bulletin_board.exception.SuchUserAlreadyExistException;
import com.senla.bulletin_board.mapper.interfaces.UserDtoEntityMapper;
import com.senla.bulletin_board.repository.UserRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class AuthService {

//    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserDtoEntityMapper userDtoEntityMapper;

    public TokenDto signUp(final UserRequestDto userDto) throws SuchUserAlreadyExistException {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new SuchUserAlreadyExistException(
                String.format("User with email %s already exists", userDto.getEmail()));
        }
        final UserEntity userEntity = saveUserWithEncodedPassword(userDto);
//        final User user = getUserDetails(userEntity);
        return new TokenDto(
            "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4LmFsZXhlZXZvdkB5YW5kZXgucnUiLCJleHAiOjE1ODI2ODA3MjgsImlhdCI6MTU4MjY0NDcyOH0.oxNyf3jOPRoTuywoe2-oibyVxcisvOaPTWCaX56v9-0");
    }

    private UserEntity saveUserWithEncodedPassword(final UserRequestDto userDto) {
        final UserEntity userEntity = userDtoEntityMapper.sourceToDestination(userDto);
//        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userRepository.save(userEntity);
    }

    public TokenDto signIn(final SignInDto signInDto) throws NoSuchUserException {
        final UserEntity userEntity = userRepository
            .findByEmail(signInDto.getEmail())
            .orElseThrow(
                () -> new NoSuchUserException(
                    String.format("No user with email = %s was found.", signInDto.getEmail())));
//        final User user = getUserDetails(userEntity);
        return new TokenDto(
            "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4LmFsZXhlZXZvdkB5YW5kZXgucnUiLCJleHAiOjE1ODI2ODA3MjgsImlhdCI6MTU4MjY0NDcyOH0.oxNyf3jOPRoTuywoe2-oibyVxcisvOaPTWCaX56v9-0");
    }

//    private User getUserDetails(final UserEntity userEntity) {
//        final String email = userEntity.getEmail();
//        final String password = userEntity.getPassword();
//        final List<SimpleGrantedAuthority> authorities =
//            List.of(new SimpleGrantedAuthority(userEntity.getRole().name()));
//        return new User(email, password, authorities);
//    }
}

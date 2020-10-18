package com.senla.hotel.service;

import com.senla.hotel.dao.interfaces.UserDao;
import com.senla.hotel.dto.AuthRequestDto;
import com.senla.hotel.dto.UserDto;
import com.senla.hotel.dto.UserTokenDto;
import com.senla.hotel.entity.UserEntity;
import com.senla.hotel.exceptions.NoSuchUserException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.exceptions.SuchUserAlreadyExistException;
import com.senla.hotel.mapper.interfaces.dtoMapper.UserDtoMapper;
import com.senla.hotel.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserDtoMapper userDtoMapper;

    public UserTokenDto signIn(final AuthRequestDto authRequestDto) throws NoSuchUserException {

        final UserEntity userEntity;
        try {
            userEntity = userDao
                    .findByEmail(authRequestDto.getEmail());
        } catch (PersistException e) {
            throw new NoSuchUserException(
                    String.format("No user with email = %s was found.", authRequestDto.getEmail()));
        }

        final UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(), authRequestDto.getPassword());
        authenticationManager.authenticate(authentication);

        final User user = getUserDetails(userEntity);
        return new UserTokenDto(jwtUtil.generateToken(user));
    }

    public UserTokenDto signUp(final UserDto user)
            throws SuchUserAlreadyExistException, NoSuchUserException, PersistException {
        try {
            UserEntity entity = userDao.findByEmail(user.getEmail());
            if (entity != null) {
                throw new SuchUserAlreadyExistException(
                        String.format("User with email %s already exists", user.getEmail()));
            }
        } catch (PersistException ignored) {

        }
        final UserEntity userEntity = userDtoMapper.sourceToDestination(user);
        userDao.create(userEntity);
        return signIn(new AuthRequestDto(user.getEmail(), user.getPassword()));
    }

    private User getUserDetails(final UserEntity userEntity) {
        final String email = userEntity.getEmail();
        final String password = userEntity.getPassword();
        final List<SimpleGrantedAuthority> authorities =
                Collections.singletonList(new SimpleGrantedAuthority(userEntity.getRole().name()));
        return new User(email, password, authorities);
    }

}

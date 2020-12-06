package com.senla.bulletinboard.service;

import com.senla.bulletinboard.dto.PasswordDto;
import com.senla.bulletinboard.dto.UserDto;
import com.senla.bulletinboard.entity.UserEntity;
import com.senla.bulletinboard.exception.EntityNotFoundException;
import com.senla.bulletinboard.exception.NoSuchUserException;
import com.senla.bulletinboard.mapper.interfaces.UserDtoEntityMapper;
import com.senla.bulletinboard.repository.UserRepository;
import com.senla.bulletinboard.service.interfaces.UserService;
import com.senla.bulletinboard.utils.Translator;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class UserServiceImpl extends AbstractService<UserDto, UserEntity, UserRepository> implements UserService {

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(final UserDtoEntityMapper dtoEntityMapper,
                           final UserRepository repository,
                           final PasswordEncoder passwordEncoder) {
        super(dtoEntityMapper, repository);
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @PreAuthorize("authentication.principal.id == #id")
    public UserDto findDtoById(final Long id) throws EntityNotFoundException {
        return super.findDtoById(id);
    }

    @Override
    @PreAuthorize("authentication.principal.id == #id")
    public UserDto updateUser(final Long id, final UserDto dto) throws NoSuchUserException {
        UserEntity user = repository.findById(id)
                .orElseThrow(() -> new NoSuchUserException(Translator.toLocale("no-such-user-id", id)));
        user.setAutoSubscribe(dto.getAutoSubscribe());
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhone(dto.getPhone());
        UserEntity save = repository.save(user);
        return dtoEntityMapper.destinationToSource(save);
    }

    @Override
    @PreAuthorize("authentication.principal.id == #id")
    public void changePassword(final Long id, final PasswordDto passwordDto) throws NoSuchUserException {
        UserEntity userEntity = repository.findById(id)
                .orElseThrow(() -> new NoSuchUserException(Translator.toLocale("no-such-user-id", id)));
        String userPassword = userEntity.getPassword();
        if (isPasswordsEquals(passwordDto) && isPasswordsMatches(passwordDto, userPassword)) {
            userEntity.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
            repository.save(userEntity);
        }
    }

    private boolean isPasswordsEquals(PasswordDto passwordDto) {
        return passwordDto.getConfirmPassword().equals(passwordDto.getNewPassword());
    }

    private boolean isPasswordsMatches(PasswordDto passwordDto, String userPassword) {
        return passwordEncoder.matches(passwordDto.getOldPassword(), userPassword);
    }
}

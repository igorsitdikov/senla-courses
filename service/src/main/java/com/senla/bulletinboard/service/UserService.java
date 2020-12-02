package com.senla.bulletinboard.service;

import com.senla.bulletinboard.dto.PasswordDto;
import com.senla.bulletinboard.dto.UserDto;
import com.senla.bulletinboard.entity.UserEntity;
import com.senla.bulletinboard.exception.NoSuchUserException;
import com.senla.bulletinboard.mapper.interfaces.UserDtoEntityMapper;
import com.senla.bulletinboard.repository.UserRepository;
import com.senla.bulletinboard.utils.Translator;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractService<UserDto, UserEntity, UserRepository> {

    private final PasswordEncoder passwordEncoder;

    public UserService(final UserDtoEntityMapper dtoEntityMapper,
                       final UserRepository repository,
                       final PasswordEncoder passwordEncoder) {
        super(dtoEntityMapper, repository);
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @PreAuthorize("authentication.principal.id == #id")
    public UserDto update(final Long id, final UserDto dto) {
        return super.update(id, dto);
    }

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

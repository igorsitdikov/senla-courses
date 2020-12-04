package com.senla.bulletinboard.service.interfaces;

import com.senla.bulletinboard.dto.PasswordDto;
import com.senla.bulletinboard.dto.UserDto;
import com.senla.bulletinboard.entity.UserEntity;
import com.senla.bulletinboard.exception.NoSuchUserException;
import org.springframework.security.access.prepost.PreAuthorize;

public interface UserService extends CommonService<UserDto, UserEntity> {

    @PreAuthorize("authentication.principal.id == #id")
    UserDto updateUser(Long id, UserDto dto) throws NoSuchUserException;

    @PreAuthorize("authentication.principal.id == #id")
    void changePassword(Long id, PasswordDto passwordDto) throws NoSuchUserException;
}

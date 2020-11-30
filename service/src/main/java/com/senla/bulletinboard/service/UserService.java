package com.senla.bulletinboard.service;

import com.senla.bulletinboard.dto.UserDto;
import com.senla.bulletinboard.entity.UserEntity;
import com.senla.bulletinboard.mapper.interfaces.UserDtoEntityMapper;
import com.senla.bulletinboard.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractService<UserDto, UserEntity, UserRepository> {

    public UserService(final UserDtoEntityMapper dtoEntityMapper,
                       final UserRepository repository) {
        super(dtoEntityMapper, repository);
    }

    @Override
    @PreAuthorize("authentication.principal.id == #id")
    public UserDto update(final Long id, final UserDto dto) {
        return super.update(id, dto);
    }
}

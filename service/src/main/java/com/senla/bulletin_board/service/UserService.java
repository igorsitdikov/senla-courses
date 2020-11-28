package com.senla.bulletin_board.service;

import com.senla.bulletin_board.dto.UserDto;
import com.senla.bulletin_board.entity.UserEntity;
import com.senla.bulletin_board.mapper.interfaces.UserDtoEntityMapper;
import com.senla.bulletin_board.repository.UserRepository;
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

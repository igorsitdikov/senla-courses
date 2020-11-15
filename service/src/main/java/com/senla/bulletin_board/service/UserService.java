package com.senla.bulletin_board.service;

import com.senla.bulletin_board.dto.UserDto;
import com.senla.bulletin_board.entity.UserEntity;
import com.senla.bulletin_board.mapper.interfaces.UserDtoEntityMapper;
import com.senla.bulletin_board.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractService<UserDto, UserEntity, UserRepository> {

    private UserService(final UserDtoEntityMapper dtoEntityMapper,
                        final UserRepository repository) {
        super(dtoEntityMapper, repository);
    }
}

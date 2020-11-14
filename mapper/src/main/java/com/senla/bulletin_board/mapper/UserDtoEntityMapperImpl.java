package com.senla.bulletin_board.mapper;

import com.senla.bulletin_board.dto.UserDto;
import com.senla.bulletin_board.entity.UserEntity;
import com.senla.bulletin_board.mapper.interfaces.UserDtoEntityMapper;
import org.springframework.stereotype.Component;

@Component
public class UserDtoEntityMapperImpl implements UserDtoEntityMapper {

    @Override
    public UserEntity sourceToDestination(final UserDto source) {
        UserEntity destination = new UserEntity();
        destination.setId(source.getId());
        destination.setFirstName(source.getFirstName());
        destination.setLastName(source.getLastName());
        destination.setEmail(source.getEmail());
        destination.setPhone(source.getPhone());
        return destination;
    }

    @Override
    public UserDto destinationToSource(final UserEntity destination) {
        UserDto source = new UserDto();
        source.setId(destination.getId());
        source.setFirstName(destination.getFirstName());
        source.setLastName(destination.getLastName());
        source.setEmail(destination.getEmail());
        source.setPhone(destination.getPhone());
        return source;
    }
}

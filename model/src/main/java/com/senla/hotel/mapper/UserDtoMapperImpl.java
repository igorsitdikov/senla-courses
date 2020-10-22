package com.senla.hotel.mapper;

import com.senla.hotel.dto.UserDto;
import com.senla.hotel.entity.UserEntity;
import com.senla.hotel.enumerated.UserRole;
import com.senla.hotel.mapper.interfaces.dtoMapper.UserDtoMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapperImpl implements UserDtoMapper {

    private final PasswordEncoder passwordEncoder;

    public UserDtoMapperImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserEntity sourceToDestination(UserDto source) {
        UserEntity destination = new UserEntity();
        destination.setFirstName(source.getFirstName());
        destination.setLastName(source.getLastName());
        destination.setEmail(source.getEmail());
        destination.setPhone(source.getPhone());
        if (source.getRole() == null) {
            destination.setRole(UserRole.CUSTOMER);
        } else {
            destination.setRole(source.getRole());
        }
        destination.setPassword(passwordEncoder.encode(source.getPassword()));
        return destination;
    }

    @Override
    public UserDto destinationToSource(UserEntity destination) {
        UserDto source = new UserDto();
        source.setFirstName(destination.getFirstName());
        source.setLastName(destination.getLastName());
        source.setEmail(destination.getEmail());
        source.setId(destination.getId());
        source.setPhone(destination.getPhone());
        return source;
    }
}

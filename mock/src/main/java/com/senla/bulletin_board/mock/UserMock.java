package com.senla.bulletin_board.mock;

import com.senla.bulletin_board.dto.UserDto;
import com.senla.bulletin_board.dto.UserRequestDto;

import java.util.HashMap;
import java.util.Map;

public class UserMock {
    private static final Map<Long, UserRequestDto> userMap = new HashMap<Long, UserRequestDto>() {{
        put(1L, UserRequestDto.builder()
            .id(1L)
            .firstName("Иван")
            .secondName("Иванов")
            .email("ivan.ivanov@mail.ru")
            .password("123456")
            .phone("+375331234567")
            .build());
        put(2L, UserRequestDto.builder()
            .id(2L)
            .firstName("Петр")
            .secondName("Петров")
            .email("petr.petrov@yandex.ru")
            .password("654321")
            .phone("+375337654321")
            .build());
        put(3L, UserRequestDto.builder()
            .id(3L)
            .firstName("Алексей")
            .secondName("Алексеев")
            .email("alex.alexeev@gmail.com")
            .password("password")
            .phone("+375337654321")
            .build());
        put(4L, UserRequestDto.builder()
            .id(4L)
            .firstName("Антон")
            .secondName("Антонов")
            .email("anton.antonov@mail.ru")
            .password("anton")
            .phone("+375331234567")
            .build());
    }};


    public static UserRequestDto getById(final Long id) {
        return userMap.get(id);
    }

    public static UserDto getUserDtoById(final Long id) {
        final UserRequestDto userRequestDto = userMap.get(id);
        UserDto userDto = new UserDto();
        userDto.setId(userRequestDto.getId());
        userDto.setEmail(userRequestDto.getEmail());
        userDto.setFirstName(userRequestDto.getFirstName());
        userDto.setSecondName(userRequestDto.getSecondName());
        userDto.setPhone(userRequestDto.getPhone());
        return userDto;
    }
}

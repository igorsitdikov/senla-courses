package com.senla.bulletin_board.mock;

import com.senla.bulletin_board.dto.UserDto;
import com.senla.bulletin_board.dto.UserRequestDto;

import java.util.HashMap;
import java.util.Map;

public class UserMock {

    private static final Map<Long, UserRequestDto> userMap = new HashMap<Long, UserRequestDto>() {{
        put(1L, new UserRequestDto(1L, "Иван", "Иванов", "ivan.ivanov@mail.ru", "+375331234567", "123456"));
        put(2L, new UserRequestDto(2L, "Петр", "Петров", "petr.petrov@yandex.ru", "+375337654321", "654321"));
        put(3L, new UserRequestDto(3L, "Алексей", "Алексеев", "alex.alexeev@gmail.com", "+375337654321", "password"));
        put(4L, new UserRequestDto(4L, "Антон", "Антонов", "anton.antonov@mail.ru", "+375331234567", "anton"));
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
        userDto.setLastName(userRequestDto.getLastName());
        userDto.setPhone(userRequestDto.getPhone());
        return userDto;
    }
}

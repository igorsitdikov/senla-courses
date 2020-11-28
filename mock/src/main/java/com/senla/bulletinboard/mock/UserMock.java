package com.senla.bulletinboard.mock;

import com.senla.bulletinboard.dto.UserDto;
import com.senla.bulletinboard.dto.UserRequestDto;
import com.senla.bulletinboard.enumerated.AutoSubscribeStatus;
import com.senla.bulletinboard.enumerated.PremiumStatus;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class UserMock {

    private static final Map<Long, UserRequestDto> userMap = new HashMap<Long, UserRequestDto>() {{
        put(1L, new UserRequestDto(1L, "Иван", "Иванов", "ivan.ivanov@mail.ru", "+375331234567",
                                   AutoSubscribeStatus.ACTIVE, PremiumStatus.ACTIVE, BigDecimal.ZERO, "123456"));
        put(2L, new UserRequestDto(2L, "Петр", "Петров", "petr.petrov@yandex.ru", "+375337654321",
                                   AutoSubscribeStatus.ACTIVE, PremiumStatus.ACTIVE, BigDecimal.ZERO, "654321"));
        put(3L, new UserRequestDto(3L, "Алексей", "Алексеев", "alex.alexeev@gmail.com", "+375337654321",
                                   AutoSubscribeStatus.ACTIVE, PremiumStatus.ACTIVE, BigDecimal.ZERO, "password"));
        put(4L, new UserRequestDto(4L, "Антон", "Антонов", "anton.antonov@mail.ru", "+375331234567",
                                   AutoSubscribeStatus.ACTIVE, PremiumStatus.ACTIVE, BigDecimal.ZERO, "anton"));
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

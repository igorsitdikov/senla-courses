package com.senla.bulletinboard.mock;

import com.senla.bulletinboard.dto.UserDto;
import com.senla.bulletinboard.dto.UserRequestDto;
import com.senla.bulletinboard.entity.UserEntity;
import com.senla.bulletinboard.enumerated.AutoSubscribeStatus;
import com.senla.bulletinboard.enumerated.PremiumStatus;
import com.senla.bulletinboard.enumerated.UserRole;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class UserMock {

    private static final Map<Long, UserRequestDto> userMap = new HashMap<Long, UserRequestDto>() {{
        put(1L, new UserRequestDto(1L, "Иван", "Иванов", "ivan.ivanov@mail.ru", "+375331234567", UserRole.USER,
                AutoSubscribeStatus.ACTIVE, PremiumStatus.ACTIVE, BigDecimal.ZERO, "123456"));
        put(2L, new UserRequestDto(2L, "Петр", "Петров", "petr.petrov@yandex.ru", "+375337654321", UserRole.USER,
                AutoSubscribeStatus.ACTIVE, PremiumStatus.ACTIVE, BigDecimal.ZERO, "654321"));
        put(3L, new UserRequestDto(3L, "Алексей", "Алексеев", "alex.alexeev@gmail.com", "+375337654321", UserRole.ADMIN,
                AutoSubscribeStatus.ACTIVE, PremiumStatus.ACTIVE, BigDecimal.ZERO, "password"));
        put(4L, new UserRequestDto(4L, "Антон", "Антонов", "anton.antonov@mail.ru", "+375331234567", UserRole.USER,
                AutoSubscribeStatus.ACTIVE, PremiumStatus.ACTIVE, BigDecimal.ZERO, "anton"));
    }};
    private static final Map<Long, UserEntity> userEntityMap = new HashMap<Long, UserEntity>() {{
        put(1L, new UserEntity(
                "Иван",
                "Иванов",
                "ivan.ivanov@mail.ru",
                "$2a$10$wbfi9Zi4pfkC0Y3Mp7r.fOc8po9kqc0T5.nMPTq65D/qgdKBdKAre",
                "+375331234567",
                UserRole.USER,
                PremiumStatus.ACTIVE,
                AutoSubscribeStatus.ACTIVE,
                BigDecimal.ZERO));
        put(2L, new UserEntity(
                "Петр",
                "Петров",
                "petr.petrov@yandex.ru",
                "$2a$10$VvYBVKbr4xafUsgWdEHsxuOTnBhTFdzY9f1nm9p1j6x5j8TQhNZX6",
                "+375337654321",
                UserRole.USER,
                PremiumStatus.ACTIVE,
                AutoSubscribeStatus.ACTIVE,
                BigDecimal.ZERO));
        put(3L, new UserEntity(
                "Алексей",
                "Алексеев",
                "alex.alexeev@gmail.com",
                "$2a$10$A4HOZGs7d65Nqd1Wz2T/kO8Zt4GZOI6CTn/Vo0Ug3OLjJsVGafwM.",
                "+375337654321",
                UserRole.ADMIN,
                PremiumStatus.ACTIVE,
                AutoSubscribeStatus.ACTIVE,
                BigDecimal.ZERO));
        put(4L, new UserEntity(
                "Антон",
                "Антонов",
                "anton.antonov@mail.ru",
                "$2a$10$uq8o9KxY.sr2l9FRy0kj1uRbDklhj3qWyXT4Mlr.9rmBZlZb115r6",
                "+375331234567",
                UserRole.USER,
                PremiumStatus.ACTIVE,
                AutoSubscribeStatus.ACTIVE,
                BigDecimal.ZERO));
    }};

    public static UserEntity getEntityById(final Long id) {
        UserEntity userEntity = userEntityMap.get(id);
        userEntity.setId(id);
        return userEntity;
    }

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

package com.senla.bulletinboard.mock;

import com.senla.bulletinboard.dto.UserDto;
import com.senla.bulletinboard.dto.UserRequestDto;
import com.senla.bulletinboard.entity.UserEntity;
import com.senla.bulletinboard.enumerated.AutoSubscribeStatus;
import com.senla.bulletinboard.enumerated.PremiumStatus;
import com.senla.bulletinboard.enumerated.UserRole;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserMock {

    private final Map<Long, UserRequestDto> userMap = new HashMap<Long, UserRequestDto>() {{
        put(1L, new UserRequestDto(1L, "Иван", "Иванов", "ivan.ivanov@mail.ru", "+375331234567", UserRole.USER,
                                   AutoSubscribeStatus.ACTIVE, PremiumStatus.ACTIVE, BigDecimal.valueOf(13), "123456"));
        put(2L, new UserRequestDto(2L, "Петр", "Петров", "petr.petrov@yandex.ru", "+375337654321", UserRole.USER,
                                   AutoSubscribeStatus.ACTIVE, PremiumStatus.ACTIVE, BigDecimal.ZERO, "654321"));
        put(3L, new UserRequestDto(3L, "Алексей", "Алексеев", "alex.alexeev@gmail.com", "+375337654321", UserRole.ADMIN,
                                   AutoSubscribeStatus.ACTIVE, PremiumStatus.ACTIVE, BigDecimal.ZERO, "password"));
        put(4L, new UserRequestDto(4L, "Антон", "Антонов", "anton.antonov@mail.ru", "+375331234567", UserRole.USER,
                                   AutoSubscribeStatus.ACTIVE, PremiumStatus.ACTIVE, BigDecimal.ZERO, "anton12"));
    }};

    private final Map<Long, UserEntity> userEntityMap = new HashMap<Long, UserEntity>() {{
        put(1L, new UserEntity(
            "Иван",
            "Иванов",
            "ivan.ivanov@mail.ru",
            "$2a$10$wbfi9Zi4pfkC0Y3Mp7r.fOc8po9kqc0T5.nMPTq65D/qgdKBdKAre",
            "+375331234567",
            UserRole.USER,
            PremiumStatus.ACTIVE,
            AutoSubscribeStatus.ACTIVE,
            BigDecimal.valueOf(13)));
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
            "$2a$10$fSasHsC8d2P/H0RBO9TW8.lTWCd/yF9zWEobY4y8WzL8Vy6SaqYxm",
            "+375331234567",
            UserRole.USER,
            PremiumStatus.ACTIVE,
            AutoSubscribeStatus.ACTIVE,
            BigDecimal.ZERO));
    }};

    public UserEntity getEntityById(final Long id) {
        final UserEntity userEntity = userEntityMap.get(id);
        userEntity.setId(id);
        return userEntity;
    }

    public List<UserDto> findAllDto() {
        return new ArrayList<>(userMap.values());
    }

    public List<UserEntity> findAllEntities() {
        return userEntityMap
            .entrySet()
            .stream()
            .map(user -> {
                UserEntity userEntity = user.getValue();
                userEntity.setId(user.getKey());
                return userEntity;
            })
            .collect(Collectors.toList());
    }

    public UserRequestDto getById(final Long id) {
        return userMap.get(id);
    }

    public UserDto getUserDtoById(final Long id) {
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

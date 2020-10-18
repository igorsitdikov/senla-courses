package mock;

import com.senla.hotel.entity.UserEntity;
import com.senla.hotel.enumerated.UserRole;

import java.util.HashMap;
import java.util.Map;

public class UsersMock {

    private static final Map<Long, UserEntity> userMap = new HashMap<Long, UserEntity>() {{
        put(1L,
            new UserEntity("Петр", "Петров", "petr.petrov@yandex.ru", "654321", "+375337654321", UserRole.CUSTOMER));
        put(2L, new UserEntity("Алексей", "Алексеев", "alex.alexeev@gmail.com", "password", "+375337654321",
                               UserRole.ADMIN));
    }};

    public static UserEntity getById(final Long id) {
        return userMap.get(id);
    }
}

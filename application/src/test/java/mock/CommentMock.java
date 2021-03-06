package mock;

import com.senla.bulletinboard.dto.CommentDto;
import com.senla.bulletinboard.entity.BulletinEntity;
import com.senla.bulletinboard.entity.CommentEntity;
import com.senla.bulletinboard.entity.UserEntity;
import com.senla.bulletinboard.enumerated.AutoSubscribeStatus;
import com.senla.bulletinboard.enumerated.BulletinStatus;
import com.senla.bulletinboard.enumerated.PremiumStatus;
import com.senla.bulletinboard.enumerated.UserRole;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class CommentMock {

    private final Map<Long, CommentDto> commentMap = new HashMap<Long, CommentDto>() {{
        put(1L, new CommentDto(
            1L,
                new UserMock().getUserDtoById(2L),
            2L,
            "Отличный картофель",
            4L,
            LocalDateTime.parse("2020-09-14T11:30:45")));
        put(2L, new CommentDto(
            1L,
                new UserMock().getUserDtoById(3L),
            4L,
            "Отличный картофель",
            3L,
            LocalDateTime.parse("2020-09-14T11:30:45")));
    }};

    private final Map<Long, CommentEntity> commentEntityMap = new HashMap<Long, CommentEntity>() {{
        put(1L, new CommentEntity(
            LocalDateTime.parse("2020-09-14T11:30:45"),
            "Отличный картофель",
            new BulletinEntity(
                LocalDateTime.parse("2020-09-12T12:00:32"),
                "Продам отборный картофель, сорт «Вектор»",
                BigDecimal.valueOf(34.12),
                "БЕСПЛАТНАЯ доставка по г.Пружаны и району.",
                BulletinStatus.OPEN,
                new HashSet<>(),
                    new UserMock().getEntityById(1L),
                1L,
                new HashSet<>()),
            4L,
            new UserEntity(
                "Петр",
                "Петров",
                "petr.petrov@yandex.ru",
                "$2a$10$VvYBVKbr4xafUsgWdEHsxuOTnBhTFdzY9f1nm9p1j6x5j8TQhNZX6",
                "+375337654321",
                UserRole.USER,
                PremiumStatus.ACTIVE,
                AutoSubscribeStatus.ACTIVE,
                BigDecimal.ZERO, 0.0),
            2L));
        put(2L, new CommentEntity(
            LocalDateTime.parse("2020-09-14T11:30:45"),
            "Отличный картофель",
            new BulletinEntity(
                LocalDateTime.parse("2020-09-12T12:00:32"),
                "Продам хомяка",
                BigDecimal.valueOf(12),
                "",
                BulletinStatus.CLOSE,
                new HashSet<>(),
                    new UserMock().getEntityById(4L),
                4L,
                new HashSet<>()),
            3L,
            new UserEntity(
                "Антон",
                "Антонов",
                "anton.antonov@mail.ru",
                "$2a$10$8Gdmt4V0qD5sdtafZZUkgO04l7WGpMgmeiYaU9lqUXx0fZ0gCyesC",
                "+375331234567",
                UserRole.USER,
                PremiumStatus.ACTIVE,
                AutoSubscribeStatus.ACTIVE,
                BigDecimal.ZERO, 0.0),
            4L));
    }};

    public CommentEntity getEntityById(final Long id) {
        final CommentEntity commentEntity = commentEntityMap.get(id);
        commentEntity.setId(id);
        return commentEntity;
    }

    public CommentDto getById(final Long id) {
        return commentMap.get(id);
    }

    public List<CommentDto> getAll() {
        return new ArrayList<>(commentMap.values());
    }
}

package com.senla.bulletinboard.mock;

import com.senla.bulletinboard.dto.DialogDto;
import com.senla.bulletinboard.entity.BulletinEntity;
import com.senla.bulletinboard.entity.DialogEntity;
import com.senla.bulletinboard.entity.UserEntity;
import com.senla.bulletinboard.enumerated.AutoSubscribeStatus;
import com.senla.bulletinboard.enumerated.BulletinStatus;
import com.senla.bulletinboard.enumerated.PremiumStatus;
import com.senla.bulletinboard.enumerated.UserRole;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public class DialogMock {

    private static final Map<Long, DialogDto> dialogMap = new HashMap<Long, DialogDto>() {{
        put(1L, new DialogDto(
                1L,
                "Продам отборный картофель, сорт «Вектор»",
                1L,
                2L,
                4L,
                UserMock.getUserDtoById(2L),
                BulletinMock.getById(4L),
                LocalDateTime.parse("2020-09-12T12:00:32")));
    }};

    private static final Map<Long, DialogEntity> dialogEntityMap = new HashMap<Long, DialogEntity>() {{
        put(1L, new DialogEntity(
                new BulletinEntity(
                        LocalDateTime.parse("2020-09-12T12:00:32"),
                        "Продам отборный картофель, сорт «Вектор»",
                        BigDecimal.valueOf(34.12),
                        "БЕСПЛАТНАЯ доставка по г.Пружаны и району.",
                        BulletinStatus.OPEN,
                        new HashSet<>(),
                        UserMock.getEntityById(1L),
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
                        BigDecimal.ZERO),
                2L,
                LocalDateTime.parse("2020-09-12T12:00:32")));
    }};

    public static DialogEntity getEntityById(final Long id) {
        DialogEntity dialogEntity = dialogEntityMap.get(id);
        dialogEntity.setId(id);
        return dialogEntity;
    }

    public static DialogDto getById(final Long id) {
        return dialogMap.get(id);
    }

    public static List<DialogDto> getAll() {
        return new ArrayList<>(dialogMap.values());
    }
}

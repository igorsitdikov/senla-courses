package com.senla.bulletinboard.mock;

import com.senla.bulletinboard.dto.BulletinBaseDto;
import com.senla.bulletinboard.dto.BulletinDto;
import com.senla.bulletinboard.entity.BulletinEntity;
import com.senla.bulletinboard.enumerated.BulletinStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class BulletinMock {

    private static final Map<Long, BulletinDto> bulletinMap = new HashMap<Long, BulletinDto>() {{
        put(1L, new BulletinDto(
                1L,
                "Продам свадебный сервиз",
                BigDecimal.valueOf(34.12),
                LocalDateTime.parse("2020-09-12T12:00:32"),
                UserMock.getUserDtoById(2L),
                2L));
        put(2L, new BulletinDto(
                2L,
                "Продам соковыжималку \"Журавинка\" СВСП 102П",
                BigDecimal.valueOf(25),
                LocalDateTime.parse("2020-09-12T12:00:32"),
                UserMock.getUserDtoById(3L),
                3L));
        put(3L, new BulletinDto(
                3L,
                "Продам хомяка",
                BigDecimal.valueOf(12),
                LocalDateTime.parse("2020-09-12T12:00:32"),
                UserMock.getUserDtoById(4L),
                4L));
        put(4L, new BulletinDto(
                4L,
                "Продам отборный картофель, сорт «Вектор»",
                BigDecimal.valueOf(34.12),
                LocalDateTime.parse("2020-09-12T12:00:32"),
                UserMock.getUserDtoById(1L),
                1L,
                "БЕСПЛАТНАЯ доставка по г.Пружаны и району.",
                CommentMock.getAll(),
                BulletinStatus.OPEN));
    }};
    private static final Map<Long, BulletinBaseDto> bulletinBaseMap = new HashMap<Long, BulletinBaseDto>() {{
        put(1L, new BulletinBaseDto(
                1L,
                "Продам свадебный сервиз",
                BigDecimal.valueOf(34.12),
                LocalDateTime.parse("2020-09-12T12:00:32"),
                UserMock.getUserDtoById(2L),
                2L));
        put(2L, new BulletinBaseDto(
                2L,
                "Продам соковыжималку \"Журавинка\" СВСП 102П",
                BigDecimal.valueOf(25),
                LocalDateTime.parse("2020-09-12T12:00:32"),
                UserMock.getUserDtoById(3L),
                3L));
        put(3L, new BulletinBaseDto(
                3L,
                "Продам хомяка",
                BigDecimal.valueOf(12),
                LocalDateTime.parse("2020-09-12T12:00:32"),
                UserMock.getUserDtoById(4L),
                4L));
        put(4L, new BulletinBaseDto(
                4L,
                "Продам отборный картофель, сорт «Вектор»",
                BigDecimal.valueOf(34.12),
                LocalDateTime.parse("2020-09-12T12:00:32"),
                UserMock.getUserDtoById(1L),
                1L));
    }};

    private static final Map<Long, BulletinEntity> bulletinEntityMap = new HashMap<Long, BulletinEntity>() {{
        put(1L, new BulletinEntity(
                LocalDateTime.parse("2020-09-12T12:00:32"),
                "Продам свадебный сервиз",
                BigDecimal.valueOf(34.12),
                "",
                BulletinStatus.OPEN,
                new HashSet<>(),
                UserMock.getEntityById(2L),
                2L,
                new HashSet<>()));
        put(2L, new BulletinEntity(
                LocalDateTime.parse("2020-09-12T12:00:32"),
                "Продам соковыжималку \"Журавинка\" СВСП 102П",
                BigDecimal.valueOf(25),
                "Соковыжималка",
                BulletinStatus.OPEN,
                new HashSet<>(),
                UserMock.getEntityById(3L),
                3L,
                new HashSet<>()));
        put(3L, new BulletinEntity(
                LocalDateTime.parse("2020-09-12T12:00:32"),
                "Продам хомяка",
                BigDecimal.valueOf(12),
                "",
                BulletinStatus.CLOSE,
                new HashSet<>(),
                UserMock.getEntityById(4L),
                4L,
                new HashSet<>()));
        put(4L, new BulletinEntity(
                LocalDateTime.parse("2020-09-12T12:00:32"),
                "Продам отборный картофель, сорт «Вектор»",
                BigDecimal.valueOf(34.12),
                "БЕСПЛАТНАЯ доставка по г.Пружаны и району.",
                BulletinStatus.OPEN,
                new HashSet<>(),
                UserMock.getEntityById(1L),
                1L,
                new HashSet<>()));
    }};

    public static BulletinEntity getEntityById(final Long id) {
        BulletinEntity bulletinEntity = bulletinEntityMap.get(id);
        bulletinEntity.setId(id);
        return bulletinEntity;
    }

    public static BulletinBaseDto getBaseById(final Long id) {
        return bulletinBaseMap.get(id);
    }

    public static List<BulletinBaseDto> getAllBase() {
        return new ArrayList<>(bulletinBaseMap.values());
    }

    public static List<BulletinEntity> getAllEntities() {
        return bulletinEntityMap
                .entrySet()
                .stream()
                .map(entry -> {
                    BulletinEntity bulletinEntity = entry.getValue();
                    bulletinEntity.setId(entry.getKey());
                    return bulletinEntity;
                }).collect(Collectors.toList());
    }

    public static BulletinDto getById(final Long id) {
        return bulletinMap.get(id);
    }

    public static List<BulletinDto> getAll() {
        return new ArrayList<>(bulletinMap.values());
    }

}

package com.senla.bulletinboard.mock;

import com.senla.bulletinboard.dto.BulletinBaseDto;
import com.senla.bulletinboard.dto.BulletinDto;
import com.senla.bulletinboard.enumerated.BulletinStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BulletinMock {

    private static final Map<Long, BulletinDto> bulletinMap = new HashMap<Long, BulletinDto>() {{
        put(1L, new BulletinDto(1L, "Продам свадебный сервиз", BigDecimal.valueOf(34.12),
                                LocalDateTime.parse("2020-09-12T12:00:32"), UserMock.getUserDtoById(1L), 1L));
        put(2L, new BulletinDto(2L, "Продам соковыжималку \"Журавинка\" СВСП 102П", BigDecimal.valueOf(25),
                                LocalDateTime.parse("2020-09-12T12:00:32"), UserMock.getUserDtoById(2L), 2L));
        put(3L, new BulletinDto(3L, "Продам хомяка", BigDecimal.valueOf(12), LocalDateTime.parse("2020-09-12T12:00:32"),
                                UserMock.getUserDtoById(3L), 3L));
        put(4L, new BulletinDto(4L, "Продам свадебный сервиз", BigDecimal.valueOf(34.12),
                                LocalDateTime.parse("2020-09-12T12:00:32"), UserMock.getUserDtoById(4L), 4L,
                                "БЕСПЛАТНАЯ доставка по г.Пружаны и району.", CommentMock.getAll(),
                                BulletinStatus.OPEN));
    }};
    private static final Map<Long, BulletinBaseDto> bulletinBaseMap = new HashMap<Long, BulletinBaseDto>() {{
        put(1L, new BulletinBaseDto(1L, "Продам свадебный сервиз", BigDecimal.valueOf(34.12),
                                    LocalDateTime.parse("2020-09-12T12:00:32"), UserMock.getUserDtoById(1L), 1L));
        put(2L, new BulletinBaseDto(2L, "Продам соковыжималку \"Журавинка\" СВСП 102П", BigDecimal.valueOf(25),
                                    LocalDateTime.parse("2020-09-12T12:00:32"), UserMock.getUserDtoById(2L), 2L));
        put(3L,
            new BulletinBaseDto(3L, "Продам хомяка", BigDecimal.valueOf(12), LocalDateTime.parse("2020-09-12T12:00:32"),
                                UserMock.getUserDtoById(3L), 3L));
        put(4L, new BulletinBaseDto(4L, "БЕСПЛАТНАЯ доставка по г.Пружаны и району.", BigDecimal.valueOf(34.12),
                                    LocalDateTime.parse("2020-09-12T12:00:32"),
                                    UserMock.getUserDtoById(4L) , 4L));
    }};

    public static BulletinBaseDto getBaseById(final Long id) {
        return bulletinBaseMap.get(id);
    }

    public static List<BulletinBaseDto> getAllBase() {
        return new ArrayList<>(bulletinBaseMap.values());
    }

    public static BulletinDto getById(final Long id) {
        return bulletinMap.get(id);
    }

    public static List<BulletinDto> getAll() {
        return new ArrayList<>(bulletinMap.values());
    }

}

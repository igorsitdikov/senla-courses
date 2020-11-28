package com.senla.bulletinboard.mock;

import com.senla.bulletinboard.dto.BulletinBaseDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BulletinMock {

    private static final Map<Long, BulletinBaseDto> bulletinMap = new HashMap<Long, BulletinBaseDto>() {{
        put(1L, new BulletinBaseDto(1L, "Продам свадебный сервиз", BigDecimal.valueOf(34.12),
                                    LocalDateTime.parse("2020-09-12T12:00:32"), UserMock.getUserDtoById(1L)));
        put(2L, new BulletinBaseDto(2L, "Продам соковыжималку \"Журавинка\" СВСП 102П", BigDecimal.valueOf(25),
                                    LocalDateTime.parse("2020-09-12T12:00:32"), UserMock.getUserDtoById(2L)));
        put(3L, new BulletinBaseDto(3L, "Продам хомяка", BigDecimal.valueOf(12), LocalDateTime.parse("2020-09-12T12:00:32"),
                                    UserMock.getUserDtoById(3L)));
        put(4L, new BulletinBaseDto(4L, "Продам отборный картофель, сорт «Вектор»", BigDecimal.valueOf(0.45), LocalDateTime.parse("2020-09-12T12:00:32"),
                                    UserMock.getUserDtoById(1L)));
    }};

    public static BulletinBaseDto getById(final Long id) {
        return bulletinMap.get(id);
    }

    public static List<BulletinBaseDto> getAll() {
        return new ArrayList<>(bulletinMap.values());
    }

}

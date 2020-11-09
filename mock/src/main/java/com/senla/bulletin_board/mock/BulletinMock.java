package com.senla.bulletin_board.mock;

import com.senla.bulletin_board.dto.BulletinDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BulletinMock {

    private static final Map<Long, BulletinDto> bulletinMap = new HashMap<Long, BulletinDto>() {{
        put(1L, new BulletinDto(1L, "Продам свадебный сервиз", BigDecimal.valueOf(34.12),
                                LocalDateTime.parse("2020-09-12T12:00:32"), UserMock.getUserDtoById(1L)));
        put(2L, new BulletinDto(2L, "Продам соковыжималку \"Журавинка\" СВСП 102П", BigDecimal.valueOf(25),
                                LocalDateTime.parse("2020-09-12T12:00:32"), UserMock.getUserDtoById(2L)));
        put(3L, new BulletinDto(3L, "Продам хомяка", BigDecimal.valueOf(12), LocalDateTime.parse("2020-09-12T12:00:32"),
                                UserMock.getUserDtoById(3L)));
        put(4L, new BulletinDto(4L, "Продам отборный картофель, сорт «Вектор»", BigDecimal.valueOf(0.45), LocalDateTime.parse("2020-09-12T12:00:32"),
                                UserMock.getUserDtoById(1L)));
    }};

    public static BulletinDto getById(final Long id) {
        return bulletinMap.get(id);
    }

    public static List<BulletinDto> getAll() {
        return new ArrayList<>(bulletinMap.values());
    }

}

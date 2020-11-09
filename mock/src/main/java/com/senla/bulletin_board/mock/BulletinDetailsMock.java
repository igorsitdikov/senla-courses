package com.senla.bulletin_board.mock;

import com.senla.bulletin_board.dto.BulletinDetailsDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BulletinDetailsMock {

    private static final Map<Long, BulletinDetailsDto> bulletinMap = new HashMap<Long, BulletinDetailsDto>() {{
        put(4L, new BulletinDetailsDto(4L, "Продам свадебный сервиз", BigDecimal.valueOf(34.12),
                                       LocalDateTime.parse("2020-09-12T12:00:32"), UserMock.getUserDtoById(1L),
                                       "БЕСПЛАТНАЯ доставка по г.Пружаны и району.", CommentMock.getAll()));
    }};

    public static BulletinDetailsDto getById(final Long id) {
        return bulletinMap.get(id);
    }

    public static List<BulletinDetailsDto> getAll() {
        return new ArrayList<>(bulletinMap.values());
    }

}

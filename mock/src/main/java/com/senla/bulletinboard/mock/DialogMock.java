package com.senla.bulletinboard.mock;

import com.senla.bulletinboard.dto.DialogDto;
import com.senla.bulletinboard.dto.DialogRequestDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DialogMock {

    private static final Map<Long, DialogDto> dialogMap = new HashMap<Long, DialogDto>() {{
        put(1L, new DialogDto(1L, "Продам свадебный сервиз", 3L, 1L, 4L, UserMock.getUserDtoById(1L),
                              BulletinDetailsMock.getById(1L),
                              LocalDateTime.parse("2020-09-12T12:00:32")));
    }};

    public static DialogDto getById(final Long id) {
        return dialogMap.get(id);
    }

    public static DialogRequestDto getRequestById(final Long id) {
        DialogDto dialogDto = dialogMap.get(id);
        DialogRequestDto dialogRequestDto = new DialogRequestDto(
            dialogDto.getSellerId(),
            dialogDto.getCustomerId(),
            dialogDto.getBulletinId(),
            dialogDto.getCreatedAt());
        return dialogRequestDto;
    }

    public static List<DialogDto> getAll() {
        return new ArrayList<>(dialogMap.values());
    }
}

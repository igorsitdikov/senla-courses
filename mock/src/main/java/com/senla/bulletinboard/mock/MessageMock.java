package com.senla.bulletinboard.mock;

import com.senla.bulletinboard.dto.MessageDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageMock {

    private static final Map<Long, MessageDto> messageMap = new HashMap<Long, MessageDto>() {{
        put(1L, new MessageDto(3L, 1L, 4L, "Возможна ли доставка 22.02.2021?",
                LocalDateTime.parse("2020-09-12T12:00:32")));
    }};

    public static MessageDto getById(final Long id) {
        return messageMap.get(id);
    }

    public static List<MessageDto> getAll() {
        return new ArrayList<>(messageMap.values());
    }
}

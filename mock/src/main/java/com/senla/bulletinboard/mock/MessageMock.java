package com.senla.bulletinboard.mock;

import com.senla.bulletinboard.dto.MessageDto;
import com.senla.bulletinboard.entity.DialogEntity;
import com.senla.bulletinboard.entity.MessageEntity;
import com.senla.bulletinboard.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageMock {

    private static final Map<Long, MessageDto> messageMap = new HashMap<Long, MessageDto>() {{
        put(1L, new MessageDto(
                1L,
                2L,
                1L,
                "Возможна ли доставка 22.02.2021?",
                LocalDateTime.parse("2020-09-12T12:00:32")));
    }};
    private static final Map<Long, MessageEntity> messageEntityMap = new HashMap<Long, MessageEntity>() {{
        put(1L, new MessageEntity(
                "Возможна ли доставка 22.02.2021?",
                LocalDateTime.parse("2020-09-12T12:00:32"),
                new UserEntity(),
                1L,
                new UserEntity(),
                2L,
                new DialogEntity(),
                1L));
    }};

    public static MessageDto getById(final Long id) {
        return messageMap.get(id);
    }

    public static List<MessageDto> getAll() {
        return new ArrayList<>(messageMap.values());
    }
}

package com.senla.bulletin_board.mock;

import com.senla.bulletin_board.dto.CommentDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentMock {

    private static final Map<Long, CommentDto> commentMap = new HashMap<Long, CommentDto>() {{
        put(1L, new CommentDto(UserMock.getUserDtoById(3L), "Отличный картофель",
                               LocalDateTime.parse("2020-09-14T11:30:45")));
    }};


    public static CommentDto getById(final Long id) {
        return commentMap.get(id);
    }

    public static List<CommentDto> getAll() {
        return new ArrayList<>(commentMap.values());
    }
}

package com.senla.bulletin_board.mock;

import com.senla.bulletin_board.dto.TariffDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TariffMock {

    private static final Map<Long, TariffDto> tariffMap = new HashMap<Long, TariffDto>() {{
        put(1L, new TariffDto(1L, BigDecimal.valueOf(5), 1, "5$ за 1 день"));
        put(2L, new TariffDto(2L, BigDecimal.valueOf(12), 3, "12$ за 3 дня"));
        put(3L, new TariffDto(3L, BigDecimal.valueOf(19.5), 7, "19.5$ за 7 дней"));
    }};

    public static TariffDto getById(final Long id) {
        return tariffMap.get(id);
    }

    public static List<TariffDto> getAll() {
        return new ArrayList<>(tariffMap.values());
    }
}

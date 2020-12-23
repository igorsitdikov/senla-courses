package mock;

import com.senla.bulletinboard.dto.TariffDto;
import com.senla.bulletinboard.entity.TariffEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TariffMock {

    private final Map<Long, TariffDto> tariffMap = new HashMap<Long, TariffDto>() {{
        put(1L, new TariffDto(1L, BigDecimal.valueOf(5), 1, "5$ за 1 день"));
        put(2L, new TariffDto(2L, BigDecimal.valueOf(12), 3, "12$ за 3 дня"));
        put(3L, new TariffDto(3L, BigDecimal.valueOf(19.5), 7, "19.5$ за 7 дней"));
    }};

    private final Map<Long, TariffEntity> tariffEntityMap = new HashMap<Long, TariffEntity>() {{
        put(1L, new TariffEntity(
            BigDecimal.valueOf(5),
            1,
            "5$ за 1 день"));
        put(2L, new TariffEntity(
            BigDecimal.valueOf(12),
            3,
            "12$ за 3 дня"));
        put(3L, new TariffEntity(
            BigDecimal.valueOf(19.5),
            7,
            "19.5$ за 7 дней"));
    }};

    public TariffEntity getEntityById(final Long id) {
        final TariffEntity tariffEntity = tariffEntityMap.get(id);
        tariffEntity.setId(id);
        return tariffEntity;
    }

    public TariffDto getById(final Long id) {
        return tariffMap.get(id);
    }

    public List<TariffDto> getAll() {
        return new ArrayList<>(tariffMap.values());
    }
}

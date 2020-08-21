package com.senla.hotel.utils;

import com.senla.hotel.entity.AEntity;
import com.senla.hotel.exceptions.EntityIsEmptyException;
import com.senla.hotel.mapper.interfaces.csvMapper.EntityMapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParseUtils {
    public static <T extends AEntity> List<T> stringToEntities(final Stream<String> stream,
                                                               final EntityMapper<T> mapper,
                                                               final Class<T> tClass) {
        return stream
            .map(entity -> {
                try {
                    return mapper.sourceToDestination(entity);
                } catch (final EntityIsEmptyException e) {
                    System.err.println(e.getMessage());
                }
                return null;
            })
            .filter(Objects::nonNull)
            .map(tClass::cast)
            .collect(Collectors.toList());
    }

    public static <T extends AEntity> List<String> entitiesToCsv(final EntityMapper<T> mapper,
                                                                 final List<T> entities) {
        return entities.stream()
            .map(entity -> {
                try {
                    return mapper.destinationToSource(entity);
                } catch (final EntityIsEmptyException e) {
                    System.err.println(e.getMessage());
                }
                return null;
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

}

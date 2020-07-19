package com.senla.hotel.utils.csv.interfaces;

import java.util.stream.Stream;

public interface CsvReader {
    Stream<String> read(final String property);
}

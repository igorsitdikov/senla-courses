package com.senla.hotel.utils.csv.interfaces;

import java.util.List;

public interface CsvWriter {
    void write(final String property, final List<String> entities);
}

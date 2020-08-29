package com.senla.hotel.utils.csv.interfaces;

import java.util.List;

public interface CsvWriter {

    void write(String property, List<String> entities);
}

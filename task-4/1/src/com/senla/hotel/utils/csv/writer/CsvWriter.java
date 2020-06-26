package com.senla.hotel.utils.csv.writer;

import com.senla.hotel.utils.csv.interfaces.ICsvWriter;
import com.senla.hotel.utils.settings.PropertyLoader;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class CsvWriter implements ICsvWriter {
    private static CsvWriter csvWriter;

    private CsvWriter() {
    }

    public static CsvWriter getInstance() {
        if (csvWriter == null) {
            csvWriter = new CsvWriter();
        }
        return csvWriter;
    }

    @Override
    public void write(final String property, final List<String> entities) {
        final String csv = PropertyLoader.getInstance().getProperty(property);
        try (final PrintWriter pw = new PrintWriter(csv)) {
            entities.forEach(pw::println);
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

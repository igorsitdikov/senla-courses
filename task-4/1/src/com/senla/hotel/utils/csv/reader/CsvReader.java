package com.senla.hotel.utils.csv.reader;

import com.senla.hotel.utils.csv.interfaces.ICsvReader;
import com.senla.hotel.utils.settings.PropertyLoader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class CsvReader implements ICsvReader {
    private static CsvReader csvReader;

    private CsvReader() {
    }

    public static CsvReader getInstance() {
        if (csvReader == null) {
            csvReader = new CsvReader();
        }
        return csvReader;
    }

    @Override
    public Stream<String> read(final String property) {
        final String csv = PropertyLoader.getInstance().getProperty(property);
        try {
            return Files.lines(Path.of(csv));
        } catch (final FileNotFoundException e) {
            System.err.println(String.format("Failed to read from file %s %s", csv, e));
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

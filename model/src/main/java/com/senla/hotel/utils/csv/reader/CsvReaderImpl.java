package com.senla.hotel.utils.csv.reader;

import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.utils.csv.interfaces.CsvReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Singleton
public class CsvReaderImpl implements CsvReader {

    @Override
    public Stream<String> read(final String path) {
        try {
            return Files.lines(Paths.get(path));
        } catch (final IOException e) {
            System.err.printf("File not found %s %s%n%n", path, e);
        }
        return null;
    }
}

package com.senla.hotel.utils.csv.reader;

import com.senla.anntotaion.Singleton;
import com.senla.hotel.utils.csv.interfaces.ICsvReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Singleton
public class CsvReader implements ICsvReader {
    @Override
    public Stream<String> read(final String path) {
        try {
            return Files.lines(Paths.get(path));
        } catch (final IOException e) {
            System.err.println(String.format("File not found %s %s%n", path, e));
        }
        return null;
    }
}

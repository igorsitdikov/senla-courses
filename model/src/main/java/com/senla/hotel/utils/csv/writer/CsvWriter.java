package com.senla.hotel.utils.csv.writer;

import com.senla.anntotaion.Singleton;
import com.senla.hotel.utils.csv.interfaces.ICsvWriter;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

@Singleton
public class CsvWriter implements ICsvWriter {
    @Override
    public void write(final String path, final List<String> entities) {
        try (final PrintWriter pw = new PrintWriter(path)) {
            entities.forEach(pw::println);
        } catch (final FileNotFoundException e) {
            System.err.println(String.format("File not found %s %s%n", path, e));
        }
    }
}

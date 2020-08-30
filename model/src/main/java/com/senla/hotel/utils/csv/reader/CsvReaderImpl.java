package com.senla.hotel.utils.csv.reader;

import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.utils.csv.interfaces.CsvReader;
import com.senla.hotel.utils.csv.writer.CsvWriterImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Singleton
public class CsvReaderImpl implements CsvReader {

    private static final Logger logger = LogManager.getLogger(CsvWriterImpl.class);

    @Override
    public Stream<String> read(final String path) {
        try {
            return Files.lines(Paths.get(path));
        } catch (final IOException e) {
            logger.error("File not found {} {}", path, e);
        }
        return null;
    }
}

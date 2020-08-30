package com.senla.hotel.utils.csv.writer;

import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.utils.Connector;
import com.senla.hotel.utils.csv.interfaces.CsvWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

@Singleton
public class CsvWriterImpl implements CsvWriter {

    private static final Logger logger = LogManager.getLogger(CsvWriterImpl.class);

    @Override
    public void write(final String path, final List<String> entities) {
        try (PrintWriter pw = new PrintWriter(path)) {
            entities.forEach(pw::println);
        } catch (final FileNotFoundException e) {
            logger.error("File not found {} {}", path, e);
        }
    }
}

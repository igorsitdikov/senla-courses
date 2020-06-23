package com.senla.hotel.utils.csv.writer;

import com.senla.hotel.service.AttendanceService;
import com.senla.hotel.utils.ParseUtils;
import com.senla.hotel.utils.csv.interfaces.ICsvWriter;
import com.senla.hotel.utils.settings.PropertyLoader;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class CsvWriter implements ICsvWriter {
    @Override
    public void write(final String property) {
        final String csv = PropertyLoader.getInstance().getProperty(property);

        try {
            final PrintWriter pw = new PrintWriter(csv);
            final List<String> list = ParseUtils.attendancesToString(AttendanceService.getInstance().showAttendances());
            list.forEach(pw::println);
            pw.close();
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

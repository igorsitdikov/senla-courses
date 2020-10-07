package mock;

import com.senla.hotel.dto.AttendanceDto;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.mapper.interfaces.dtoMapper.AttendanceDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class AttendanceMock {

    private static Map<Long, Attendance> attendanceMap = new HashMap<Long, Attendance>() {{
        put(1L, new Attendance(BigDecimal.valueOf(2.3).setScale(2), "Ironing"));
        put(2L, new Attendance(BigDecimal.valueOf(1.5).setScale(2), "Wake-up"));
        put(3L, new Attendance(BigDecimal.valueOf(4.5).setScale(2), "Laundry"));
        put(4L, new Attendance(BigDecimal.valueOf(3.1).setScale(2), "Dog walking"));
    }};

    public static Attendance getById(final Long id) {
        return attendanceMap.get(id);
    }

    public static AttendanceDto getDtoById(final Long id) {
        return destinationToSource(attendanceMap.get(id));
    }

    public static List<Attendance> getAll() {
        return attendanceMap.entrySet().stream().map(entry -> {
            Attendance attendance = new Attendance(
                    entry.getValue().getPrice(),
                    entry.getValue().getName());
            attendance.setId(entry.getKey());
            return attendance;
        }).collect(Collectors.toList());
    }

    public static List<AttendanceDto> getAllDto() {
        return attendanceMap
                .entrySet()
                .stream()
                .map(entry -> new AttendanceDto(
                        entry.getKey(),
                        entry.getValue().getPrice(),
                        entry.getValue().getName()))
                .collect(Collectors.toList());
    }

    private static AttendanceDto destinationToSource(final Attendance destination) {
        AttendanceDto source = new AttendanceDto();
        source.setId(destination.getId());
        source.setPrice(destination.getPrice());
        source.setName(destination.getName());
        return source;
    }

}

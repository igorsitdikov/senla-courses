package mock;

import com.senla.hotel.dto.ResidentDto;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.enumerated.Gender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class ResidentMock {

    private static Map<Long, Resident> residentMap = new HashMap<Long, Resident>() {{
        put(1L, new Resident("Mike", "Smith", Gender.MALE, false, "1234567", null));
        put(2L, new Resident("Alex", "Smith", Gender.MALE, false, "1234569", null));
        put(3L, new Resident("Juliet", "Fox", Gender.FEMALE, true, "1234568", null));
        put(4L, new Resident("Stephani", "Brown", Gender.FEMALE, false, "1234560", null));
        put(5L, new Resident("Carl", "Patoshek", Gender.MALE, false, "1234561", null));
    }};

    public static Resident getById(final Long id) {
        final Resident resident = residentMap.get(id);
        resident.setHistory(new HashSet<>());
        return resident;
    }

    public static ResidentDto getDtoById(final Long id) {
        return destinationToSource(residentMap.get(id));
    }

    public static List<Resident> getAll() {
        return residentMap.entrySet().stream().map(entry -> {
            Resident resident = new Resident(
                    entry.getValue().getFirstName(),
                    entry.getValue().getLastName(),
                    entry.getValue().getGender(),
                    entry.getValue().getVip(),
                    entry.getValue().getPhone(),
                    new HashSet<>());
            resident.setId(entry.getKey());
            return resident;
        }).collect(Collectors.toList());
    }

    public static List<ResidentDto> getAllDto() {
        return residentMap
                .entrySet()
                .stream()
                .map(entry -> new ResidentDto(
                        entry.getKey(),
                        entry.getValue().getFirstName(),
                        entry.getValue().getLastName(),
                        entry.getValue().getGender(),
                        entry.getValue().getVip(),
                        entry.getValue().getPhone(),
                        null))
                .collect(Collectors.toList());
    }

    private static ResidentDto destinationToSource(final Resident destination) {
        ResidentDto source = new ResidentDto();
        source.setId(destination.getId());
        source.setFirstName(destination.getFirstName());
        source.setLastName(destination.getLastName());
        source.setGender(destination.getGender());
        source.setPhone(destination.getPhone());
        source.setVip(destination.getVip());
        return source;
    }
}

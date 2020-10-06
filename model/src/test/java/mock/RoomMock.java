package mock;

import com.senla.hotel.dto.RoomDto;
import com.senla.hotel.entity.Room;
import com.senla.hotel.enumerated.Accommodation;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.enumerated.Stars;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class RoomMock {

    private static Map<Long, Room> roomMap = new HashMap<Long, Room>() {{
        put(1L, new Room(101, Stars.STANDARD, Accommodation.SGL, BigDecimal.valueOf(100), RoomStatus.VACANT));
        put(2L, new Room(102, Stars.JUNIOR_SUIT, Accommodation.SGL_2_CHD, BigDecimal.valueOf(120), RoomStatus.VACANT));
        put(3L, new Room(103, Stars.SUIT, Accommodation.DBL, BigDecimal.valueOf(150), RoomStatus.VACANT));
        put(4L, new Room(201, Stars.DE_LUX, Accommodation.TRPL, BigDecimal.valueOf(400), RoomStatus.OCCUPIED));
        put(5L, new Room(202, Stars.FAMILY_ROOM, Accommodation.TRPL_2_CHD, BigDecimal.valueOf(350), RoomStatus.OCCUPIED));
        put(6L, new Room(203, Stars.STUDIO, Accommodation.SGL, BigDecimal.valueOf(300), RoomStatus.REPAIR));
        put(7L, new Room(301, Stars.STUDIO, Accommodation.SGL, BigDecimal.valueOf(300), RoomStatus.VACANT));
        put(8L, new Room(302, Stars.DUPLEX, Accommodation.DBL_EXB, BigDecimal.valueOf(320), RoomStatus.REPAIR));
        put(9L, new Room(301, Stars.STUDIO, Accommodation.SGL, BigDecimal.valueOf(300), RoomStatus.VACANT));
        put(10L, new Room(303, Stars.HONEYMOON_ROOM, Accommodation.DBL, BigDecimal.valueOf(440), RoomStatus.OCCUPIED));
    }};

    public static Room getById(final Long id) {
        return roomMap.get(id);
    }

    public static RoomDto getDtoById(final Long id) {
        return destinationToSource(roomMap.get(id));
    }

    public static List<Room> getAll() {
        return new ArrayList<>(roomMap.values());
    }

    public static List<RoomDto> getAllDto() {
        return roomMap.entrySet()
                .stream()
                .map(entry -> new RoomDto(
                        entry.getKey(),
                        entry.getValue().getNumber(),
                        entry.getValue().getStars(),
                        entry.getValue().getAccommodation(),
                        entry.getValue().getPrice(),
                        entry.getValue().getStatus(),
                        new ArrayList<>()
                ))
                .collect(Collectors.toList());
    }

    private static RoomDto destinationToSource(final Room destination) {
        RoomDto source = new RoomDto();
        source.setId(destination.getId());
        source.setPrice(destination.getPrice());
        source.setStatus(destination.getStatus());
        source.setAccommodation(destination.getAccommodation());
        source.setNumber(destination.getNumber());
        source.setStars(destination.getStars());
        source.setHistoriesDto(new ArrayList<>());
        return source;
    }
}

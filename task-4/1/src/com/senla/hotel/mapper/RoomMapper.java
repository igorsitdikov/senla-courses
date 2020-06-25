package com.senla.hotel.mapper;

import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.enumerated.Accommodation;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.enumerated.Stars;
import com.senla.hotel.exceptions.NoSuchEntityException;
import com.senla.hotel.mapper.interfaces.IEntityMapper;
import com.senla.hotel.service.RoomHistoryService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RoomMapper implements IEntityMapper<Room> {
    @Override
    public Room sourceToDestination(String source) {
        if (source == null) {
            return null;
        }

        final String[] elements = source.split(SEPARATOR);
        Room room = new Room();
        room.setId(Long.valueOf(elements[0]));
        room.setNumber(Integer.valueOf(elements[1]));
        room.setStars(Stars.valueOf(elements[2]));
        room.setAccommodation(Accommodation.valueOf(elements[3]));
        room.setPrice(new BigDecimal(elements[4]));
        room.setStatus(RoomStatus.valueOf(elements[5]));

        List<RoomHistory> historyList = new ArrayList<>();
        if (elements.length > 6) {
            for (int i = 6; i < elements.length; i++) {
                try {
                    historyList.add(RoomHistoryService.getInstance().findById(Long.parseLong(elements[i])));
                } catch (NoSuchEntityException e) {
                    System.err.println(String.format("No such history with id %s %s", elements[i], e));
                }
            }
        }
        room.setHistories(historyList);

        return room;
    }

    @Override
    public String destinationToSource(Room destination) {
        if (destination == null) {
            return null;
        }

        final StringBuilder sb = new StringBuilder();
        sb.append(destination.getId());
        sb.append(SEPARATOR);
        sb.append(destination.getNumber());
        sb.append(SEPARATOR);
        sb.append(destination.getStars());
        sb.append(SEPARATOR);
        sb.append(destination.getAccommodation());
        sb.append(SEPARATOR);
        sb.append(destination.getPrice());
        sb.append(SEPARATOR);
        sb.append(destination.getStatus());
        sb.append(SEPARATOR);
        destination.getHistories()
                .forEach(history -> sb.append(history.getId()).append(SEPARATOR));

        return sb.toString();
    }
}

package com.senla.hotel.mapper;

import com.senla.hotel.entity.Room;
import com.senla.hotel.enumerated.Accommodation;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.enumerated.Stars;
import com.senla.hotel.exceptions.EntityIsEmptyException;
import com.senla.hotel.mapper.interfaces.csvMapper.RoomMapper;

import java.math.BigDecimal;

public class RoomMapperImpl implements RoomMapper {

    @Override
    public Room sourceToDestination(final String source) throws EntityIsEmptyException {
        if (source.isEmpty()) {
            throw new EntityIsEmptyException("Room string is empty");
        }

        final String[] elements = source.split(SEPARATOR);
        final Room room = new Room();
        room.setId(Long.valueOf(elements[0]));
        room.setNumber(Integer.valueOf(elements[1]));
        room.setStars(Stars.valueOf(elements[2]));
        room.setAccommodation(Accommodation.valueOf(elements[3]));
        room.setPrice(new BigDecimal(elements[4]));
        room.setStatus(RoomStatus.valueOf(elements[5]));

        return room;
    }

    @Override
    public String destinationToSource(final Room destination) throws EntityIsEmptyException {
        if (destination == null) {
            throw new EntityIsEmptyException("Room is null");
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

        return sb.toString();
    }
}

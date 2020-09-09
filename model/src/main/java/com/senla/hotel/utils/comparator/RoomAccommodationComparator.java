package com.senla.hotel.utils.comparator;

import com.senla.hotel.dto.RoomDTO;
import com.senla.hotel.entity.Room;

import java.util.Comparator;

public class RoomAccommodationComparator implements Comparator<RoomDTO> {

    @Override
    public int compare(final RoomDTO roomFirst, final RoomDTO roomSecond) {
        return roomFirst.getAccommodation().compareTo(roomSecond.getAccommodation());
    }
}

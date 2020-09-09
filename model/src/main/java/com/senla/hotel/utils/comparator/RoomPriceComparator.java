package com.senla.hotel.utils.comparator;

import com.senla.hotel.dto.RoomDTO;
import com.senla.hotel.entity.Room;

import java.util.Comparator;

public class RoomPriceComparator implements Comparator<RoomDTO> {

    @Override
    public int compare(final RoomDTO roomFirst, final RoomDTO roomSecond) {
        return roomFirst.getPrice().compareTo(roomSecond.getPrice());
    }
}

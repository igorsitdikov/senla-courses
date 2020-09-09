package com.senla.hotel.utils.comparator;

import com.senla.hotel.dto.RoomDTO;
import com.senla.hotel.entity.Room;

import java.util.Comparator;

public class RoomStarsComparator implements Comparator<RoomDTO> {

    @Override
    public int compare(final RoomDTO roomFirst, final RoomDTO roomSecond) {
        return roomFirst.getStars().compareTo(roomSecond.getStars());
    }
}

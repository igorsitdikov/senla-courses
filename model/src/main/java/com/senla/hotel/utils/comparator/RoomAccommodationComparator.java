package com.senla.hotel.utils.comparator;

import com.senla.hotel.entity.Room;

import java.util.Comparator;

public class RoomAccommodationComparator implements Comparator<Room> {

    @Override
    public int compare(final Room roomFirst, final Room roomSecond) {
        return roomFirst.getAccommodation().compareTo(roomSecond.getAccommodation());
    }
}

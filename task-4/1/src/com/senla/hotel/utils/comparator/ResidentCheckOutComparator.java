package com.senla.hotel.utils.comparator;

import com.senla.hotel.entity.Resident;

import java.util.Comparator;

public class ResidentCheckOutComparator implements Comparator<Resident> {
    @Override
    public int compare(final Resident residentFirst, final Resident residentSecond) {
        return residentFirst.getHistory().getCheckOut().compareTo(residentSecond.getHistory().getCheckOut());
    }
}

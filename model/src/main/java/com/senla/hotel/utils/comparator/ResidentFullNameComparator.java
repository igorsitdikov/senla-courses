package com.senla.hotel.utils.comparator;

import com.senla.hotel.entity.Resident;

import java.util.Comparator;

public class ResidentFullNameComparator implements Comparator<Resident> {

    @Override
    public int compare(final Resident residentFirst, final Resident residentSecond) {
        final int sizeCmp = residentFirst.getLastName().compareTo(residentSecond.getLastName());
        if (sizeCmp != 0) {
            return sizeCmp;
        }
        return residentFirst.getFirstName().compareTo(residentSecond.getFirstName());
    }
}

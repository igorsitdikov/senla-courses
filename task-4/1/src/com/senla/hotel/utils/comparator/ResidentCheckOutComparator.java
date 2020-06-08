package com.senla.hotel.utils.comparator;

import com.senla.hotel.entity.Resident;

import java.util.Comparator;

public class ResidentCheckOutComparator implements Comparator<Resident> {
    @Override
    public int compare(final Resident residentFirst, final Resident residentSecond) {
        if (residentFirst.getHistory() != null &&
            residentSecond.getHistory() != null) {
            int cmp = residentSecond.getHistory().getCheckOut().getYear() - residentFirst.getHistory().getCheckOut().getYear();
            if (cmp == 0) {
                cmp = residentSecond.getHistory().getCheckOut().getDayOfYear()- residentFirst.getHistory().getCheckOut().getDayOfYear();
            }
            return cmp;
        }
        return 0;
    }
}

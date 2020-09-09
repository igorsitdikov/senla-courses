package com.senla.hotel.utils.comparator;

import com.senla.hotel.dto.ResidentDTO;
import com.senla.hotel.entity.Resident;

import java.util.Comparator;

public class ResidentFullNameComparator implements Comparator<ResidentDTO> {

    @Override
    public int compare(final ResidentDTO residentFirst, final ResidentDTO residentSecond) {
        final int sizeCmp = residentFirst.getLastName().compareTo(residentSecond.getLastName());
        if (sizeCmp != 0) {
            return sizeCmp;
        }
        return residentFirst.getFirstName().compareTo(residentSecond.getFirstName());
    }
}

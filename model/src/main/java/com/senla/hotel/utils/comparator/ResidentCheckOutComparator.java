package com.senla.hotel.utils.comparator;

import com.senla.hotel.dto.ResidentDTO;
import com.senla.hotel.entity.Resident;

import java.util.Comparator;

public class ResidentCheckOutComparator implements Comparator<ResidentDTO> {

    @Override
    public int compare(final ResidentDTO residentFirst, final ResidentDTO residentSecond) {
        if (residentFirst.getHistory() != null &&
            residentSecond.getHistory() != null) {
            return residentFirst.getHistory().iterator().next().getCheckOut().compareTo(residentSecond.getHistory().iterator().next().getCheckOut());
        } else if (residentFirst.getHistory() != null && residentSecond.getHistory() == null) {
            return -1;
        } else if (residentFirst.getHistory() == null && residentSecond.getHistory() != null) {
            return 1;
        }
        return 0;
    }
}

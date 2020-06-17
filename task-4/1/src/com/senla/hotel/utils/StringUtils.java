package com.senla.hotel.utils;

import com.senla.hotel.enumerated.Accommodation;
import com.senla.hotel.enumerated.RoomStatus;

public class StringUtils {
    public static String accommodationToString(final Accommodation accommodation) {
        switch (accommodation) {
            case SGL_CHD:
                return "single resident with 1 child";
            case SGL_2_CHD:
                return "single resident with 2 children";
            case DBL:
                return "double residents";
            case DBL_EXB:
                return "double residents with extra bad";
            case DBL_CHD:
                return "double residents with 1 child";
            case DBL_EXB_CHD:
                return "double residents with 1 child and extra bad";
            case TRPL:
                return "triple residents";
            case TRPL_CHD:
                return "triple residents with 1 children";
            case TRPL_2_CHLD:
                return "triple residents with 2 children";
            default:
                return "single resident";
        }
    }

    public static String statusToString(final RoomStatus status) {
        switch (status) {
            case OCCUPIED:
                return "occupied";
            case REPAIR:
                return "being repaired";
            default:
                return "vacant";
        }
    }
}

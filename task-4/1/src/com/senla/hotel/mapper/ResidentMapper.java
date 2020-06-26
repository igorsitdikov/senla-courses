package com.senla.hotel.mapper;

import com.senla.hotel.entity.Resident;
import com.senla.hotel.enumerated.Gender;
import com.senla.hotel.mapper.interfaces.IEntityMapper;

public class ResidentMapper implements IEntityMapper<Resident> {
    @Override
    public Resident sourceToDestination(final String source) {
        if (source == null) {
            throw new NullPointerException();
        }
        final String[] elements = source.split(SEPARATOR);
        final Resident resident = new Resident();
        resident.setId(Long.valueOf(elements[0]));
        resident.setFirstName(elements[1]);
        resident.setLastName(elements[2]);
        resident.setGender(Gender.valueOf(elements[3]));
        resident.setVip(Boolean.parseBoolean(elements[4]));
        resident.setPhone(elements[5]);

        return resident;
    }

    @Override
    public String destinationToSource(final Resident destination) {
        if (destination == null) {
            throw new NullPointerException();
        }
        final StringBuilder sb = new StringBuilder();
        sb.append(destination.getId());
        sb.append(SEPARATOR);
        sb.append(destination.getFirstName());
        sb.append(SEPARATOR);
        sb.append(destination.getLastName());
        sb.append(SEPARATOR);
        sb.append(destination.getGender());
        sb.append(SEPARATOR);
        sb.append(destination.getVip());
        sb.append(SEPARATOR);
        sb.append(destination.getPhone());

        return sb.toString();
    }
}

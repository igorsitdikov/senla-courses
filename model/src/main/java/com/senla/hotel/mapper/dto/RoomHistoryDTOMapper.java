package com.senla.hotel.mapper.dto;

import com.senla.hotel.dto.RoomHistoryDTO;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.exceptions.DTOIsEmptyException;
import com.senla.hotel.exceptions.EntityIsEmptyException;
import com.senla.hotel.mapper.interfaces.IDataTransferObjectMapper;

import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.stream.Collectors;

public class RoomHistoryDTOMapper implements IDataTransferObjectMapper<RoomHistory, RoomHistoryDTO> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final ResidentDTOMapper residentMapper = new ResidentDTOMapper();
    private static final AttendanceDTOMapper attendanceMapper = new AttendanceDTOMapper();
    private static final RoomDTOMapper roomMapper = new RoomDTOMapper();

    @Override
    public RoomHistory sourceToDestination(final RoomHistoryDTO source) throws DTOIsEmptyException {
        if (source == null) {
            throw new DTOIsEmptyException("RoomHistoryDTO is empty");
        }
        final RoomHistory roomHistory = new RoomHistory();
        roomHistory.setId(source.getId());
        roomHistory.setRoom(roomMapper.sourceToDestination(source.getRoom()));
        roomHistory.setCheckIn(source.getCheckIn());
        roomHistory.setCheckOut(source.getCheckOut());
        roomHistory.setResident(residentMapper.sourceToDestination(source.getResident()));
        roomHistory.setAttendances(source.getAttendances()
                                       .stream()
                                       .map(attendanceDTO -> {
                                           try {
                                               return attendanceMapper.sourceToDestination(attendanceDTO);
                                           } catch (final DTOIsEmptyException e) {
                                               e.printStackTrace();
                                           }
                                           return null;
                                       })
                                       .filter(Objects::nonNull)
                                       .collect(Collectors.toList()));
        return roomHistory;
    }

    @Override
    public RoomHistoryDTO destinationToSource(final RoomHistory destination) throws EntityIsEmptyException {
        if (destination == null) {
            throw new EntityIsEmptyException("RoomHistory is empty");
        }
        final RoomHistoryDTO roomHistoryDTO = new RoomHistoryDTO();
        roomHistoryDTO.setId(destination.getId());
        roomHistoryDTO.setRoom(roomMapper.destinationToSource(destination.getRoom()));
        roomHistoryDTO.setCheckIn(destination.getCheckIn());
        roomHistoryDTO.setCheckOut(destination.getCheckOut());
        roomHistoryDTO.setResident(residentMapper.destinationToSource(destination.getResident()));
        roomHistoryDTO.setAttendances(destination.getAttendances()
                                          .stream()
                                          .map(attendanceDTO -> {
                                              try {
                                                  return attendanceMapper.destinationToSource(attendanceDTO);
                                              } catch (final EntityIsEmptyException e) {
                                                  e.printStackTrace();
                                              }
                                              return null;
                                          })
                                          .filter(Objects::nonNull)
                                          .collect(Collectors.toList()));

        return roomHistoryDTO;
    }
}

package com.senla.hotel.repository.interfaces;

import com.senla.hotel.entity.AEntity;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.exceptions.EntityNotFoundException;

import java.math.BigDecimal;
import java.util.List;

public interface IRoomRepository {
    List<Room> getVacantRooms();

    int countVacantRooms();

    AEntity add(AEntity room);

    AEntity findById(Long id) throws EntityNotFoundException;

    AEntity findByRoomNumber(Integer number) throws EntityNotFoundException;

    void changePrice(Long id, BigDecimal price) throws EntityNotFoundException;

    void changePrice(Integer number, BigDecimal price) throws EntityNotFoundException;

    void addHistory(Room room, RoomHistory roomHistory);

    void setRooms(List<Room> rooms);

    List<Room> getRooms();
}

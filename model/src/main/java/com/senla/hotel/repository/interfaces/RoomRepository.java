package com.senla.hotel.repository.interfaces;

import com.senla.hotel.entity.AEntity;
import com.senla.hotel.entity.Room;
import com.senla.hotel.exceptions.EntityNotFoundException;

import java.math.BigDecimal;
import java.util.List;

public interface RoomRepository {
    List<Room> getVacantRooms();

    int countVacantRooms();

    AEntity add(AEntity room);

    AEntity findById(Long id) throws EntityNotFoundException;

    AEntity findByRoomNumber(Integer number) throws EntityNotFoundException;

    void update(Room room);

    void changePrice(Long id, BigDecimal price) throws EntityNotFoundException;

    void changePrice(Integer number, BigDecimal price) throws EntityNotFoundException;

    void setRooms(List<Room> rooms);

    List<Room> getRooms();
}

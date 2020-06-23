package com.senla.hotel.repository.interfaces;

import com.senla.hotel.entity.AEntity;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;

import java.math.BigDecimal;
import java.util.List;

public interface IRoomRepository {
    List<Room> getVacantRooms();

    int countVacantRooms();

    AEntity add(AEntity room);

    AEntity findById(Long id);

    AEntity findByRoomNumber(Integer number);

    void changePrice(Long id, BigDecimal price);

    void changePrice(Integer number, BigDecimal price);

    void addHistory(Room room, RoomHistory roomHistory);

    List<Room> getRooms();
}

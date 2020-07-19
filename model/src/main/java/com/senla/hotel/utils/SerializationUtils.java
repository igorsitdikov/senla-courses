package com.senla.hotel.utils;

import com.senla.annotaion.Autowired;
import com.senla.annotaion.PropertyLoad;
import com.senla.annotaion.Singleton;
import com.senla.hotel.entity.AEntity;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.repository.interfaces.IAttendanceRepository;
import com.senla.hotel.repository.interfaces.IResidentRepository;
import com.senla.hotel.repository.interfaces.IRoomHistoryRepository;
import com.senla.hotel.repository.interfaces.IRoomRepository;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Singleton
public class SerializationUtils {
    @Autowired
    private IRoomRepository roomRepository;
    @Autowired
    private IAttendanceRepository attendanceRepository;
    @Autowired
    private IRoomHistoryRepository historyRepository;
    @Autowired
    private IResidentRepository residentRepository;
    @PropertyLoad
    private String hotelState;

    @SafeVarargs
    public final void serialize(final List<? extends AEntity>... entities) {
        try {
            final List<List<? extends AEntity>> entitiesList = new ArrayList<>(Arrays.asList(entities));
            final ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(hotelState));
            objectOutputStream.writeObject(entitiesList);
            objectOutputStream.close();

        } catch (final FileNotFoundException e) {
            System.err.printf("No such file! %s%n", e);
        } catch (final IOException e) {
            System.err.printf("Fail to save the Hotel state! %s%n", e);
        }
    }

    public void deserialize() {
        try {
            final ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(hotelState));
            final List<List<? extends AEntity>> entitiesLists =
                (List<List<? extends AEntity>>) objectInputStream.readObject();
            for (List<? extends AEntity> entitiesList : entitiesLists) {
                final Long id = maxId(entitiesList);
                if (!entitiesList.isEmpty() && entitiesList.get(0) instanceof Attendance) {
                    attendanceRepository.setAttendances((List<Attendance>) entitiesList);
                    attendanceRepository.setCounter(id);
                } else if (!entitiesList.isEmpty() && entitiesList.get(0) instanceof Room) {
                    roomRepository.setRooms((List<Room>) entitiesList);
                    roomRepository.setCounter(id);
                } else if (!entitiesList.isEmpty() && entitiesList.get(0) instanceof RoomHistory) {
                    historyRepository.setHistories((List<RoomHistory>) entitiesList);
                    historyRepository.setCounter(id);
                } else if (!entitiesList.isEmpty() && entitiesList.get(0) instanceof Resident) {
                    residentRepository.setResidents((List<Resident>) entitiesList);
                    residentRepository.setCounter(id);
                }
            }
            objectInputStream.close();

        } catch (final FileNotFoundException e) {
            System.err.printf("File was not found with name %s%n", hotelState);
        } catch (final IOException e) {
            System.err.printf("Fail to load data from file %s%n", hotelState);
        } catch (final ClassNotFoundException e) {
            System.err.println("Class was not found");
        }
    }

    private static Long maxId(final List<? extends AEntity> list) {
        return list.stream().mapToLong(AEntity::getId).max().orElseThrow(NoSuchElementException::new);
    }
}

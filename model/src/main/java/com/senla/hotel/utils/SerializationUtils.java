package com.senla.hotel.utils;

import com.senla.annotation.Autowired;
import com.senla.annotation.PropertyLoad;
import com.senla.annotation.Singleton;
import com.senla.hotel.entity.*;
import com.senla.hotel.repository.interfaces.AttendanceRepository;
import com.senla.hotel.repository.interfaces.ResidentRepository;
import com.senla.hotel.repository.interfaces.RoomHistoryRepository;
import com.senla.hotel.repository.interfaces.RoomRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Singleton
public class SerializationUtils {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private RoomHistoryRepository historyRepository;
    @Autowired
    private ResidentRepository residentRepository;
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

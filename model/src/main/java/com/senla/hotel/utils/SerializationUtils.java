package com.senla.hotel.utils;

import com.senla.hotel.entity.AEntity;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.repository.AttendanceRepository;
import com.senla.hotel.repository.ResidentRepository;
import com.senla.hotel.repository.RoomHistoryRepository;
import com.senla.hotel.repository.RoomRepository;
import com.senla.hotel.utils.settings.PropertyLoader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SerializationUtils {
    private final static String STATE_HOTEL = PropertyLoader.getInstance().getProperty("state-hotel");

    @SafeVarargs
    public static void serialize(final List<? extends AEntity>... entities) {
        try {
            final List<List<? extends AEntity>> entitiesList = new ArrayList<>(Arrays.asList(entities));
            final ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(STATE_HOTEL));

            objectOutputStream.writeObject(entitiesList);
            objectOutputStream.close();

        } catch (final FileNotFoundException e) {
            System.err.printf("No such file! %s%n", e);
        } catch (final IOException e) {
            System.err.printf("Fail to save the Hotel state! %s%n", e);
        }
    }

    public static void deserialize() {

        try {
            final ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(STATE_HOTEL));
            final List<List<? extends AEntity>> entitiesLists =
                (List<List<? extends AEntity>>) objectInputStream.readObject();
            entitiesLists.forEach(entitiesList -> {
                if (!entitiesList.isEmpty() && entitiesList.get(0) instanceof Attendance) {
                    AttendanceRepository.getInstance().setAttendances((List<Attendance>) entitiesList);
                } else if (!entitiesList.isEmpty() && entitiesList.get(0) instanceof Room) {
                    RoomRepository.getInstance().setRooms((List<Room>) entitiesList);
                } else if (!entitiesList.isEmpty() && entitiesList.get(0) instanceof RoomHistory) {
                    RoomHistoryRepository.getInstance().setHistories((List<RoomHistory>) entitiesList);
                } else if (!entitiesList.isEmpty() && entitiesList.get(0) instanceof Resident) {
                    ResidentRepository.getInstance().setResidents((List<Resident>) entitiesList);
                }
            });

            objectInputStream.close();

        } catch (final FileNotFoundException e) {
            System.err.printf("File was not found with name %s%n", STATE_HOTEL);
        } catch (final IOException e) {
            System.err.printf("Fail to load data from file %s%n", STATE_HOTEL);
        } catch (final ClassNotFoundException e) {
            System.err.println("Class was not found");
        }
    }
}

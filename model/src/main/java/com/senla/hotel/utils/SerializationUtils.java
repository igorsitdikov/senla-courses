package com.senla.hotel.utils;

import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.annotation.PropertyLoad;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dao.interfaces.AttendanceDao;
import com.senla.hotel.dao.interfaces.ResidentDao;
import com.senla.hotel.dao.interfaces.RoomDao;
import com.senla.hotel.dao.interfaces.RoomHistoryDao;
import com.senla.hotel.entity.AEntity;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.exceptions.PersistException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Singleton
public class SerializationUtils {

    @Autowired
    private RoomDao roomRepository;
    @Autowired
    private AttendanceDao attendanceRepository;
    @Autowired
    private RoomHistoryDao historyRepository;
    @Autowired
    private ResidentDao residentRepository;
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
                if (!entitiesList.isEmpty() && entitiesList.get(0) instanceof Attendance) {
                    attendanceRepository.insertMany((List<Attendance>) entitiesList);
                } else if (!entitiesList.isEmpty() && entitiesList.get(0) instanceof Room) {
                    roomRepository.insertMany((List<Room>) entitiesList);
                } else if (!entitiesList.isEmpty() && entitiesList.get(0) instanceof RoomHistory) {
                    historyRepository.insertMany((List<RoomHistory>) entitiesList);
                } else if (!entitiesList.isEmpty() && entitiesList.get(0) instanceof Resident) {
                    residentRepository.insertMany((List<Resident>) entitiesList);
                }
            }
            objectInputStream.close();
        } catch (final FileNotFoundException e) {
            System.err.printf("File was not found with name %s%n", hotelState);
        } catch (final IOException e) {
            System.err.printf("Fail to load data from file %s%n", hotelState);
        } catch (final ClassNotFoundException e) {
            System.err.println("Class was not found");
        } catch (final PersistException e) {
            System.err.printf("Couldn't add to database %s%n", e.getMessage());
        }
    }
}

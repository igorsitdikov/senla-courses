package com.senla.hotel.utils;

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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class SerializationUtils {

    private static final Logger logger = LogManager.getLogger(SerializationUtils.class);

    private final RoomDao roomRepository;
    private final AttendanceDao attendanceRepository;
    private final RoomHistoryDao historyRepository;
    private final ResidentDao residentRepository;
    @Value(value = "hotelState")
    private String hotelState;

    public SerializationUtils(final RoomDao roomRepository,
                              final AttendanceDao attendanceRepository,
                              final RoomHistoryDao historyRepository,
                              final ResidentDao residentRepository) {
        this.roomRepository = roomRepository;
        this.attendanceRepository = attendanceRepository;
        this.historyRepository = historyRepository;
        this.residentRepository = residentRepository;
    }

    @SafeVarargs
    public final void serialize(final List<? extends AEntity>... entities) {
        try {
            final List<List<? extends AEntity>> entitiesList = new ArrayList<>(Arrays.asList(entities));
            final ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(hotelState));
            objectOutputStream.writeObject(entitiesList);
            objectOutputStream.close();
        } catch (final FileNotFoundException e) {
            logger.error("No such file! {}", e.getMessage());
        } catch (final IOException e) {
            logger.error("Fail to save the Hotel state! {}", e.getMessage());
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
            logger.error("File was not found with name {}", hotelState);
        } catch (final IOException e) {
            logger.error("Fail to load data from file {}", hotelState);
        } catch (final ClassNotFoundException e) {
            logger.error("Class was not found");
        } catch (final PersistException e) {
            logger.error("Couldn't add to database {}", e.getMessage());
        }
    }
}

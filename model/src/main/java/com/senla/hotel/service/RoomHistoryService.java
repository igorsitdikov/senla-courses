package com.senla.hotel.service;

import com.senla.anntotaion.Autowired;
import com.senla.anntotaion.PropertyLoad;
import com.senla.anntotaion.Singleton;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.mapper.RoomHistoryMapper;
import com.senla.hotel.mapper.interfaces.IEntityMapper;
import com.senla.hotel.repository.interfaces.IRoomHistoryRepository;
import com.senla.hotel.service.interfaces.IRoomHistoryService;
import com.senla.hotel.utils.ParseUtils;
import com.senla.hotel.utils.csv.interfaces.ICsvReader;
import com.senla.hotel.utils.csv.interfaces.ICsvWriter;

import java.util.List;

@Singleton
public class RoomHistoryService implements IRoomHistoryService {
    @Autowired
    private ICsvReader csvReader;
    @Autowired
    private ICsvWriter csvWriter;
    @PropertyLoad(propertyName = "histories")
    private String property;
    @Autowired
    private IRoomHistoryRepository roomHistoryRepository;

    @Override
    public RoomHistory create(final RoomHistory history) {
        return (RoomHistory) roomHistoryRepository.add(history);
    }

    @Override
    public RoomHistory findById(final Long id) throws EntityNotFoundException {
        final RoomHistory history = (RoomHistory) roomHistoryRepository.findById(id);
        if (history == null) {
            throw new EntityNotFoundException(String.format("No history with id %d%n", id));
        }
        return history;
    }

    @Override
    public void importHistories() {
        IEntityMapper<RoomHistory> roomHistoryMapper = new RoomHistoryMapper();
        final List<RoomHistory> histories =
            ParseUtils.stringToEntities(csvReader.read(property), roomHistoryMapper, RoomHistory.class);
        roomHistoryRepository.setHistories(histories);
    }

    @Override
    public void exportHistories() {
        IEntityMapper<RoomHistory> roomHistoryMapper = new RoomHistoryMapper();
        csvWriter.write(property, ParseUtils.entitiesToCsv(roomHistoryMapper, showHistories()));
    }

    @Override
    public List<RoomHistory> showHistories() {
        return roomHistoryRepository.getHistories();
    }
}

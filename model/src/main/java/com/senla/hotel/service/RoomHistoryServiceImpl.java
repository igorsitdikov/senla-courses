package com.senla.hotel.service;

import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.annotation.PropertyLoad;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.mapper.RoomHistoryMapperImpl;
import com.senla.hotel.mapper.interfaces.EntityMapper;
import com.senla.hotel.repository.interfaces.RoomHistoryRepository;
import com.senla.hotel.service.interfaces.RoomHistoryService;
import com.senla.hotel.utils.ParseUtils;
import com.senla.hotel.utils.csv.interfaces.CsvReader;
import com.senla.hotel.utils.csv.interfaces.CsvWriter;

import java.util.List;

@Singleton
public class RoomHistoryServiceImpl implements RoomHistoryService {
    @Autowired
    private CsvReader csvReader;
    @Autowired
    private CsvWriter csvWriter;
    @PropertyLoad(propertyName = "histories")
    private String property;
    @Autowired
    private RoomHistoryRepository roomHistoryRepository;

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
        EntityMapper<RoomHistory> roomHistoryMapper = new RoomHistoryMapperImpl();
        final List<RoomHistory> histories =
            ParseUtils.stringToEntities(csvReader.read(property), roomHistoryMapper, RoomHistory.class);
        roomHistoryRepository.setHistories(histories);
    }

    @Override
    public void exportHistories() {
        EntityMapper<RoomHistory> roomHistoryMapper = new RoomHistoryMapperImpl();
        csvWriter.write(property, ParseUtils.entitiesToCsv(roomHistoryMapper, showHistories()));
    }

    @Override
    public List<RoomHistory> showHistories() {
        return roomHistoryRepository.getHistories();
    }
}

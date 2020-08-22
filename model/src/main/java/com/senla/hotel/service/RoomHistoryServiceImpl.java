package com.senla.hotel.service;

import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.annotation.PropertyLoad;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dao.interfaces.RoomHistoryDao;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.mapper.RoomHistoryMapperImpl;
import com.senla.hotel.mapper.interfaces.csvMapper.EntityMapper;
import com.senla.hotel.mapper.interfaces.csvMapper.RoomHistoryMapper;
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
    private RoomHistoryDao roomHistoryRepository;
    @Autowired
    private RoomHistoryMapper roomHistoryMapper;

    @Override
    public RoomHistory create(final RoomHistory history) throws PersistException {
        return roomHistoryRepository.create(history);
    }

    @Override
    public RoomHistory findById(final Long id) throws EntityNotFoundException, PersistException {
        final RoomHistory history = roomHistoryRepository.findById(id);
        if (history == null) {
            throw new EntityNotFoundException(String.format("No history with id %d%n", id));
        }
        return history;
    }

    @Override
    public void importHistories() throws PersistException {
        final List<RoomHistory> histories =
            ParseUtils.stringToEntities(csvReader.read(property), roomHistoryMapper, RoomHistory.class);
        roomHistoryRepository.insertMany(histories);
    }

    @Override
    public void exportHistories() throws PersistException {
        csvWriter.write(property, ParseUtils.entitiesToCsv(roomHistoryMapper, roomHistoryRepository.getAll()));
    }

    @Override
    public List<RoomHistory> showHistories() throws PersistException {
        return roomHistoryRepository.getAll();
    }
}

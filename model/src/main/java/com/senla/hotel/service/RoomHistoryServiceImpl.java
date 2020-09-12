package com.senla.hotel.service;

import com.senla.hotel.dao.interfaces.RoomHistoryDao;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.mapper.interfaces.csvMapper.RoomHistoryMapper;
import com.senla.hotel.service.interfaces.RoomHistoryService;
import com.senla.hotel.utils.ParseUtils;
import com.senla.hotel.utils.csv.interfaces.CsvReader;
import com.senla.hotel.utils.csv.interfaces.CsvWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoomHistoryServiceImpl implements RoomHistoryService {

    @Autowired
    private CsvReader csvReader;
    @Autowired
    private RoomHistoryDao roomHistoryDao;
    @Autowired
    private RoomHistoryMapper roomHistoryMapper;
    @Autowired
    private CsvWriter csvWriter;
    @Value(value = "histories")
    private String property;

    public RoomHistoryServiceImpl(final CsvReader csvReader,
                                  final CsvWriter csvWriter,
                                  final RoomHistoryDao roomHistoryDao,
                                  final RoomHistoryMapper roomHistoryMapper) {
        this.csvReader = csvReader;
        this.csvWriter = csvWriter;
        this.roomHistoryDao = roomHistoryDao;
        this.roomHistoryMapper = roomHistoryMapper;
    }

    @Override
    public RoomHistory create(final RoomHistory history) throws PersistException {
        return roomHistoryDao.create(history);
    }

    @Override
    public RoomHistory findById(final Long id) throws EntityNotFoundException, PersistException {
        final RoomHistory history = roomHistoryDao.findById(id);
        if (history == null) {
            throw new EntityNotFoundException(String.format("No history with id %d%n", id));
        }
        return history;
    }

    @Override
    public void importHistories() throws PersistException {
        final List<RoomHistory> histories =
            ParseUtils.stringToEntities(csvReader.read(property), roomHistoryMapper, RoomHistory.class);
        roomHistoryDao.insertMany(histories);
    }

    @Override
    public void exportHistories() throws PersistException {
        csvWriter.write(property, ParseUtils.entitiesToCsv(roomHistoryMapper, roomHistoryDao.getAll()));
    }

    @Override
    public List<RoomHistory> showHistories() throws PersistException {
        return roomHistoryDao.getAll();
    }
}

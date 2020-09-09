package com.senla.hotel.service;

import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.annotation.PropertyLoad;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dao.interfaces.RoomHistoryDao;
import com.senla.hotel.dto.RoomHistoryDTO;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.exceptions.EntityIsEmptyException;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.mapper.interfaces.csvMapper.RoomHistoryMapper;
import com.senla.hotel.mapper.interfaces.dtoMapper.RoomHistoryDtoMapper;
import com.senla.hotel.service.interfaces.RoomHistoryService;
import com.senla.hotel.utils.ParseUtils;
import com.senla.hotel.utils.csv.interfaces.CsvReader;
import com.senla.hotel.utils.csv.interfaces.CsvWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class RoomHistoryServiceImpl implements RoomHistoryService {

    @Autowired
    private CsvReader csvReader;
    @Autowired
    private CsvWriter csvWriter;
    @PropertyLoad(propertyName = "histories")
    private String property;
    @Autowired
    private RoomHistoryDao roomHistoryDao;
    @Autowired
    private RoomHistoryMapper roomHistoryMapper;
    @Autowired
    private RoomHistoryDtoMapper roomHistoryDtoMapper;

    @Override
    public RoomHistoryDTO create(final RoomHistoryDTO history) throws PersistException, EntityIsEmptyException {
        RoomHistory entity = roomHistoryDtoMapper.sourceToDestination(history);
        RoomHistory roomHistory = roomHistoryDao.create(entity);
        return  roomHistoryDtoMapper.destinationToSource(roomHistory);
    }

    @Override
    public RoomHistoryDTO findById(final Long id) throws EntityNotFoundException, PersistException, EntityIsEmptyException {
        final RoomHistory history = roomHistoryDao.findById(id);
        if (history == null) {
            throw new EntityNotFoundException(String.format("No history with id %d%n", id));
        }
        return roomHistoryDtoMapper.destinationToSource(history);
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
    public List<RoomHistoryDTO> showHistories() throws PersistException, EntityIsEmptyException {
        List<RoomHistory> all = roomHistoryDao.getAll();
        List<RoomHistoryDTO> list = new ArrayList<>();
        for (RoomHistory roomHistory : all) {
            RoomHistoryDTO roomHistoryDTO = roomHistoryDtoMapper.destinationToSource(roomHistory);
            list.add(roomHistoryDTO);
        }
        return list;
    }
}

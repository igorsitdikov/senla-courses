package com.senla.hotel.service;

import com.senla.hotel.dao.interfaces.RoomHistoryDao;
import com.senla.hotel.dto.RoomHistoryDto;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.mapper.interfaces.csvMapper.RoomHistoryMapper;
import com.senla.hotel.mapper.interfaces.dtoMapper.RoomHistoryDtoMapper;
import com.senla.hotel.service.interfaces.RoomHistoryService;
import com.senla.hotel.utils.ParseUtils;
import com.senla.hotel.utils.csv.interfaces.CsvReader;
import com.senla.hotel.utils.csv.interfaces.CsvWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomHistoryServiceImpl implements RoomHistoryService {

    @Autowired
    private CsvReader csvReader;
    @Autowired
    private RoomHistoryDao roomHistoryDao;
    @Autowired
    private RoomHistoryMapper roomHistoryMapper;
    @Autowired
    private RoomHistoryDtoMapper roomHistoryDtoMapper;
    @Autowired
    private CsvWriter csvWriter;
    @Value("${histories:histories.csv}")
    private String property;

    @Override
    public RoomHistoryDto create(final RoomHistoryDto historyDto) throws PersistException {
        final RoomHistory history = roomHistoryDtoMapper.sourceToDestination(historyDto);
        final RoomHistory roomHistory = roomHistoryDao.create(history);
        return roomHistoryDtoMapper.destinationToSource(roomHistory);
    }

    @Override
    public RoomHistoryDto findById(final Long id) throws EntityNotFoundException, PersistException {
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
    public List<RoomHistoryDto> showHistories() throws PersistException {
        List<RoomHistory> histories = roomHistoryDao.getAll();
        return histories
            .stream()
            .map(roomHistoryDtoMapper::destinationToSource)
            .collect(Collectors.toList());
    }
}

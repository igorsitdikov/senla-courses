package com.senla.hotel.service;

import com.senla.anntotaion.Autowired;
import com.senla.anntotaion.CsvPath;
import com.senla.anntotaion.Singleton;
import com.senla.enumerated.Storage;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.exceptions.EntityNotFoundException;
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
    @CsvPath(Storage.HISTORIES)
    private String property;
    @Autowired
    private IRoomHistoryRepository roomHistoryRepository;

    public RoomHistoryService() {
        System.out.println("created " + RoomService.class);
    }

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
        final List<RoomHistory>
            histories = ParseUtils.stringToHistories(csvReader.read(property));
        roomHistoryRepository.setHistories(histories);
    }

    @Override
    public void exportHistories() {
        csvWriter.write(property, ParseUtils.historiesToCsv());
    }

    @Override
    public List<RoomHistory> showHistories() {
        return roomHistoryRepository.getHistories();
    }
}

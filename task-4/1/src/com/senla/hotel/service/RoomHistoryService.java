package com.senla.hotel.service;

import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.exceptions.NoSuchEntityException;
import com.senla.hotel.repository.RoomHistoryRepository;
import com.senla.hotel.repository.interfaces.IRoomHistoryRepository;
import com.senla.hotel.service.interfaces.IRoomHistoryService;
import com.senla.hotel.utils.ParseUtils;
import com.senla.hotel.utils.csv.writer.CsvWriter;

import java.util.List;

public class RoomHistoryService implements IRoomHistoryService {
    private static RoomHistoryService roomHistoryService;
    private IRoomHistoryRepository roomHistoryRepository = RoomHistoryRepository.getInstance();

    private RoomHistoryService() {
    }

    public static RoomHistoryService getInstance() {
        if (roomHistoryService == null) {
            roomHistoryService = new RoomHistoryService();
        }
        return roomHistoryService;
    }

    @Override
    public RoomHistory create(final RoomHistory history) {
        return (RoomHistory) roomHistoryRepository.add(history);
    }

    @Override
    public RoomHistory findById(final Long id) throws NoSuchEntityException {
        RoomHistory history = (RoomHistory) roomHistoryRepository.findById(id);
        if (history == null) {
            throw new NoSuchEntityException(String.format("No history with id %d%n", id));
        }
        return history;
    }

    @Override
    public void importHistories(List<RoomHistory> histories) {
        roomHistoryRepository.setHistories(histories);
    }

    @Override
    public void exportHistories() {
        CsvWriter.getInstance().write("histories", ParseUtils.historiesToCsv());
    }

    @Override
    public List<RoomHistory> showHistories() {
        return roomHistoryRepository.getHistories();
    }
}

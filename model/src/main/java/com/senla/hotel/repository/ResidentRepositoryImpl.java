package com.senla.hotel.repository;

import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dao.interfaces.ResidentDao;
import com.senla.hotel.entity.AEntity;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.repository.interfaces.ResidentRepository;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class ResidentRepositoryImpl implements ResidentRepository {
    @Autowired
    private ResidentDao residentDao;

    private static List<Resident> residents = new ArrayList<>();

    @Override
    public AEntity create(final AEntity entity) {
        try {
            return residentDao.create((Resident) entity);
        } catch (PersistException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(final Resident resident) {
        try {
            residentDao.update(resident);
        } catch (PersistException e) {
            e.printStackTrace();
        }
    }

    @Override
    public AEntity findById(final Long id) throws EntityNotFoundException {
        try {
            return residentDao.findById(id);
        } catch (PersistException e) {
            e.printStackTrace();
        }
        throw new EntityNotFoundException(String.format("No resident with id %d%n", id));
    }

    @Override
    public int showTotalNumber() {
        return residents.size();
    }

    @Override
    public void setResidents(final List<Resident> residents) {
//        ResidentRepositoryImpl.residents = new ArrayList<>(residents);
    }

    @Override
    public List<Resident> getAll() {
        try {
            return residentDao.getAll();
        } catch (PersistException e) {
            e.printStackTrace();
        }
        return null;
    }
}

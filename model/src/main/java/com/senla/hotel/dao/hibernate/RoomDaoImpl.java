package com.senla.hotel.dao.hibernate;

import com.senla.hotel.dao.interfaces.RoomDao;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.enumerated.HistoryStatus;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.utils.HibernateUtil;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public class RoomDaoImpl extends AbstractDao<Room, Long> implements RoomDao {

    public RoomDaoImpl(final HibernateUtil hibernateUtil) {
        super(hibernateUtil);
    }

    @Override
    public List<Room> getVacantRooms(final SortField sortField) throws PersistException {
        try (Session session = hibernateUtil.getSessionFactory().openSession()) {
            return getAllBy("status", RoomStatus.VACANT, sortField, session);
        } catch (Exception e) {
            throw new PersistException(e.getMessage());
        }
    }

    @Override
    public Room findByNumber(final Integer number) throws PersistException {
        try (Session session = hibernateUtil.getSessionFactory().openSession()) {
            return getSingleBy("number", number, session);
        } catch (Exception e) {
            throw new PersistException(e.getMessage());
        }
    }

    @Override
    public List<Room> getVacantOnDate(final LocalDate date) throws PersistException {
        try (Session session = hibernateUtil.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Room> criteria = builder.createQuery(Room.class);
            Root<Room> root = criteria.from(Room.class);
            Join<Room, RoomHistory> historyJoin = root.join("histories", JoinType.LEFT);
            Predicate equalRoomVacant = builder.equal(root.get("status"), RoomStatus.VACANT);
            Predicate equalHistoryCheckedOut = builder.equal(historyJoin.get("status"), HistoryStatus.CHECKED_OUT);
            Predicate lessCheckOutDate = builder.lessThan(historyJoin.get("checkOut"), Date.valueOf(date));
            criteria.select(root).where(builder.or(equalRoomVacant, equalHistoryCheckedOut, lessCheckOutDate));
            return session.createQuery(criteria).getResultList();
        } catch (Exception e) {
            throw new PersistException(e.getMessage());
        }
    }

    @Override
    public Long countVacantRooms() throws PersistException {
        try (Session session = hibernateUtil.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
            Root<Room> root = criteria.from(Room.class);
            Predicate equalRoomVacant = builder.equal(root.get("status"), RoomStatus.VACANT);
            criteria
                    .select(builder.countDistinct(root))
                    .where(equalRoomVacant);
            return session.createQuery(criteria).getSingleResult();
        } catch (Exception e) {
            throw new PersistException(e.getMessage());
        }
    }

    @Override
    public List<Room> getAllSortedBy(final SortField sortField) throws PersistException {
        return super.getAllSortedBy(sortField);
    }
}

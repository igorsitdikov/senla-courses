package com.senla.hotel.dao.hibernate;

import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dao.interfaces.RoomDao;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.enumerated.HistoryStatus;
import com.senla.hotel.enumerated.RoomStatus;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Singleton
public class RoomDaoImpl extends AbstractDao<Room, Long> implements RoomDao {

    public RoomDaoImpl(final HibernateUtil hibernateUtil) {
        super(hibernateUtil);
    }

    @Override
    public List<Room> getVacantRooms() throws PersistException {
        return getAllWhere("status", RoomStatus.VACANT);
    }

    @Override
    public Room findByNumber(final Integer number) throws PersistException {
        return getBy("number", number);
    }

    @Override
    public List<Room> getVacantOnDate(final LocalDate date) throws PersistException {
        SessionFactory factory = hibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Room> criteria = builder.createQuery(Room.class);
            Root<Room> root = criteria.from(Room.class);
            Join<Room, RoomHistory> historyJoin = root.join("histories", JoinType.LEFT);
            Predicate equalRoomVacant = builder.equal(root.get("status"), RoomStatus.VACANT);
            Predicate equalHistoryCheckedOut = builder.equal(historyJoin.get("status"), HistoryStatus.CHECKED_OUT);
            Predicate lessCheckOutDate = builder.lessThan(historyJoin.get("checkOut"), Date.valueOf(date));
            criteria.select(root).where(builder.or(equalRoomVacant, equalHistoryCheckedOut, lessCheckOutDate));
            return session.createQuery(criteria).getResultList();
        }
    }
}

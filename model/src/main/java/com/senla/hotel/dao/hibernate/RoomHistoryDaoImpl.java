package com.senla.hotel.dao.hibernate;

import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dao.interfaces.RoomHistoryDao;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.enumerated.HistoryStatus;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.List;

@Singleton
public class RoomHistoryDaoImpl extends AbstractDao<RoomHistory, Long> implements RoomHistoryDao {

    public RoomHistoryDaoImpl(final HibernateUtil hibernateUtil) {
        super(hibernateUtil);
    }

    @Override
    public RoomHistory getByResidentAndCheckedInStatus(final Long id) throws PersistException, EntityNotFoundException {
        SessionFactory factory = hibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<RoomHistory> criteria = builder.createQuery(RoomHistory.class);
            Root<RoomHistory> root = criteria.from(RoomHistory.class);
            Predicate equalStatusCheckedIn = builder.equal(root.get("status"), HistoryStatus.CHECKED_IN);
            Predicate equalResidentId = builder.equal(root.get("resident").get("id"), id);
            criteria.select(root).where(builder.and(equalResidentId, equalStatusCheckedIn));
            return session.createQuery(criteria).getSingleResult();
        }
    }

    @Override
    public BigDecimal calculateBill(final Long id) throws PersistException {
        SessionFactory factory = hibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);
            Root<RoomHistory> root = criteria.from(RoomHistory.class);
            Join<Attendance, RoomHistory> attendanceJoin = root.join("attendances", JoinType.LEFT);
            Expression<Integer> diff = builder.function("DATEDIFF", Integer.class, root.get("checkOut"), root.get("checkIn"));
            Expression<Number> roomPrice = root.get("room").get("price");
            Expression<Number> totalRoomOnly = builder.prod(diff, roomPrice);
            Expression<Number> allAttendancesPerDay = builder.sum(attendanceJoin.get("price"));
            Expression<Number> roomWithAttendancesPrice = builder.sum(roomPrice, allAttendancesPerDay);
            Expression<Number> total = builder.prod(diff, roomWithAttendancesPrice);
            Predicate equalStatusCheckedIn = builder.equal(root.get("status"), HistoryStatus.CHECKED_IN);
            Predicate equalId = builder.equal(root.get("id"), id);
            criteria
                    .multiselect(totalRoomOnly, total)
                    .where(builder.and(equalId, equalStatusCheckedIn));
            List<Object[]> result = session.createQuery(criteria).getResultList();
            if (result.size() == 0) {
                throw new PersistException("Received no records.");
            }
            if (result.size() > 1) {
                throw new PersistException("Received more than one record.");
            }
            if (result.get(0)[1] != null) {
                return (BigDecimal) result.get(0)[1];
            } else {
                return (BigDecimal) result.get(0)[0];
            }
        }
    }

    @Override
    public void addAttendanceToHistory(final RoomHistory history, final Attendance attendance) throws PersistException {
        SessionFactory factory = hibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<RoomHistory> criteria = builder.createQuery(RoomHistory.class);
            Root<RoomHistory> root = criteria.from(RoomHistory.class);
            criteria.select(root).where(builder.equal(root.get("id"), history.getId()));
            Join<Attendance, RoomHistory> attendanceJoin = root.join("attendances", JoinType.LEFT);
            RoomHistory roomHistory = session.createQuery(criteria).getSingleResult();

            session.beginTransaction();

            roomHistory.addAttendance(attendance);
            session.save(roomHistory);
            session.getTransaction().commit();
//            update(roomHistory);
        }
    }
}

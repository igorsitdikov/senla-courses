package com.senla.hotel.dao.hibernate;

import com.senla.hotel.dao.interfaces.AttendanceDao;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.utils.HibernateUtil;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class AttendanceDaoImpl extends AbstractDao<Attendance, Long> implements AttendanceDao {

    public AttendanceDaoImpl(final HibernateUtil hibernateUtil) {
        super(hibernateUtil);
    }

    @Override
    public List<Attendance> getAllAttendancesByHistoryId(Long id) throws PersistException {
        try (Session session = hibernateUtil.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Attendance> criteria = builder.createQuery(Attendance.class);
            Root<Attendance> root = criteria.from(Attendance.class);
            Join<Attendance, RoomHistory> historyJoin = root.join("histories", JoinType.LEFT);
            criteria.select(root).where(builder.equal(historyJoin.get("id"), id));
            return session.createQuery(criteria).getResultList();
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    public List<Attendance> getAllSortedBy(final SortField sortField) throws PersistException {
        return super.getAllSortedBy(sortField);
    }
}

package com.senla.hotel.dao.hibernate;

import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dao.interfaces.ResidentDao;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.enumerated.HistoryStatus;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;

//@Singleton
public class ResidentDaoImpl extends AbstractDao<Resident, Long> implements ResidentDao {

    public ResidentDaoImpl(final HibernateUtil hibernateUtil) {
        super(hibernateUtil);
    }

    @Override
    public Resident findById(final Long id) throws PersistException {
        try (Session session = hibernateUtil.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Resident> criteria = builder.createQuery(Resident.class);
            Root<Resident> root = criteria.from(Resident.class);
            root.join("history", JoinType.LEFT);
            criteria.select(root).where(builder.and(builder.equal(root.get("id"), id)));
            Query<Resident> query = session.createQuery(criteria);
            return query.getSingleResult();
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    public List<Resident> getLastResidentsByRoomId(final Long roomId, final Integer limit) throws PersistException {
        try (Session session = hibernateUtil.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<RoomHistory> criteria = builder.createQuery(RoomHistory.class);
            Root<RoomHistory> root = criteria.from(RoomHistory.class);
            Predicate equalRoomId = builder.equal(root.get("room").get("id"), roomId);
            Predicate equalCheckOutStatus = builder.equal(root.get("status"), HistoryStatus.CHECKED_OUT);
            criteria.select(root).where(builder.and(equalRoomId, equalCheckOutStatus)).orderBy(builder.desc(root.get("checkOut")));
            List<RoomHistory> data = session.createQuery(criteria).setMaxResults(limit).getResultList();
            return data.stream().map(RoomHistory::getResident).collect(Collectors.toList());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    public Long getTotalResidents() throws PersistException {
        try (Session session = hibernateUtil.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
            Root<Resident> root = criteria.from(Resident.class);
            criteria.select(builder.countDistinct(root));
            return session.createQuery(criteria).getSingleResult();
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    public List<Resident> getAllSortedBy(final SortField sortField) throws PersistException {
        return super.getAllSortedBy(sortField);
    }
}

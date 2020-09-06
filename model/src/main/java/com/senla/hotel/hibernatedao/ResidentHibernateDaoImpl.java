package com.senla.hotel.hibernatedao;

import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.enumerated.HistoryStatus;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.hibernatedao.interfaces.ResidentHibernateDao;
import com.senla.hotel.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class ResidentHibernateDaoImpl extends AbstractHibernateDao<Resident, Long> implements ResidentHibernateDao {

    public ResidentHibernateDaoImpl(final HibernateUtil hibernateUtil) {
        super(hibernateUtil);
    }

    @Override
    public List<Resident> getLastResidentsByRoomId(final Long roomId, final Integer limit) throws PersistException {
        SessionFactory factory = hibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<RoomHistory> criteria = builder.createQuery(RoomHistory.class);
            Root<RoomHistory> root = criteria.from(RoomHistory.class);
            Predicate equalRoomId = builder.equal(root.get("room").get("id"), roomId);
            Predicate equalCheckOutStatus = builder.equal(root.get("status"), HistoryStatus.CHECKED_OUT);
            criteria.select(root).where(builder.and(equalRoomId, equalCheckOutStatus)).orderBy(builder.desc(root.get("checkOut")));
            List<RoomHistory> data = session.createQuery(criteria).setMaxResults(limit).getResultList();
            List<Resident> residents = data.stream().map(RoomHistory::getResident).collect(Collectors.toList());
            return residents;
        }
    }
}

package com.senla.hotel.hibernatedao;

import com.senla.hotel.annotation.Autowired;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dao.interfaces.AttendanceDao;
import com.senla.hotel.entity.RoomHistory;
import com.senla.hotel.enumerated.HistoryStatus;
import com.senla.hotel.exceptions.EntityNotFoundException;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.hibernatedao.interfaces.RoomHistoryHibernateDao;
import com.senla.hotel.mapper.interfaces.resultSetMapper.RoomHistoryResultSetMapper;
import com.senla.hotel.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;

@Singleton
public class RoomHistoryHibernateDaoImpl extends AbstractHibernateDao<RoomHistory, Long> implements RoomHistoryHibernateDao {

    @Autowired
    private RoomHistoryResultSetMapper mapper;

    @Autowired
    private AttendanceDao attendanceDao;

    public RoomHistoryHibernateDaoImpl(final HibernateUtil hibernateUtil) {
        super(hibernateUtil);
    }
//
//    @Override
//    public String getSelectQuery() {
//        return "SELECT  room.id AS rm_id,\n" +
//                "       room.number AS rm_number,\n" +
//                "       room.price AS rm_price,\n" +
//                "       room.status AS rm_status,\n" +
//                "       room.stars AS rm_stars,\n" +
//                "       room.accommodation AS rm_accommodation, " +
//                "       resident.id         AS rt_id,\n" +
//                "       resident.first_name AS rt_first_name,\n" +
//                "       resident.last_name  AS rt_last_name,\n" +
//                "       resident.gender     AS rt_gender,\n" +
//                "       resident.vip        AS rt_vip,\n" +
//                "       resident.phone      AS rt_phone,\n" +
//                "       history.id          AS h_id,\n" +
//                "       history.room_id     AS h_room_id,\n" +
//                "       history.resident_id AS h_resident_id,\n" +
//                "       history.check_in    AS h_check_in,\n" +
//                "       history.check_out   AS h_check_out,\n" +
//                "       history.status      AS h_status\n" +
//                "FROM history JOIN resident " +
//                "  ON resident.id = history.resident_id " +
//                "JOIN room ON room.id = history.room_id ";
//    }
//
//    @Override
//    public String getCreateQuery() {
//        return "INSERT INTO history (room_id, resident_id, check_in, check_out, status) VALUES (?, ?, ?, ?, ?)";
//    }
//
//    public String getCreateQueryAttendanceToHistory() {
//        return "INSERT INTO histories_attendances (history_id, attendance_id) VALUES (?, ?);";
//    }
//
//    @Override
//    public String getUpdateQuery() {
//        return "UPDATE history SET room_id = ?, resident_id = ?, check_in = ?, check_out = ?, status = ? WHERE id = ?;";
//    }
//
//    public String getCalculateBillQuery() {
//        return  "SELECT SUM(DATEDIFF(check_out, check_in) * room.price) as room_price,\n" +
//                "       (SUM(attendance.price * DATEDIFF(check_out, check_in)) +\n" +
//                "        DATEDIFF(check_out, check_in) * room.price)    AS room_price_with_attendances\n" +
//                "FROM history\n" +
//                "         LEFT JOIN resident ON resident.id = history.resident_id\n" +
//                "         LEFT JOIN room ON room.id = history.room_id\n" +
//                "         LEFT JOIN histories_attendances on history.id = histories_attendances.history_id\n" +
//                "         LEFT JOIN attendance on histories_attendances.attendance_id = attendance.id\n" +
//                "WHERE history.id = ?\n" +
//                "  AND history.status = 'CHECKED_IN';";
//    }
//
//    @Override
//    public String getDeleteQuery() {
//        return "DELETE FROM history WHERE id = ?";
//    }

//    @Override
//    protected List<RoomHistory> parseResultSet(final ResultSet rs) throws PersistException {
//        final LinkedList<RoomHistory> result = new LinkedList<>();
//        try {
//            while (rs.next()) {
//                final RoomHistory roomHistory = mapper.sourceToDestination(rs);
//                final List<Attendance> attendances = attendanceDao.getAllAttendancesByHistoryId(roomHistory.getId());
//                roomHistory.setAttendances(attendances);
//                result.add(roomHistory);
//            }
//        } catch (final Exception e) {
//            throw new PersistException(e);
//        }
//        return result;
//    }

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
            RoomHistory data = session.createQuery(criteria).getSingleResult();
            System.out.println();
            return data;
        }
    }

//    @Override
//    public RoomHistory findById(final Long id) throws PersistException {
//        return getBy("history.id", id);
//    }

    @Override
    public BigDecimal calculateBill(final Long id) throws PersistException {
//        final List<Double> result = new LinkedList<>();
//        final String sql = getCalculateBillQuery();
//        try (PreparedStatement statement = connector.getConnection().prepareStatement(sql)) {
//            setVariableToStatement(statement, id);
//            final ResultSet rs = statement.executeQuery();
//            while (rs.next()) {
//                Double result1 = rs.getDouble("room_price_with_attendances");
//                if (rs.wasNull()) {
//                    result1 = rs.getDouble("room_price");
//                }
//                result.add(result1);
//            }
//        } catch (final Exception e) {
//            throw new PersistException(e);
//        }
//        if (result.size() == 0) {
//            throw new PersistException("Received no records.");
//        }
//        if (result.size() > 1) {
//            throw new PersistException("Received more than one record.");
//        }
//        return BigDecimal.valueOf(result.iterator().next());
        return null;
    }

    @Override
    public void addAttendanceToHistory(final Long historyId, final Long attendanceId) throws PersistException {



//        final String sql = getCreateQueryAttendanceToHistory();
//        try (PreparedStatement statement = connector.getConnection()
//                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//            setVariableToStatement(statement, historyId, attendanceId);
//            final int count = statement.executeUpdate();
//            if (count != 1) {
//                throw new PersistException("On persist modify more then 1 record: " + count);
//            }
//        } catch (final SQLIntegrityConstraintViolationException e) {
//            throw new PersistException("Attendance already added!");
//        } catch (final Exception e) {
//            throw new PersistException(e.getMessage());
//        }
    }
//
//    @Override
//    protected void prepareStatementForInsert(final PreparedStatement statement, final RoomHistory object) throws PersistException {
//        try {
//            statement.setLong(1, object.getRoom().getId());
//            statement.setLong(2, object.getResident().getId());
//            statement.setDate(3, Date.valueOf(object.getCheckIn()));
//            statement.setDate(4, Date.valueOf(object.getCheckOut()));
//            statement.setString(5, object.getStatus().toString());
//        } catch (final Exception e) {
//            throw new PersistException(e);
//        }
//    }
//
//    @Override
//    protected void prepareStatementForUpdate(final PreparedStatement statement, final RoomHistory object) throws PersistException {
//        try {
//            statement.setLong(1, object.getRoom().getId());
//            statement.setLong(2, object.getResident().getId());
//            statement.setDate(3, Date.valueOf(object.getCheckIn()));
//            statement.setDate(4, Date.valueOf(object.getCheckOut()));
//            statement.setString(5, object.getStatus().toString());
//            statement.setLong(6, object.getId());
//        } catch (final Exception e) {
//            throw new PersistException(e);
//        }
//    }
}

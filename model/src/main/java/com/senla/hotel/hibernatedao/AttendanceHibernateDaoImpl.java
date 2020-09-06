package com.senla.hotel.hibernatedao;

import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.hibernatedao.interfaces.AttendanceHibernateDao;
import com.senla.hotel.utils.HibernateUtil;

@Singleton
public class AttendanceHibernateDaoImpl extends AbstractHibernateDao<Attendance, Long> implements
                                                                                       AttendanceHibernateDao {
    public AttendanceHibernateDaoImpl(final HibernateUtil hibernateUtil) {
        super(hibernateUtil);
    }
}

package com.senla.hotel.utils;

import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.entity.Resident;
import com.senla.hotel.entity.Room;
import com.senla.hotel.entity.RoomHistory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

@Singleton
public class HibernateUtil {

    private static final Logger logger = LogManager.getLogger(HibernateUtil.class);
    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    public HibernateUtil() {
        init();
    }

    private void init() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                configuration.addAnnotatedClass(Attendance.class);
                configuration.addAnnotatedClass(RoomHistory.class);
                configuration.addAnnotatedClass(Room.class);
                configuration.addAnnotatedClass(Resident.class);
                logger.info("Hibernate configuration has been loaded!");
                registry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();
                logger.info("Hibernate ServiceRegistry has been created!");
                sessionFactory = configuration.buildSessionFactory(registry);
            } catch (final Exception e) {
                logger.error(e.getMessage());
                shutdown();
            }
        }
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public Session openSession() {
        return sessionFactory.openSession();
    }

    public void closeSession(final Session session) {
        session.close();
    }

    public void shutdown() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}

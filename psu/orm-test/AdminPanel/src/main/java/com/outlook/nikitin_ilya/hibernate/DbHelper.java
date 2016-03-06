package com.outlook.nikitin_ilya.hibernate;

import org.hibernate.Session;
import java.util.List;

/**
 * Created by Ilya on 06.03.2016.
 */
public enum DbHelper {
    INSTANCE;

    public List<UserEntity> getUsersData() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from UserEntity").list();
        }
    }

    public List<CategoryEntity> getCategoryData() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from CategoryEntity").list();
        }
    }


}

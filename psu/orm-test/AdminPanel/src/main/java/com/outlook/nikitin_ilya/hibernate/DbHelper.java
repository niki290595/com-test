package com.outlook.nikitin_ilya.hibernate;

import org.hibernate.Session;

import java.util.Calendar;
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

    public UserEntity addUser(String login, String pass, CategoryEntity category, String salt) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            UserEntity user = new UserEntity(login, pass, category, salt, new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            user.setId((Integer) session.save(user));
            session.getTransaction().commit();
            return user;
        }
    }

    public List<CategoryEntity> getCategoryData() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from CategoryEntity").list();
        }
    }


}

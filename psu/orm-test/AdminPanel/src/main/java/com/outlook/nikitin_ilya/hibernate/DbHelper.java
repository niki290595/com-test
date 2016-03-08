package com.outlook.nikitin_ilya.hibernate;

import org.hibernate.Session;

import java.sql.Date;
import java.util.Calendar;
import java.util.Collection;

/**
 * Created by Ilya on 06.03.2016.
 */
public enum DbHelper {
    INSTANCE;

    public Collection getUsersData() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createCriteria(UserEntity.class).list();
        }
    }

    public UserEntity addUser(String login, String pass, CategoryEntity category, String salt) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            UserEntity user = new UserEntity(login, pass, category, salt, new Date(Calendar.getInstance().getTime().getTime()));
            user.setId((Integer) session.save(user));
            session.getTransaction().commit();
            return user;
        }
    }

    public UserEntity addUser(String login, CategoryEntity category) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            UserEntity user = new UserEntity();
            user.setLogin(login);
            user.setCategory(category);
            user.setDateCreation(new Date(Calendar.getInstance().getTime().getTime()));
            user.setId((Integer) session.save(user));
            session.getTransaction().commit();
            return user;
        }
    }

    public UserEntity editUser(UserEntity user, String newLogin, CategoryEntity newCategory) {
        return editUser(user, newLogin, null, newCategory, null);
    }

    public UserEntity editUser(UserEntity user, String pass, String salt) {
        return editUser(user, null, pass, null, salt);
    }

    private UserEntity editUser(UserEntity user, String login, String pass, CategoryEntity category, String salt) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            user = session.get(UserEntity.class, user.getId());
            if (login != null) user.setLogin(login);
            if (pass != null) user.setPass(pass);
            if (category != null) user.setCategory(category);
            if (salt != null) user.setSalt(salt);
            user.setDateModification(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            session.getTransaction().commit();
            return user;
        }
    }

    public void removeUser(UserEntity user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        }
    }

    public Collection getCategoryData() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createCriteria(CategoryEntity.class).list();
        }
    }
            /*
            //return session.createQuery("from CategoryEntity").list();
        }
    }*/



}

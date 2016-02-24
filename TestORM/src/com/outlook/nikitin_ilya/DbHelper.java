package com.outlook.nikitin_ilya;

import com.outlook.nikitin_ilya.cryptography.SaltGenerator;
import com.outlook.nikitin_ilya.hibernate.CategoryEntity;
import com.outlook.nikitin_ilya.hibernate.HibernateUtil;
import com.outlook.nikitin_ilya.hibernate.UserEntity;
import org.hibernate.Session;

import java.util.Calendar;
import java.util.List;

public class DbHelper {
    private static DbHelper instance;

    private DbHelper() {
    }

    public static DbHelper getInstance() {
        return instance == null ? (instance = new DbHelper()) : instance;
    }

    public UserEntity getUser(String login) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(UserEntity.class, login);
        }

    }

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

    public UserEntity addUser(String login, String pass, CategoryEntity category, String salt) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            UserEntity user = new UserEntity(login, pass, category, salt, new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            session.save(user);
            session.getTransaction().commit();
            return user;
        }
    }

    public UserEntity editUser(UserEntity user, String newPass, CategoryEntity newCategory, String newSalt) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            user = session.get(UserEntity.class, user.getLogin());
            user.setPass(newPass);
            user.setCategory(newCategory);
            user.setSalt(newSalt);
            user.setDateModification(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            session.getTransaction().commit();
            return user;
        }
    }
/*
    public UserEntity editUser(UserEntity user, CategoryEntity newCategory, String newSalt) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            user = session.get(UserEntity.class, user.getLogin());
            user.setPass(newPass);
            user.setCategory(newCategory);
            user.setSalt(newSalt);
            user.setDateModification(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            session.getTransaction().commit();
            return user;
        }
    }
*/
    public void removeUser(UserEntity user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        }
    }
}

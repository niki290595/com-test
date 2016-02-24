package com.outlook.nikitin_ilya.hibernate;


import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Ilya on 17.02.2016.
 */
@Entity
@Table(name = "user", schema = "usersdb", catalog = "")
public class UserEntity implements Comparable<UserEntity>{
    private String login;
    private String pass;
    private CategoryEntity category;
    private String salt;
    private Date dateCreation;
    private Date dateModification;

    public UserEntity() {}

    public UserEntity(String login, String pass, CategoryEntity category, String salt, Date dateCreation) {
        this.login = login;
        this.pass = pass;
        this.category = category;
        this.salt = salt;
        this.dateCreation = dateCreation;
    }

    public UserEntity(String login, String pass, CategoryEntity category, String salt) {
        this.login = login;
        this.pass = pass;
        this.category = category;
        this.salt = salt;
    }

    @Id
    @Column(name = "login", nullable = false, length = 30)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "pass", nullable = true, length = 100)
    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @ManyToOne
    @JoinColumn(name = "idcategory")
    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    @Column(name = "salt")
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Column(name = "dateCreation")
    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Column(name = "dateModification")
    public Date getDateModification() {
        return dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (login != null ? !login.equals(that.login) : that.login != null) return false;
        return pass != null ? pass.equals(that.pass) : that.pass == null;
    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + (pass != null ? pass.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return getLogin();
    }

    @Override
    public int compareTo(UserEntity user) {
        return this.getLogin().compareTo(user.getLogin());
    }
}

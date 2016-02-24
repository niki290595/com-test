package com.outlook.nikitin_ilya.hibernate;

import javax.persistence.*;

@Entity
@Table(name = "category", schema = "usersdb", catalog = "")
public class CategoryEntity implements Comparable<CategoryEntity>{
    private int id;
    private String description;

    public enum Description {
        ADMIN, OWNER, STAFF;

        static int admin = "admin".hashCode();
        static int owner = "owner".hashCode();
        static int staff = "staff".hashCode();

        public static Description getRule(String s) {
            int hashCode = s.hashCode();
            if (hashCode == admin) return ADMIN;
            if (hashCode == owner) return OWNER;
            if (hashCode == staff) return STAFF;
            return null;
        }
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 15)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoryEntity that = (CategoryEntity) o;

        if (id != that.id) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return getDescription();
    }

    @Override
    public int compareTo(CategoryEntity o) {
        return this.getDescription().compareTo(o.getDescription());
    }

}

package com.outlook.nikitin_ilya.hibernate;

import javax.persistence.*;

/**
 * Created by Ilya on 06.03.2016.
 */
@Entity
@Table(name = "category", schema = "usersdb")
public class CategoryEntity implements Comparable<CategoryEntity> {
    private Integer id;
    private String description;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return description != null ? description.equals(that.description) : that.description == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return description;
    }

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

    public Description descriptionCONST() {
        int hashCode = this.getDescription().hashCode();
        if (hashCode == Description.admin) return Description.ADMIN;
        if (hashCode == Description.owner) return Description.OWNER;
        if (hashCode == Description.staff) return Description.STAFF;
        return null;
    }

    @Override
    public int compareTo(CategoryEntity o) {
        return this.getDescription().compareTo(o.getDescription());
    }
}

package com.outlook.nikitin_ilya.hibernate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collections;
import java.util.List;

/**
 * Created by Ilya on 06.03.2016.
 */
public enum Main {
    INSTANCE;

    private static DbHelper dbHelper;
    private ObservableList<UserEntity> userData;
    private ObservableList<CategoryEntity> categoryData;

    static {
        dbHelper = DbHelper.INSTANCE;
    }

    public ObservableList<UserEntity> getUserData() {
        return userData == null ? initUserData() : userData;
    }

    private ObservableList addCollection(List list) {
        ObservableList observableList = FXCollections.observableArrayList();
        observableList.addAll(list);
        Collections.sort(observableList);
        return observableList;
    }

    private ObservableList<UserEntity> initUserData() {
        userData = addCollection(dbHelper.getUsersData());
        return userData;
        /*
        userData = FXCollections.observableArrayList();
        userData.addAll(dbHelper.getUsersData());
        Collections.sort(userData);
        return userData;*/
    }

    public UserEntity getUser(String login) {
        for (UserEntity user : getUserData()) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }

    public UserEntity addUser(String login, String hash, CategoryEntity category, String salt) {
        UserEntity newUser = dbHelper.addUser(login, hash, category, salt);
        userData.add(newUser);
        Collections.sort(userData);
        return newUser;
    }

    public ObservableList<CategoryEntity> getCategoryData() {
        return categoryData == null ? initCategoryData() : categoryData;
    }

    private ObservableList<CategoryEntity> initCategoryData() {
        categoryData = addCollection(dbHelper.getCategoryData());
        return categoryData;
        /*
        categoryData = FXCollections.observableArrayList();
        categoryData.addAll(dbHelper.getCategoryData());
        Collections.sort(categoryData);
        return categoryData;*/
    }

}

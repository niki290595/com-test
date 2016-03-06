package com.outlook.nikitin_ilya.hibernate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collections;

/**
 * Created by Ilya on 06.03.2016.
 */
public enum Main {
    INSTANCE;

    private static DbHelper dbHelper;
    ObservableList<UserEntity> userData;

    static {
        dbHelper = DbHelper.INSTANCE;
    }

    public ObservableList<UserEntity> getUserData() {
        return userData == null ? initUserData() : userData;
    }

    private ObservableList<UserEntity> initUserData() {
        userData = FXCollections.observableArrayList();
        userData.addAll(dbHelper.getUsersData());
        Collections.sort(userData);
        return userData;
    }
}

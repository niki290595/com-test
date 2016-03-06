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

    private void normalize(ObservableList observableList, List list) {
        userData = FXCollections.observableArrayList();
        userData.addAll(dbHelper.getUsersData());
        Collections.sort(userData);
    }

    private ObservableList<UserEntity> initUserData() {
        userData = FXCollections.observableArrayList();
        userData.addAll(dbHelper.getUsersData());
        Collections.sort(userData);
        return userData;
    }

    public ObservableList<CategoryEntity> getCategoryData() {
        return categoryData == null ? initCategoryData() : categoryData;
    }

    private ObservableList<CategoryEntity> initCategoryData() {
        categoryData = FXCollections.observableArrayList();
        categoryData.addAll(dbHelper.getCategoryData());
        Collections.sort(categoryData);
        return categoryData;
    }
}

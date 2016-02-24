package com.outlook.nikitin_ilya.hibernate;

import com.outlook.nikitin_ilya.DbHelper;
import com.outlook.nikitin_ilya.cryptography.HashText;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collections;

public class Main {
    private static Main instance;
    private ObservableList<UserEntity> userData;
    private ObservableList<CategoryEntity> categoryData;

    private Main() {}

    public static Main getInstance() {
        return instance == null ? (instance = new Main()) : instance;
    }

    public ObservableList<UserEntity> getUserData() {
        return userData == null ? initUserData() : userData;
    }

    private ObservableList<UserEntity> initUserData() {
        userData = FXCollections.observableArrayList();
        userData.addAll(DbHelper.getInstance().getUsersData());
        Collections.sort(userData);
        return userData;
    }

    public UserEntity getUser(String login) {
        for (UserEntity userEntity : getUserData()) if (userEntity.getLogin().equals(login)) return userEntity;
        return null;
    }

    public UserEntity addUser(String login, String pass, CategoryEntity category, String salt) {
        UserEntity newUser = DbHelper.getInstance().addUser(login, HashText.sha256(pass, salt), category, salt);
        userData.add(newUser);
        Collections.sort(userData);
        return newUser;
    }

    public void editUser(UserEntity user, String newPass, CategoryEntity newCategory, String newSalt) {
        UserEntity alterUser = DbHelper.getInstance().editUser(user, HashText.sha256(newPass, newSalt), newCategory, newSalt);
        userData.remove(user);
        userData.add(alterUser);
        Collections.sort(userData);
    }
/*
    public void editUser(UserEntity user, CategoryEntity newCategory, String newSalt) {
        UserEntity alterUser = DbHelper.getInstance().editUser(user, newCategory, newSalt);
        userData.remove(user);
        userData.add(alterUser);
        Collections.sort(userData);
    }
*/

    public void removeUser(UserEntity user) {
        DbHelper.getInstance().removeUser(user);
        userData.remove(user);
    }

    public ObservableList<CategoryEntity> getCategoryData() {
        return categoryData == null ? initCategoryData() : categoryData;
    }

    private ObservableList<CategoryEntity> initCategoryData() {
        categoryData = FXCollections.observableArrayList();
        categoryData.addAll(DbHelper.getInstance().getCategoryData());
        Collections.sort(categoryData);
        return categoryData;
    }
}

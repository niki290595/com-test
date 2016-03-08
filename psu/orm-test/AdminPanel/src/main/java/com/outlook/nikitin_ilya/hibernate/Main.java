package com.outlook.nikitin_ilya.hibernate;

import com.outlook.nikitin_ilya.cryptography.HashText;
import com.outlook.nikitin_ilya.cryptography.SaltGenerator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collection;
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

    private ObservableList addCollection(Collection collection) {
        ObservableList observableList = FXCollections.observableArrayList();
        observableList.addAll(collection);
        Collections.sort(observableList);
        return observableList;
    }

    private ObservableList<UserEntity> initUserData() {
        userData = addCollection(dbHelper.getUsersData());
        return userData;
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

    public UserEntity addUser(String login, CategoryEntity category) {
        UserEntity newUser = dbHelper.addUser(login, category);
        userData.add(newUser);
        Collections.sort(userData);
        return newUser;
    }

    public void editUser(UserEntity user, String newLogin, CategoryEntity newCategory) {
        UserEntity alterUser = dbHelper.editUser(user, newLogin, newCategory);
        userData.remove(user);
        userData.add(alterUser);
        Collections.sort(userData);
    }


    public void editUser(UserEntity user, String pass) {
        String salt = SaltGenerator.generate();
        UserEntity alterUser = dbHelper.editUser(user, HashText.getHash(pass, salt), salt);
        userData.remove(user);
        userData.add(alterUser);
        Collections.sort(userData);
    }

    public void removeUser(UserEntity user) {
        dbHelper.removeUser(user);
        userData.remove(user);
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

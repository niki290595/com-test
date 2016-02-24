package com.outlook.nikitin_ilya.controllers;

import com.outlook.nikitin_ilya.DialogHelper;
import com.outlook.nikitin_ilya.cryptography.SaltGenerator;
import com.outlook.nikitin_ilya.hibernate.CategoryEntity;
import com.outlook.nikitin_ilya.hibernate.Main;
import com.outlook.nikitin_ilya.hibernate.UserEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddUserController implements Initializable{
    private static Stage stage;
    private static UserEntity user;

    public TextField loginTEdit;
    public TextField passTEdit;
    public TextField repeatPassTEdit;
    public TextField saltTEdit;
    public ComboBox<CategoryEntity> categoryCBox;
    public Circle repeatPassCircle;
    public Circle loginCircle;


    public AddUserController(String title, UserEntity user) throws IOException {
        AddUserController.user = user;
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("addUser.fxml"));
        stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 400, 200));
        stage.showAndWait();
    }

    public AddUserController(String s) throws IOException {
        this("Добавить пользователя", null);
    }

    public AddUserController() {
    }

    public AddUserController(UserEntity user) throws IOException {
        this("Изменение пользователя " + user.getLogin(), user);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        categoryCBox.setItems(Main.getInstance().getCategoryData());
        categoryCBox.getSelectionModel().select(Main.getInstance().getCategoryData().get(0));
        if (user != null) initEditing();
        loginTEdit.textProperty().addListener((observable, oldValue, newValue) -> {
            UserEntity user = Main.getInstance().getUser(newValue);
            loginCircle.setFill(user == null && newValue.length() != 0 ? Color.GREEN : Color.RED);
        });
        passTEdit.textProperty().addListener((observable, oldValue, newValue) -> {
            repeatPassCircle.setFill(passTEdit.getText().equals(repeatPassTEdit.getText()) ? Color.GREEN : Color.RED);
        });
        repeatPassTEdit.textProperty().addListener((observable, oldValue, newValue) -> {
            repeatPassCircle.setFill(passTEdit.getText().equals(repeatPassTEdit.getText()) ? Color.GREEN : Color.RED);
        });
    }


    private void initEditing() {
        loginTEdit.setText(user.getLogin());
        loginTEdit.setEditable(false);
        loginCircle.setFill(Color.GREEN);
        categoryCBox.getSelectionModel().select(user.getCategory());
        saltTEdit.setText(user.getSalt());
        //passTEdit.setText(user.getPass());
        //repeatPassTEdit.setText(user.getPass());
    }

    public void generateSalt(ActionEvent actionEvent) {
        saltTEdit.setText(SaltGenerator.generate());
    }

    public void save(ActionEvent actionEvent) {
        if (loginCircle.getFill().equals(Color.RED) ||
                repeatPassCircle.getFill().equals(Color.RED)) {
            DialogHelper.getInstance().openErrorDialog("Проверьте введенные данные", "Красный цвет у логина означает, что он уже занят.\nКрасный цвет у пароля - пароли не совпадают или пароль не введен.");
            return;
        }
        String login = loginTEdit.getText();
        String pass = passTEdit.getText();
        CategoryEntity category = categoryCBox.getSelectionModel().getSelectedItem();
        String salt = saltTEdit.getText();

        if (user == null) {
            Main.getInstance().addUser(login, pass, category, salt);
            DialogHelper.getInstance().openInformationDialog("Пользователь был создан");
        } else {
            /*if (user.getPass().equals(pass))
                Main.getInstance().editUser(user, category, salt);
            else*/
                Main.getInstance().editUser(user, pass, category, salt);
            DialogHelper.getInstance().openInformationDialog("Пользователь был изменен");
        }

        cancel(actionEvent);
    }

    public void cancel(ActionEvent actionEvent) {
        stage.hide();
    }
}

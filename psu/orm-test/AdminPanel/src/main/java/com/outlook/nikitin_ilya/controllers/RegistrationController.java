package com.outlook.nikitin_ilya.controllers;

import com.outlook.nikitin_ilya.cryptography.HashText;
import com.outlook.nikitin_ilya.cryptography.SaltGenerator;
import com.outlook.nikitin_ilya.hibernate.CategoryEntity;
import com.outlook.nikitin_ilya.hibernate.Main;
import com.outlook.nikitin_ilya.hibernate.UserEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.outlook.nikitin_ilya.hibernate.CategoryEntity.CategoryType;

/**
 * Created by Ilya on 06.03.2016.
 */
public class RegistrationController implements Initializable {
    private static Stage stage;
    private static Main db;

    public TextField loginTEdit;
    public PasswordField passTEdit;
    public PasswordField repeatPassTEdit;
    public Circle repeatPassCircle;
    public Circle loginCircle;

    public RegistrationController() {
    }

    public RegistrationController(Stage parentStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/registration.form.fxml"));
        stage = new Stage();
        stage.setTitle("Регистрация");
        stage.setScene(new Scene(root, 400, 200));
        stage.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        db = Main.INSTANCE;

        loginTEdit.textProperty().addListener((observable, oldValue, newValue) -> {
            checkLogin(newValue);
        });
        passTEdit.textProperty().addListener((observable, oldValue, newValue) -> {
            checkPass(passTEdit.getText(), repeatPassTEdit.getText());
        });
        repeatPassTEdit.textProperty().addListener((observable, oldValue, newValue) -> {
            checkPass(passTEdit.getText(), repeatPassTEdit.getText());
        });
    }

    private void checkLogin(String login) {
        UserEntity user = db.getUser(login);
        loginCircle.setFill(user == null && login.length() != 0 ? Color.GREEN : Color.RED);
    }

    private void checkPass(String pass, String repeatPass) {
        repeatPassCircle.setFill(pass.equals(repeatPass) ? Color.GREEN : Color.RED);
    }

    public void save(ActionEvent actionEvent) {
        if (loginCircle.getFill().equals(Color.RED) || repeatPassCircle.getFill().equals(Color.RED)) {
            new Alert(Alert.AlertType.ERROR, "Проверьте введенные данные", ButtonType.OK).showAndWait();
            return;
        }

        String login = loginTEdit.getText();
        String pass = passTEdit.getText();
        CategoryEntity category = null;
        for (CategoryEntity categoryEntity : db.getCategoryData()) {
            if (categoryEntity.categoryType().equals(CategoryType.STAFF)) {
                category = categoryEntity;
            }
        }
        String salt = SaltGenerator.generate();

        db.addUser(login, HashText.getHash(pass, salt), category, salt);
        new Alert(Alert.AlertType.INFORMATION, "Пользователь создан", ButtonType.OK).showAndWait();
        stage.hide();
        /*

        if (user == null) {
            Main.getInstance().addUser(login, pass, category, salt);
            DialogHelper.getInstance().openInformationDialog("Пользователь был создан");
        } else {
            //if (user.getPass().equals(pass))
               // Main.getInstance().editUser(user, category, salt);
            //else
            Main.getInstance().editUser(user, pass, category, salt);
            DialogHelper.getInstance().openInformationDialog("Пользователь был изменен");
        }

        cancel(actionEvent);
        */

    }

    public void cancel(ActionEvent actionEvent) {
        stage.hide();
    }
}

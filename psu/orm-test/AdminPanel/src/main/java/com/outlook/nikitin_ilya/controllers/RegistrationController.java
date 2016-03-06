package com.outlook.nikitin_ilya.controllers;

import com.outlook.nikitin_ilya.hibernate.CategoryEntity;
import com.outlook.nikitin_ilya.hibernate.Main;
import com.outlook.nikitin_ilya.hibernate.UserEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Ilya on 06.03.2016.
 */
public class RegistrationController implements Initializable {
    private static Stage stage;
    private static Main db;

    public TextField loginTEdit;
    public ComboBox<CategoryEntity> categoryCBox;
    public PasswordField passTEdit;
    public PasswordField repeatPassTEdit;
    public Circle repeatPassCircle;
    public Circle loginCircle;

    public RegistrationController() {
    }

    public RegistrationController(Stage parentStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/registration.fxml"));
        stage = new Stage();
        stage.setTitle("Регистрация");
        stage.setScene(new Scene(root, 400, 200));
        stage.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        db = Main.INSTANCE;
        categoryCBox.setItems(db.getCategoryData());
    }

    public void save(ActionEvent actionEvent) {
    }

    public void cancel(ActionEvent actionEvent) {
    }

}

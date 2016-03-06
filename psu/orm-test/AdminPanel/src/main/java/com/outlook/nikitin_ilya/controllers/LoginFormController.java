package com.outlook.nikitin_ilya.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Ilya on 06.03.2016.
 */
public class LoginFormController implements Initializable {
    private static Stage stage;

    public ComboBox loginCBox;
    public PasswordField passTextField;

    public LoginFormController() {
    }

    public LoginFormController(Stage parentStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/login.form.fxml"));
        stage = new Stage();
        stage.setTitle("Вход");
        stage.setScene(new Scene(root, 400, 200));
        stage.show();
    }

    public void initialize(URL location, ResourceBundle resources) {

    }

    public void registration(ActionEvent actionEvent) {
    }

    public void enter(ActionEvent actionEvent) {
    }

    public void exit(ActionEvent actionEvent) {
    }
}

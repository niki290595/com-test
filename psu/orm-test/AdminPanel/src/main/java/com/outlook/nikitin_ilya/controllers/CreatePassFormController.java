package com.outlook.nikitin_ilya.controllers;

import com.outlook.nikitin_ilya.hibernate.Main;
import com.outlook.nikitin_ilya.hibernate.UserEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Ilya on 07.03.2016.
 */
public class CreatePassFormController implements Initializable {
    private static Stage stage;
    private static Main db;
    private static UserEntity user;
    public static String newPass;

    static {
        db = Main.INSTANCE;
    }


    public PasswordField passTEdit;
    public PasswordField repeatPassTEdit;
    public Circle repeatPassCircle;

    public CreatePassFormController() {
    }

    public CreatePassFormController(Stage parentStage, UserEntity user) throws IOException {
        CreatePassFormController.user = user;
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/create-pass.form.fxml"));
        stage = new Stage();
        stage.setTitle("Новый пароль");
        stage.setScene(new Scene(root, 400, 200));
        stage.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        passTEdit.textProperty().addListener((observable, oldValue, newValue) -> {
            checkPass(passTEdit.getText(), repeatPassTEdit.getText());
        });
        repeatPassTEdit.textProperty().addListener((observable, oldValue, newValue) -> {
            checkPass(passTEdit.getText(), repeatPassTEdit.getText());
        });
    }

    private void checkPass(String pass, String repeatPass) {
        repeatPassCircle.setFill(pass.equals(repeatPass) && pass.length() != 0 ? Color.GREEN : Color.RED);
    }

    public void save(ActionEvent actionEvent) {
        if (repeatPassCircle.getFill().equals(Color.RED)) {
            new Alert(Alert.AlertType.ERROR, "Пароли не совпадают", ButtonType.OK).showAndWait();
            return;
        }

        newPass = passTEdit.getText();
        db.editUser(user, newPass);
        new Alert(Alert.AlertType.INFORMATION, "Пароль установлен", ButtonType.OK);
        stage.hide();
    }

    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }


}

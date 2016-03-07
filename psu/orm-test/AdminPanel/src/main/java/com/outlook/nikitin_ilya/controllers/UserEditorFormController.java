package com.outlook.nikitin_ilya.controllers;

import com.outlook.nikitin_ilya.hibernate.CategoryEntity;
import com.outlook.nikitin_ilya.hibernate.Main;
import com.outlook.nikitin_ilya.hibernate.UserEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Ilya on 07.03.2016.
 */
public class UserEditorFormController implements Initializable {
    private static Stage stage;
    private static UserEntity user;
    private static Main db;

    static {
        db = Main.INSTANCE;
    }

    public TextField loginTEdit;
    public Circle loginCircle;
    public ComboBox<CategoryEntity> categoryCBox;

    public UserEditorFormController() {
    }

    private UserEditorFormController(Stage parentStage, String title, UserEntity user) throws IOException {
        UserEditorFormController.user = user;
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/user-editor.form.fxml"));
        stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 400, 200));
        stage.showAndWait();
    }

    public UserEditorFormController(Stage parentStage) throws IOException {
        this(parentStage, "Новый пользователь", null);
    }

    public UserEditorFormController(Stage parentStage, UserEntity user) throws IOException {
        this(parentStage, "Редактирование", user);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginTEdit.textProperty().addListener((observable, oldValue, newValue) -> {
            checkLogin(newValue);
        });
        categoryCBox.setItems(db.getCategoryData());

        if (user == null) {
            return;
        }

        loginTEdit.setText(user.getLogin());
        categoryCBox.getSelectionModel().select(user.getCategory());
    }

    private void checkLogin(String login) {
        UserEntity user = db.getUser(login);
        loginCircle.setFill((user == null && login.length() != 0) ||
                (user != null && user.equals(UserEditorFormController.user)) ? Color.GREEN : Color.RED);
    }

    public void save(ActionEvent actionEvent) {
        if (loginCircle.getFill().equals(Color.RED)) {
            new Alert(Alert.AlertType.ERROR, "Проверьте введенные данные", ButtonType.OK).showAndWait();
            return;
        }

        new Alert(Alert.AlertType.INFORMATION, user == null ? addUser() : editUser(), ButtonType.OK).showAndWait();
        stage.hide();
    }

    private String addUser() {
        db.addUser(loginTEdit.getText(), categoryCBox.getSelectionModel().getSelectedItem());
        return "Пользователь добавлен";
    }

    private String editUser() {
        db.editUser(user, loginTEdit.getText(), categoryCBox.getSelectionModel().getSelectedItem());
        return "Пользователь изменен";
    }


    public void cancel(ActionEvent actionEvent) {
        stage.hide();
    }
}

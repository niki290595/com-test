package com.outlook.nikitin_ilya.controllers;

import com.outlook.nikitin_ilya.hibernate.CategoryEntity;
import com.outlook.nikitin_ilya.hibernate.Main;
import com.outlook.nikitin_ilya.hibernate.UserEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Ilya on 06.03.2016.
 */
public class LoginFormController implements Initializable {
    private static Stage stage;
    private static Main db;

    public ComboBox<UserEntity> loginCBox;
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
        db = Main.INSTANCE;
        loginCBox.setItems(db.getUserData());
    }

    public void registration(ActionEvent actionEvent) {
    }

    public void enter(ActionEvent actionEvent) throws IOException {
        tryAuthorization(loginCBox.getSelectionModel().getSelectedItem(), passTextField.getText());
    }

    private void tryAuthorization(UserEntity user, String pass) throws IOException {
        if (user == null) {
            new Alert(Alert.AlertType.ERROR, "Пользователь не выбран",ButtonType.OK).showAndWait();
            //DialogHelper.getInstance().openErrorDialog("Пользователь не выбран", "Выберите ");
        } else if (user.getPass().equals(HashText.sha256(pass, user.getSalt()))) {//(user.getPass().equals(pass)) {
            DialogHelper.getInstance().openInformationDialog(
                    "Добро пожаловать " + user.getLogin(),
                    "Вы вошли как " + user.getCategory());

            CategoryEntity.Description description = CategoryEntity.Description.getRule(user.getCategory().getDescription());
            switch (description) {
                case ADMIN:
                    new AdminController(user.getLogin());
                    break;
                case OWNER:
                case STAFF:
                    DialogHelper.getInstance().openInformationDialog("Работа программы завершена");
                    System.exit(0);
            }
        } else {
            DialogHelper.getInstance().openErrorDialog("Авторизация не пройдена", "Пароль введен неверно");
        }
    }

    public void exit(ActionEvent actionEvent) {
    }
}

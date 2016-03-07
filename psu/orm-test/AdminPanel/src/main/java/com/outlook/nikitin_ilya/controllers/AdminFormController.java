package com.outlook.nikitin_ilya.controllers;

import com.outlook.nikitin_ilya.hibernate.Main;
import com.outlook.nikitin_ilya.hibernate.UserEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

/**
 * Created by Ilya on 07.03.2016.
 */
public class AdminFormController implements Initializable {
    private static Stage stage;
    private static Main db;

    static {
        db = Main.INSTANCE;
    }

    public TableView<UserEntity> usersTable;
    public TableColumn<UserEntity, String> loginColumn;
    public TableColumn<UserEntity, String> categoryColumn;
    public TableColumn<UserEntity, Date> dateCreationColumn;
    public TableColumn<UserEntity, Date> dateModificationColumn;

    public AdminFormController() {}

    public AdminFormController(Stage parentStage, String login) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/admin.form.fxml"));
        stage = new Stage();
        stage.setTitle("Добро пожаловать " + login);
        stage.setScene(new Scene(root, 400, 200));
        stage.show();
        //AdminController.login = login;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        dateCreationColumn.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));
        dateModificationColumn.setCellValueFactory(new PropertyValueFactory<>("dateModification"));
        usersTable.setItems(db.getUserData());
    }

    public void addUser(ActionEvent actionEvent) throws IOException {
        new UserEditorFormController(stage);
    }

    public void editUser(ActionEvent actionEvent) throws IOException {
        UserEntity user = usersTable.getSelectionModel().getSelectedItem();
        if (user != null) {
            new UserEditorFormController(stage, user);
        }
    }

    public void removeUser(ActionEvent actionEvent) {
        UserEntity user = usersTable.getSelectionModel().getSelectedItem();
        if (user != null) {
            db.removeUser(user);
        }
    }

    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }


}

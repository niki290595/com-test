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


public class AdminController implements Initializable {
    private static String login;
    /*
                        <TableColumn fx:id="passColumn" editable="false" prefWidth="109.0" text="Пароль" />
                        <TableColumn fx:id="saltColumn" editable="false" prefWidth="109.0" text="Соль" />
    * */

    public TableView<UserEntity> usersTable;
    public TableColumn<UserEntity, String> loginColumn;
    public TableColumn<UserEntity, String> categoryColumn;
    //public TableColumn<UserEntity, String> passColumn;
    //public TableColumn<UserEntity, String> saltColumn;
    public TableColumn<UserEntity, Date> dateCreationColumn;
    public TableColumn<UserEntity, Date> dateModificationColumn;


    public AdminController() {
    }

    public AdminController(String login) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("admin.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Добро пожаловать " + login);
        stage.setScene(new Scene(root, 400, 200));
        stage.show();
        AdminController.login = login;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        //passColumn.setCellValueFactory(new PropertyValueFactory<>("pass"));
        //saltColumn.setCellValueFactory(new PropertyValueFactory<>("salt"));
        dateCreationColumn.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));
        dateModificationColumn.setCellValueFactory(new PropertyValueFactory<>("dateModification"));
        usersTable.setItems(Main.getInstance().getUserData());
    }

    public void addUser(ActionEvent actionEvent) throws IOException {
        new AddUserController("");
    }

    public void editUser(ActionEvent actionEvent) throws IOException {
        UserEntity user = usersTable.getSelectionModel().getSelectedItem();
        if (user == null) return;
        new AddUserController(user);
        usersTable.refresh();
    }

    public void removeUser(ActionEvent actionEvent) {
        UserEntity user = usersTable.getSelectionModel().getSelectedItem();
        Main.getInstance().removeUser(user);
    }

    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }
}

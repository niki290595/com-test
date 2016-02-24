package com.outlook.nikitin_ilya;

import com.outlook.nikitin_ilya.controllers.AdminController;
import com.outlook.nikitin_ilya.cryptography.HashText;
import com.outlook.nikitin_ilya.hibernate.CategoryEntity;
import com.outlook.nikitin_ilya.hibernate.HibernateUtil;
import com.outlook.nikitin_ilya.hibernate.Main;
import com.outlook.nikitin_ilya.hibernate.UserEntity;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;

public class Start extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        //new AdminController("login");
        authorization();
    }

    private void authorization() {
        Pair <String, String> result = DialogHelper.getInstance().openLoginDialog();

        handleResult(result);
    }

    private void handleResult(Pair<String, String> result) {
        if (result == null) {
            DialogHelper.getInstance().openInformationDialog("Работа программы завершена");
            return;
        }
        String login = result.getKey();
        String pass = result.getValue();
        UserEntity user = Main.getInstance().getUser(login);
        tryAuthorization(user, pass);
    }

    private void tryAuthorization(UserEntity user, String pass) {
        if (user == null) {
            DialogHelper.getInstance().openErrorDialog("Пользователь не найден", "Логин введен неверно");
            authorization();
        } else if (user.getPass().equals(HashText.sha256(pass, user.getSalt()))) {//(user.getPass().equals(pass)) {
            DialogHelper.getInstance().openInformationDialog(
                    "Добро пожаловать " + user.getLogin(),
                    "Вы вошли как " + user.getCategory());

            CategoryEntity.Description description = CategoryEntity.Description.getRule(user.getCategory().getDescription());
            switch (description) {
                case ADMIN:
                    openAdminOffice(user.getLogin());
                    break;
                case OWNER:
                case STAFF:
                    DialogHelper.getInstance().openInformationDialog("Работа программы завершена");
            }
        } else {
            DialogHelper.getInstance().openErrorDialog("Авторизация не пройдена", "Пароль введен неверно");
            authorization();
        }
    }

    private void openAdminOffice(String login) {
        try {
            new AdminController(login);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() throws Exception {
        HibernateUtil.getSessionFactory().close();
    }
}

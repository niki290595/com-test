package com.outlook.nikitin_ilya;

import com.outlook.nikitin_ilya.hibernate.CategoryEntity;
import com.outlook.nikitin_ilya.hibernate.Main;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.util.Pair;
import org.controlsfx.dialog.LoginDialog;

import java.util.List;

public class DialogHelper {
    private static DialogHelper instance;

    private DialogHelper() {
    }

    public static DialogHelper getInstance() {
        if (instance == null) {
            instance = new DialogHelper();
        }
        return instance;
    }

    public Pair<String, String> openLoginDialog() {
        LoginDialog dlg = new LoginDialog(null, null);
        dlg.getDialogPane().getButtonTypes().remove(0);
        dlg.getDialogPane().getButtonTypes().add(new ButtonType("Выход", ButtonBar.ButtonData.CANCEL_CLOSE));
        dlg.showAndWait();
        return dlg.getResult();
    }

    private void openDialog(Alert.AlertType type, String title, String mainMsg, String secMsg) {
        Alert dlg = new Alert(type, "");
        dlg.initModality(Modality.APPLICATION_MODAL);
        dlg.initOwner(null);
        dlg.setTitle(title);
        dlg.getDialogPane().setHeaderText(mainMsg);
        dlg.getDialogPane().setContentText(secMsg);
        dlg.showAndWait();
    }

    public void openErrorDialog(String title, String mainMsg, String secMsg) {
        openDialog(Alert.AlertType.ERROR, title, mainMsg, secMsg);
    }

    public void openErrorDialog(String mainMsg, String secMsg) {
        openDialog(Alert.AlertType.ERROR, "Ошибка", mainMsg, secMsg);
    }

    public void openInformationDialog(String title, String mainMsg, String secMsg) {
        openDialog(Alert.AlertType.INFORMATION, title, mainMsg, secMsg);
    }

    public void openInformationDialog(String mainMsg, String secMsg) {
        openInformationDialog("Уведомление", mainMsg, secMsg);
    }

    public void openInformationDialog(String msg) {
        openInformationDialog("Уведомление", msg, "");
    }

    public String openInputDialog(String title, String mainMsg, String secMsg, String text) {
        TextInputDialog dlg = new TextInputDialog(text);
        dlg.setTitle(title);
        dlg.getDialogPane().setContentText(mainMsg);
        dlg.getDialogPane().setHeaderText(secMsg);
        dlg.showAndWait();
        return dlg.getResult();
    }

    public String openInputDialog(String title, String mainMsg, String secMsg) {
        return openInputDialog(title, mainMsg, secMsg, "");
    }

    public CategoryEntity openSelectCategoryDialog() {
        List<CategoryEntity> categoryList = Main.getInstance().getCategoryData();
        return openSelectCategoryDialog(categoryList.get(0));
    }

    public CategoryEntity openSelectCategoryDialog(CategoryEntity category) {
        List<CategoryEntity> categoryList = Main.getInstance().getCategoryData();
        ChoiceDialog<CategoryEntity> dlg = new ChoiceDialog<>(category, categoryList);
        dlg.setTitle("Категория");
        dlg.getDialogPane().setContentText("Категория: ");
        dlg.getDialogPane().setHeaderText("Выберите категорию для пользователя.\nЭтим определяются его права");
        dlg.showAndWait();
        return dlg.getResult();
    }
}

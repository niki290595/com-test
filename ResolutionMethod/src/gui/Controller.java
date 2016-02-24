package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import old.Main;

public class Controller {
    @FXML
    TextField inputTxtField;
    @FXML
    TextArea outputTxtArea;

    static String output;

    public void addNOT(ActionEvent actionEvent) {
        inputTxtField.setText(inputTxtField.getText() + Character.valueOf((char) 172));
    }

    public void addAND(ActionEvent actionEvent) {
        inputTxtField.setText(inputTxtField.getText() + Character.valueOf((char) 708));
    }

    public void addOR(ActionEvent actionEvent) {
        inputTxtField.setText(inputTxtField.getText() + Character.valueOf((char) 709));
    }

    public void addFOLLOW(ActionEvent actionEvent) {
        inputTxtField.setText(inputTxtField.getText() + Character.valueOf((char) 62));
    }

    public void addEQUALENCE(ActionEvent actionEvent) {
        inputTxtField.setText(inputTxtField.getText() + Character.valueOf((char) 126));
    }

    public void getTree(ActionEvent actionEvent) {
        output = "";
        String[] args = {inputTxtField.getText()};
        Main.main(args);
        outputTxtArea.setText(output);
    }

    public static void addToLog(String s) {
        output += '\n' + s;
    }
}

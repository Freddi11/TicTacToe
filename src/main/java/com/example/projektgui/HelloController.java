package com.example.projektgui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.awt.*;

public class HelloController {
    static boolean spielerReihe = true;
    //True für X und False für 0


    @FXML
    private Label welcomeText;
    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private Button button4;
    @FXML
    private Button button5;
    @FXML
    private Button button6;
    @FXML
    private Button button7;
    @FXML
    private Button button8;
    @FXML
    private Button button9;

    @FXML
    protected void onHelloButtonClick(javafx.event.ActionEvent event) {
        //Prüft welcher Button geklickt wurde
        javafx.scene.control.Button geklickterButton = (javafx.scene.control.Button) event.getSource();

        // 2. Prüfen, wer an der Reihe ist und Text setzen
        if (spielerReihe == true) {
            geklickterButton.setText("X");
            spielerReihe = false;
        } else {
            geklickterButton.setText("O");
            spielerReihe = true;
        }

    }



}





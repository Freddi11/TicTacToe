package com.example.projektgui;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

// WICHTIG: Die ungenutzten AWT-Imports entfernen (beißt sich manchmal mit JavaFX)
import java.io.IOException;

import static com.example.projektgui.HelloController.spielerReihe;

public class HelloApplication extends Application {

    //variabeln initialisieren
    private Button[][] btns = new Button[3][3];
    Label whoWins;
    Label einfuehrung;
    Button b2;
    Button b1;
    int zuege = 0;
    boolean win = false;

    @Override
    public void start(Stage stage) {
        initBtnsArray();
        Group root = new Group();

        root.getChildren().add(getGrid());
        Scene scene = new Scene(root, 500, 500);

        stage.setTitle("TicTacToe");
        stage.setScene(scene);
        stage.show();

        einfuehrung = new Label("spiele TicTacToe...!!");
        einfuehrung.setPrefSize(220, 100);
        einfuehrung.setLayoutX(70);
        einfuehrung.setLayoutY(1);
        root.getChildren().add(einfuehrung);

        whoWins = new Label("Es gibt noch keinen Gewinner :)");
        whoWins.setPrefSize(220, 100);
        whoWins.setLayoutX(290);
        whoWins.setLayoutY(1);
        root.getChildren().add(whoWins);


        //Button b1 erstellt mit lage im Frame
        // Button 1 für spiel beenden
        b1 = new Button("Beenden");
        b1.setPrefSize(70, 50);
        b1.setLayoutX(100);
        b1.setLayoutY(420);
        root.getChildren().add(b1);
        b1.setOnAction(event -> {
            stage.close();
        });


        // Button 2 für zurücksetzen
        b2 = new Button("Felder zurücksetzen");
        b2.setPrefSize(130, 50);
        b2.setLayoutX(200);
        b2.setLayoutY(420);
        root.getChildren().add(b2);
        b2.setOnAction(event -> {
            buttonsleeren();
        });

    }


    private Pane getGrid() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10); // Abstand zwischen den Spalten
        gridPane.setVgap(10); // Abstand zwischen den Zeilen

        // Wir nutzen eine normale for-Schleife mit 'i', damit wir den Index direkt haben
        for (int i = 0; i < btns.length; i++) {
            for (int e = 0; e < btns.length; e++) {
                Button b = btns[i][e];


                b.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        Button geklickterButton = (Button) actionEvent.getSource();

                        // Prüfen, ob das Feld bereits besetzt ist
                        if (!geklickterButton.getText().equals("")) {
                            return; // Feld ist besetzt -> Tu nichts und brich ab
                        }

                        // Zeichen setzen je nachdem wer dran ist
                        // SpielerReihe = true == X
                        //SpielerReihe = false == O

                        if (spielerReihe == true) {
                            geklickterButton.setText("X");
                            spielerReihe = false;
                            zuege++;
                        } else {
                            geklickterButton.setText("O");
                            spielerReihe = true;
                            zuege++;
                        }
                        win();

                        if (zuege == 9 && win == false)
                        {
                            whoWins.setText("Es gibt keinen Gewinner :(");
                            zuege = 0;

                        }
                    }

                });

                gridPane.add(b, e, i);
                gridPane.setTranslateX(70);
                gridPane.setTranslateY(70);
            }
        }
        return gridPane;
    }

    //Buttons erstellen
    private void initBtnsArray() {
        for (int i = 0; i < btns.length; i++) { // Läuft von 0 bis 2 (3 Zeilen)
            for (int e = 0; e < btns[i].length; e++) { // Läuft von 0 bis 2 (3 Spalten pro Zeile)
                btns[i][e] = new Button("");
                btns[i][e].setPrefSize(100, 100);
            }
        }
    }


    //alle Buttons wieder leeren
    public void buttonsleeren() {
        for (int i = 0; i < btns.length; i++) {
            for (int e = 0; e < btns.length; e++) {
                btns[i][e].setText("");
            }
        }
        zuege = 0;
        win = false;
    }

    /* 1 2 3
       4 5 6
       7 8 9 */
    public void win() {
        int[] vektor1 = new int[2];
        int[] vektor2 = new int[2];
        int[] vektor3 = new int[2];
        for (int i = 0; i < btns.length; i++) {



            if (!btns[i][0].getText().isEmpty() &&
                    btns[i][0].getText().equals(btns[i][1].getText()) &&
                    btns[i][0].getText().equals(btns[i][2].getText())) {

                whoWins.setText(btns[i][0].getText() + " gewinnt");
                win = true;
                buttonsleeren();


            }
            if (!btns[0][i].getText().isEmpty() &&
                    btns[0][i].getText().equals(btns[1][i].getText()) &&
                    btns[0][i].getText().equals(btns[2][i].getText())) {

                whoWins.setText(btns[0][i].getText() + " gewinnt");
                win = true;

                buttonsleeren();


            }
            if (!btns[0][0].getText().isEmpty() &&
                    btns[0][0].getText().equals(btns[1][1].getText()) &&
                    btns[0][0].getText().equals(btns[2][2].getText())) {

                whoWins.setText(btns[0][0].getText() + " gewinnt");
                win = true;

                buttonsleeren();


            }
            if (!btns[0][2].getText().isEmpty() &&
                    btns[0][2].getText().equals(btns[1][1].getText()) &&
                    btns[0][2].getText().equals(btns[2][0].getText())) {

                whoWins.setText(btns[0][2].getText() + " gewinnt");
                win = true;

                buttonsleeren();


            }

            if (zuege == 9 && win == false) {
                whoWins.setText("Es gibt keinen Gewinner");
                buttonsleeren();
            }

        }


    }
}









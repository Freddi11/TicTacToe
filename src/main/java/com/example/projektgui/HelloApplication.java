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
    private  Button[][] btns = new Button[3][3];

    @Override
    public void start(Stage stage) {
        initBtnsArray();
        Group root = new Group();

        root.getChildren().add(getGrid());
        Scene scene = new Scene(root, 500, 500);

        stage.setTitle("TicTacToe");
        stage.setScene(scene);
        stage.show();

        Label einfuehrung = new Label("spiele TicTacToe gegen einen anderen!!...");
        einfuehrung.setPrefSize(220, 100);
        einfuehrung.setLayoutX(1);
        einfuehrung.setLayoutY(1);
        root.getChildren().add(einfuehrung);

        Label whoWins = new Label("");
        whoWins.setPrefSize(220, 100);
        whoWins.setLayoutX(150);
        whoWins.setLayoutY(1);
        root.getChildren().add(whoWins);


        //Button b1 erstellt mit lage im Frame
        // Button 1 für spiel beenden
        Button b1 = new Button("Beenden");
        b1.setPrefSize(70, 50);
        b1.setLayoutX(100);
        b1.setLayoutY(420);
        root.getChildren().add(b1);
        b1.setOnAction(event -> {
            stage.close();
        });


        // Button 2 für zurücksetzen
        Button b2 = new Button("Felder zurücksetzen");
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
                        if (spielerReihe == true) {
                            geklickterButton.setText("X");
                            spielerReihe = false;
                        } else {
                            geklickterButton.setText("O");
                            spielerReihe = true;
                        }
                        win();


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

    }


    public void win() {
        for (int i = 0; i < btns.length; i++)
        {
            for (int e = 0; e < btns.length; e++)
            {

            }
        }
    }
}






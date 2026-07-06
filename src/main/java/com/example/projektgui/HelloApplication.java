package com.example.projektgui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

// WICHTIG: Die ungenutzten AWT-Imports entfernen (beißt sich manchmal mit JavaFX)
import java.io.IOException;

import static com.example.projektgui.HelloController.spielerReihe;

public class HelloApplication extends Application {
    private final Button[] btns = new Button[9];

    @Override
    public void start(Stage stage) {
        initBtnsArray();
        Group root = new Group();

        root.getChildren().add(getGrid());
        Scene scene = new Scene(root, 400, 400);

        stage.setTitle("TicTacToe");
        stage.setScene(scene);
        stage.show();
    }

    private Pane getGrid() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10); // Abstand zwischen den Spalten
        gridPane.setVgap(10); // Abstand zwischen den Zeilen

        // Wir nutzen eine normale for-Schleife mit 'i', damit wir den Index direkt haben
        for (int i = 0; i < btns.length; i++) {
            Button b = btns[i];

            b.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    Button geklickterButton = (Button) actionEvent.getSource();

                    // Prüfen, ob das Feld bereits besetzt ist (Text ist NICHT leer)
                    if (!geklickterButton.getText().equals("")) {
                        return; // Feld ist besetzt -> Tu nichts und brich ab
                    }

                    // Zeichen setzen je nachdem wer dran ist
                    if (spielerReihe) {
                        geklickterButton.setText("X");
                    } else {
                        geklickterButton.setText("O");
                    }

                    // Spieler wechseln (aus true wird false, aus false wird true)
                    spielerReihe = !spielerReihe;
                }
            });

            // MATHEMATISCHER TRICK FÜR DAS 3x3 GITTER:
            // Spalte ist der Rest der Division durch 3 (0, 1, 2, 0, 1, 2...)
            int spalte = i % 3;
            // Zeile ist das Ergebnis der Division ohne Rest (0, 0, 0, 1, 1, 1, 2, 2, 2)
            int zeile = i / 3;

            gridPane.add(b, spalte, zeile);
        }
        return gridPane;
    }

    private void initBtnsArray() {
        for(int i = 0; i < btns.length; i++) {
            // Für TicTacToe starten wir am besten mit leeren Buttons "" statt "Button-i"
            btns[i] = new Button("");
            btns[i].setPrefSize(100, 100); // Gibt den Buttons eine schöne quadratische Größe
        }
    }
    
    //btns[x] 
    
    //alle Buttons wieder leeren
    public void buttonsleeren()
    {
        Button b1 = new Button("");
        b1.setPrefSize(100, 100);
        for(int i = 0; i < btns.length; i++) {
            btns[i].setText("");

    }
    }
    public void win()
    {
        //Array 0 bis 8;
        //schauen wann 3 Miteindaner verbunden sind
        for(int i = 0; i < btns.length; i++) {
            if (btns[0].getText().equals("X")&& btns[1].getText().equals("X")&& btns[2].getText().equals("X"))
            {
                buttonsleeren();
            }
        }

    }
}



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

import java.io.IOException;
import java.util.Objects;

import static com.example.projektgui.HelloController.spielerReihe;

public class HelloApplication extends Application {

    private Button[][] btns = new Button[3][3];
    Label whoWins;
    Label einfuehrung;
    Button b2;
    Button b1;
    Button einSpieler;
    Button zweiSpieler;
    int zuege = 0;
    boolean win = false;
    String spielModi;

    @Override
    public void start(Stage stage) {
        initBtnsArray();
        Group root = new Group();

        root.getChildren().add(getGrid());
        Scene scene = new Scene(root, 420, 480);
        root.setStyle("-fx-background-color: #3498db;");

        stage.setTitle("TicTacToe");
        {

            javafx.scene.image.Image icon = new javafx.scene.image.Image(Objects.requireNonNull(getClass().getResourceAsStream("/icon1.png")));
            stage.getIcons().add(icon);
        }

        stage.setScene(scene);
        stage.show();

        einfuehrung = new Label("");
        einfuehrung.setPrefSize(220, 100);
        einfuehrung.setLayoutX(50);
        einfuehrung.setLayoutY(1);
        if(spielModi == "1Spieler" ||spielModi == "2Spieler"){
            root.getChildren().add(einfuehrung);
        }


        whoWins = new Label("");
        whoWins.setPrefSize(180, 100);
        whoWins.setLayoutX(200);
        whoWins.setLayoutY(1);
        if(spielModi == "1Spieler" ||spielModi == "2Spieler") {
            root.getChildren().add(whoWins);
        }
        b1 = new Button("");
        b1.setPrefSize(70, 50);
        b1.setLayoutX(100);
        b1.setLayoutY(420);
        if(spielModi == "1Spieler" ||spielModi == "2Spieler") {
            root.getChildren().add(b1);
        }
        b1.setOnAction(event -> {
            stage.close();
        });
        b2 = new Button("");
        b2.setPrefSize(130, 50);
        b2.setLayoutX(200);
        b2.setLayoutY(420);
        if(spielModi == "1Spieler" ||spielModi == "2Spieler") {
            root.getChildren().add(b2);
        }
        b2.setOnAction(event -> {
            buttonsleeren();
            whoWins.setText("Es gibt noch keinen Gewinner :)");
        });


        einSpieler = new Button("1Spieler");
        einSpieler.setPrefSize(70, 50);
        einSpieler.setLayoutX(120);
        einSpieler.setLayoutY(240);
        root.getChildren().add(einSpieler);
        einSpieler.setOnAction(event -> {
            System.out.println("Ein Spieler wurde ausgewählt ");
            spielModi = "1Spieler";
        });
        zweiSpieler = new Button("2Spieler");
        zweiSpieler.setPrefSize(70, 50);
        zweiSpieler.setLayoutX(220);
        zweiSpieler.setLayoutY(240);
        root.getChildren().add(zweiSpieler);
        zweiSpieler.setOnAction(event -> {
            System.out.println(" Zwei Spieler wurde ausgewählt");
            spielModi = "2Spieler";

        });


    }

    private Pane getGrid() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        for (int i = 0; i < btns.length; i++) {
            for (int e = 0; e < btns.length; e++) {
                Button b = btns[i][e];

                b.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        Button geklickterButton = (Button) actionEvent.getSource();

                        // Wenn das Spiel schon gewonnen ist oder Feld besetzt, mach nichts
                        if (!geklickterButton.getText().equals("") || win) {
                            return;
                        }

                        if (spielerReihe) {
                            geklickterButton.setText("X");
                            spielerReihe = false;
                        } else {
                            geklickterButton.setText("O");
                            spielerReihe = true;
                        }
                        zuege++;

                        // Gewinner prüfen (3er Kette gesucht)
                        String gewinner = check(btns, 3);
                        if (!gewinner.isEmpty()) {
                            whoWins.setText("Spieler " + gewinner + " gewinnt!");
                            buttonsleeren();
                        } else if (zuege == 9) {
                            whoWins.setText("Es gibt keinen Gewinner :(");
                            buttonsleeren();
                        }
                    }
                });
                if(spielModi == "1Spieler" ||  spielModi == "2Spieler") {
                    gridPane.add(b, e, i);
                    whoWins.setText("Es gibt noch keinen Gewinner :)");
                    b1.setText("Beenden");
                    b2.setText("Felder zurücksetzen");
                }

            }
        }
        // Verschiebung des gesamten GridPanes einmalig setzen (außerhalb der Schleife reicht)
        gridPane.setTranslateX(50);
        gridPane.setTranslateY(70);
        return gridPane;
    }

    private void initBtnsArray() {
        for (int i = 0; i < btns.length; i++) {
            for (int e = 0; e < btns[i].length; e++) {
                btns[i][e] = new Button("");
                btns[i][e].setPrefSize(100, 100);
                // größere Schrift für das X und O
                btns[i][e].setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
            }
        }
    }

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
    public String check(Button[][] btns, int score) {
        //vertical Vektor 0/1
        for (int i = 0; i < btns[0].length; i++) {
            String result = checkLine(btns, i,0 , 0, 1, score);
            if (!result.isEmpty()) {
                return result;
            }
        }
        //horizontal Vektor 1/0
        for (int i = 0; i < btns[0].length; i++) {
            String result = checkLine(btns, 0,i , 1, 0, score);
            if (!result.isEmpty()) {
                return result;
            }
        }
        //diagonal / Vektor -1/1
        for (int i = 0; i < btns.length; i++) {
            //geht horizontal
            String result = checkLine(btns, 0, i, -1, 1, score);
            if (!result.isEmpty()) {
                return result;
            }
            result = checkLine(btns, i, btns[0].length - 1, 1, -1, score);
            if (!result.isEmpty()) {
                return result;
            }

        }

        //diagonal\ Vektor 1/1
        for (int i = 0; i < btns.length; i++) {
            //geht horizontal
            String result = checkLine(btns, 0,i ,1, 1, score);
            if (!result.isEmpty()) {
                return result;
            }
            //geht diagonal von 0/0 bis 0/2
            result = checkLine(btns, 0,i ,1, 1, score);
            if (!result.isEmpty()) {
                return result;
            }
            //geht diagonal von 2/0 bis 2/2

        }
        return "";
    }

    public String checkLine(Button[][] btns, int x, int y, int deltaX, int deltaY, int score) {
        int height = btns.length;
        int width = btns[0].length;
        String currentSymbol = "";
        int symbolCounter = 0;

        while (x >= 0 && x < width && y >= 0 && y < height) {
            String symbol = btns[y][x].getText();

            if (symbol.isEmpty()) {
                currentSymbol = "";
                symbolCounter = 0;
            }
            else if (symbol.equals(currentSymbol)) {
                symbolCounter++;
            }
            else {
                currentSymbol = symbol;
                symbolCounter = 1;
            }
            if (symbolCounter == score && !symbol.isEmpty()) {
                //wer hat gewonnen
                return symbol;
            }
            x = x + deltaX;
            y = y + deltaY;
        }

        // wenn keine reihe gefunden wurde
        return "";
    }
}

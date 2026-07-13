
package com.example.projektgui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Objects;

import static com.example.projektgui.HelloController.spielerReihe;

public class HelloApplication extends Application {

    private Button[][] btns = new Button[3][3];
    private int[][] feld =  new int[3][3];//1 = X und 2 = Kreis für 2d 3mal3 Array
    Label whoWins;
    Label einfuehrung;
    Button b2;
    Button b1;
    Button b3;
    Button einSpieler;
    Button zweiSpieler;
    int zuege = 0;
    boolean win = false;
    int spielModi;
    GridPane gridPane = new GridPane();
    Group root = new Group();
    boolean mehrspieler = false; //1 = X und 2 = Kreis für 2d 3mal3 Array
    String inhalt;
    int row = 0; //Spalte
    int col = 0;//Zeile

    @Override
    public void start(Stage stage) {
        stage.setTitle("TicTacToe");
        {
            javafx.scene.image.Image icon = new javafx.scene.image.Image(Objects.requireNonNull(getClass().getResourceAsStream("/icon1.png")));
            stage.getIcons().add(icon);
        }

        einfuehrung = new Label("spiele TicTacToe...!");
        einfuehrung.setPrefSize(220, 100);
        einfuehrung.setLayoutX(50);
        einfuehrung.setLayoutY(1);
        root.getChildren().add(einfuehrung);


        whoWins = new Label("Es gibt noch keinen Gewinner:)");
        whoWins.setPrefSize(180, 100);
        whoWins.setLayoutX(200);
        whoWins.setLayoutY(1);
        root.getChildren().add(whoWins);
        whoWins.setVisible(false);

        //Button b1 zum Beenden ist zuerst unsichtbar
        b1 = new Button("Beenden");
        b1.setPrefSize(75, 50);
        b1.setLayoutX(150);
        b1.setLayoutY(420);
        root.getChildren().add(b1);
        b1.setOnAction(event -> {
            stage.close();
        });

        b3 = new Button("zurück");
        b3.setPrefSize(75, 50);
        b3.setLayoutX(50);
        b3.setLayoutY(420);
        root.getChildren().add(b3);
        b3.setOnAction(event -> {
            feldLeeren();
            gridPane.setVisible(false);
            b2.setVisible(false);
            whoWins.setVisible(false);
            zweiSpieler.setVisible(true);
            einSpieler.setVisible(true);
        });

        //Button b2 zum Felder löschen
        b2 = new Button("Felder zurücksetzen");
        b2.setPrefSize(130, 50);
        b2.setLayoutX(240);
        b2.setLayoutY(420);
        root.getChildren().add(b2);
        b2.setOnAction(event -> {
            feldLeeren();
            whoWins.setText("Es gibt noch keinen Gewinner :)");
        });
        b2.setVisible(false);


        einSpieler = new Button("1Spieler");
        einSpieler.setPrefSize(70, 50);
        einSpieler.setLayoutX(120);
        einSpieler.setLayoutY(240);
        root.getChildren().add(einSpieler);
        einSpieler.setOnAction(event -> {
            spielModi = 1 ;
            gridPane.setVisible(true);
            einSpieler.setVisible(false);
            zweiSpieler.setVisible(false);
            b2.setVisible(true);
            whoWins.setVisible(true);
        });


        zweiSpieler = new Button("2Spieler");
        zweiSpieler.setPrefSize(70, 50);
        zweiSpieler.setLayoutX(220);
        zweiSpieler.setLayoutY(240);
        root.getChildren().add(zweiSpieler);
            zweiSpieler.setOnAction(event -> {
                spielModi = 2;
                gridPane.setVisible(true);
                einSpieler.setVisible(false);
                zweiSpieler.setVisible(false);
                b2.setVisible(true);
                whoWins.setVisible(true);
                mehrspieler = true;
            });


        createButtons();
        root.getChildren().add(getGrid());
        gridPane.setVisible(false);
        Scene scene = new Scene(root, 420, 480);
        root.setStyle("-fx-background-color: #3498db;");
        stage.setScene(scene);
        stage.show();
    }

    private Pane getGrid() {

        gridPane.setHgap(10);
        gridPane.setVgap(10);

        for (int i = 0; i < btns.length; i++) {
            for (int e = 0; e < btns.length; e++) {
                Button b = btns[i][e];

                b.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        Button geklickterButton = (Button) actionEvent.getSource();
                        row = GridPane.getRowIndex(geklickterButton) != null ? GridPane.getRowIndex(geklickterButton) : 0;
                        col = GridPane.getColumnIndex(geklickterButton) != null ? GridPane.getColumnIndex(geklickterButton) : 0;

                        // Wenn das Spiel schon gewonnen ist oder Feld besetzt, mach nichts
                        if (!geklickterButton.getText().equals("") || win) {
                            return;
                        }

                        if (spielerReihe) {
                            javafx.scene.image.Image iconX = new javafx.scene.image.Image(Objects.requireNonNull(getClass().getResourceAsStream("/bild1.png")));
                            ImageView ansichtX = new ImageView(iconX);
                            ansichtX.setFitHeight(70);
                            ansichtX.setFitWidth(70);
                            geklickterButton.setGraphic(ansichtX);
                            int x = row;
                            int y = col;
                            feld[x][y] = 1;
                            spielerReihe = false;
                        } else {
                            javafx.scene.image.Image iconO = new javafx.scene.image.Image(Objects.requireNonNull(getClass().getResourceAsStream("/bild2.png")));
                            ImageView ansichtO = new ImageView(iconO);
                            ansichtO.setFitHeight(70);
                            ansichtO.setFitWidth(70);
                            geklickterButton.setGraphic(ansichtO);
                            int x = row;
                            int y = col;
                            feld[x][y] = 2;
                            spielerReihe = true;
                        }
                        zuege++;

                        // Gewinner prüfen (3er Kette gesucht)
                        String gewinner = check(btns, 3);
                        if (!gewinner.isEmpty()) {
                            whoWins.setText("Spieler " + gewinner + " gewinnt!");
                            feldLeeren();
                        } else if (zuege == 9) {
                            whoWins.setText("Es gibt keinen Gewinner :(");
                            feldLeeren();
                        }
                    }
                });

                    gridPane.add(b, e, i);

            }
        }
        // Verschiebung des gesamten GridPanes einmalig setzen (außerhalb der Schleife reicht)
        gridPane.setTranslateX(50);
        gridPane.setTranslateY(70);
        return gridPane;
    }

    private void createButtons() {
        for (int i = 0; i < btns.length; i++) {
            for (int e = 0; e < btns[i].length; e++) {
                btns[i][e] = new Button("");
                btns[i][e].setPrefSize(100, 100);
            }
        }
    }

    public void feldLeeren() {
        for (int i = 0; i < btns.length; i++) {
            for (int e = 0; e < btns.length; e++) {
                btns[i][e].setGraphic(null);
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
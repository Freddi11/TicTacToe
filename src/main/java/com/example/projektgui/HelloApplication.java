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

import java.util.Objects;
import java.util.Random;

import static com.example.projektgui.HelloController.spielerReihe;

public class HelloApplication extends Application {

    public Button[][] btns = new Button[3][3];
    public int[][] feld = new int[3][3];//1 = X und 2 = Kreis für 2d 3mal3 Array
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
        einSpieler = new Button("1Spieler");
        einSpieler.setPrefSize(70, 50);
        einSpieler.setLayoutX(120);
        einSpieler.setLayoutY(240);
        root.getChildren().add(einSpieler);
        einSpieler.setOnAction(event -> {
            spielModi = 1;
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
            spielModi = 0;
        });

        //Button b2 zum Felder löschen
        b2 = new Button("Felder zurücksetzen");
        b2.setPrefSize(130, 50);
        b2.setLayoutX(240);
        b2.setLayoutY(420);
        root.getChildren().add(b2);
        b2.setOnAction(event -> {
            feldLeeren();
        });
        b2.setVisible(false);

        createButtons();
        root.getChildren().add(getGrid());
        gridPane.setVisible(false);
        Scene scene = new Scene(root, 420, 480);
        root.setStyle("-fx-background-color: #3498db;");
        stage.setScene(scene);
        stage.show();
    }

    private Pane getGrid() {
        gridPane.setVisible(true);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        for (int i = 0; i < btns.length; i++) {
            for (int e = 0; e < btns.length; e++) {
                Button b = btns[i][e];

                b.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        Button geklickterButton = (Button) actionEvent.getSource();
                        row = GridPane.getRowIndex(geklickterButton);
                        col = GridPane.getColumnIndex(geklickterButton);

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
                            zuege++;
                        }
                        if (spielerReihe == false) {
                            computerZug();
                        }


                        // Gewinner prüfen
                        String gewinner = "";
                        int i = check(feld, 3);

                        if (i == 1) {
                            gewinner = "X";
                            whoWins.setText("Spieler X hat gewonnen");
                            feldLeeren();
                        }
                        if (i == 2) {
                            gewinner = "O";
                            whoWins.setText("Spieler O hat gewonnen");
                            feldLeeren();
                        } else if (zuege == 9) {
                            whoWins.setText("Es gibt keinen Gewinner :(");
                            feldLeeren();
                            zuege = 0;
                        }
                        //durchsucht das Array dies ist für den Host zum notieren der Züge
                        for (int a = 0; a < feld.length; a++) {
                            for (int c = 0; c < feld.length; c++) {
                                System.out.print(feld[a][c]);
                            }
                            System.out.println("");
                        }
                        System.out.println("");


                    }

                });
                gridPane.add(b, e, i);


            }


            // Verschiebung des gesamten GridPanes einmalig setzen (außerhalb der Schleife reicht)
            gridPane.setTranslateX(50);
            gridPane.setTranslateY(70);

        }
        return gridPane;
    }

    //Code vom Computer----------------------------------
    public void computerZug() {
        if (spielModi == 1) {
            int count = 0;
            for(int i = 0; i < btns.length; i++) {
                for(int e = 0; e < btns.length; e++) {
                    if(feld[i][e]==2)
                    {
                        count++;
                    }

                }

            }



            if(feld[1][0] == 2 && feld[1][2] == 2 )
            {
                if(feld[1][1] == 0)
                {
                    feld[1][1] = 2;
                    javafx.scene.image.Image iconO = new javafx.scene.image.Image(Objects.requireNonNull(getClass().getResourceAsStream("/bild2.png")));
                    ImageView ansichtO = new ImageView(iconO);
                    ansichtO.setFitHeight(70);
                    ansichtO.setFitWidth(70);
                    btns[1][1].setGraphic(ansichtO);
                    zuege++;
                }
            }




            if(feld[feld.length - 1][0] == 2 && feld[0][feld[0].length - 1]==2)
            {
                if(feld[1][1] == 0)
                {
                    feld[1][1] = 2;
                    javafx.scene.image.Image iconO = new javafx.scene.image.Image(Objects.requireNonNull(getClass().getResourceAsStream("/bild2.png")));
                    ImageView ansichtO = new ImageView(iconO);
                    ansichtO.setFitHeight(70);
                    ansichtO.setFitWidth(70);
                    btns[1][1].setGraphic(ansichtO);
                    zuege++;
                }
            }

            if (feld[feld.length - 1][0] == 0) {
                feld[feld.length - 1][0] = 2;
                javafx.scene.image.Image iconO = new javafx.scene.image.Image(Objects.requireNonNull(getClass().getResourceAsStream("/bild2.png")));
                ImageView ansichtO = new ImageView(iconO);
                ansichtO.setFitHeight(70);
                ansichtO.setFitWidth(70);
                btns[btns[0].length - 1][0].setGraphic(ansichtO);
                zuege++;

            } else if (feld[0][feld[0].length - 1] == 0) {
                feld[0][feld[0].length - 1] = 2;
                javafx.scene.image.Image iconO = new javafx.scene.image.Image(Objects.requireNonNull(getClass().getResourceAsStream("/bild2.png")));
                ImageView ansichtO = new ImageView(iconO);
                ansichtO.setFitHeight(70);
                ansichtO.setFitWidth(70);
                btns[0][btns[0].length - 1].setGraphic(ansichtO);
                zuege++;

            } else if (feld[0][0] == 0) {
                feld[0][0] = 2;
                javafx.scene.image.Image iconO = new javafx.scene.image.Image(Objects.requireNonNull(getClass().getResourceAsStream("/bild2.png")));
                ImageView ansichtO = new ImageView(iconO);
                ansichtO.setFitHeight(70);
                ansichtO.setFitWidth(70);
                btns[0][0].setGraphic(ansichtO);
                zuege++;
            } else if (feld[2][2] == 0) {
                feld[2][2] = 2;
                javafx.scene.image.Image iconO = new javafx.scene.image.Image(Objects.requireNonNull(getClass().getResourceAsStream("/bild2.png")));
                ImageView ansichtO = new ImageView(iconO);
                ansichtO.setFitHeight(70);
                ansichtO.setFitWidth(70);
                btns[2][2].setGraphic(ansichtO);
                zuege++;

            }
            else if(feld[1][1] == 0)
            {
                feld[1][1] = 2;
                javafx.scene.image.Image iconO = new javafx.scene.image.Image(Objects.requireNonNull(getClass().getResourceAsStream("/bild2.png")));
                ImageView ansichtO = new ImageView(iconO);
                ansichtO.setFitHeight(70);
                ansichtO.setFitWidth(70);
                btns[1][1].setGraphic(ansichtO);
                zuege++;
            }
            else if (zuege < 9) {
                Random random = new Random();
                int zeile;
                int spalte;

                // "While-Schleife": Würfle so lange neu, bis ein LEERER Button getroffen wird
                do {
                    zeile = random.nextInt(3);  // Würfelt eine Zahl von 0 bis 2
                    spalte = random.nextInt(3); // Würfelt eine Zahl von 0 bis 2
                } while (feld[zeile][spalte] == 1 || feld[zeile][spalte] == 2);
                feld[zeile][spalte] = 2;
                javafx.scene.image.Image iconO = new javafx.scene.image.Image(Objects.requireNonNull(getClass().getResourceAsStream("/bild2.png")));
                ImageView ansichtO = new ImageView(iconO);
                ansichtO.setFitHeight(70);
                ansichtO.setFitWidth(70);
                btns[zeile][spalte].setGraphic(ansichtO);
                zuege++;
            }
            spielerReihe = true;
        }

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
                feld[i][e] = 0;
            }
        }
        zuege = 0;
        win = false;


    }

    /* 1 2 3
       4 5 6
       7 8 9 */
    public int check(int[][] feld, int score) {
        //vertical Vektor 0/1
        if (spielModi == 1) {


            for (int i = 0; i < feld[0].length; i++) {
                int result = checkLine(feld, i, 0, 0, 1, score);
                if (result != 0) {
                    return result;
                }

            }
            //horizontal Vektor 1/0
            for (int i = 0; i < feld[0].length; i++) {
                int result = checkLine(feld, 0, i, 1, 0, score);
                if (result != 0) {
                    return result;
                }

            }
            //diagonal / Vektor -1/1
            for (int i = 0; i < feld.length; i++) {
                //geht horizontal
                int result = checkLine(feld, 0, i, -1, 1, score);
                if (result != 0) {
                    return result;
                }
            }
            for (int i = 0; i < feld.length; i++) {
                int result = checkLine(feld, i, feld[0].length - 1, 1, -1, score);
                if (result != 0) {
                    return result;
                }

            }

            //diagonal\ Vektor 1/1
            for (int i = 0; i < feld.length; i++) {
                //geht horizontal
                int result = checkLine(feld, 0, i, 1, 1, score);
                if (result != 0) {
                    return result;
                }
            }
            for (int i = 0; i < feld.length; i++) {
                //geht diagonal von 0/0 bis 0/2
                int result = checkLine(feld, 0, i, 1, 1, score);
                if (result != 0) {
                    return result;
                }
            }

        }
        return 0;
    }

    public int checkLine(int[][] feld, int x, int y, int deltaX, int deltaY, int score) {
        if (spielModi == 1) {


            int height = feld.length;
            int width = feld[0].length;
            int currentSymbol = 0;
            int symbolCounter = 0;

            while (x >= 0 && x < width && y >= 0 && y < height) {
                int symbol = feld[y][x];

                if (symbol == 0) {
                    currentSymbol = 0;
                    symbolCounter = 0;
                } else if (symbol == currentSymbol) {
                    symbolCounter++;
                } else {
                    currentSymbol = symbol;
                    symbolCounter = 1;
                }
                if (symbolCounter == score && symbol != 0) {
                    //wer hat gewonnen
                    return symbol;
                }
                x = x + deltaX;
                y = y + deltaY;
            }
        }
        // wenn keine reihe gefunden wurde
        return 0;
    }
}
package com.example.javaprojectphase1;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class GameUI {
    private Label playerTurnLabel;
    private Label announcedTreasureLabel;

    public GameUI() {
        playerTurnLabel = new Label();
        announcedTreasureLabel = new Label();

        VBox vbox = new VBox(playerTurnLabel, announcedTreasureLabel);
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10));
        vbox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        // Add the VBox to the desired location in the game layout
    }

    public void updatePlayerTurn(boolean isPlayer1Turn) {
        playerTurnLabel.setText("Current Turn: " + (isPlayer1Turn ? "Player 1" : "Player 2"));
    }

    public void updateAnnouncedTreasure(String treasure) {
        announcedTreasureLabel.setText(treasure);
    }

    public Label getPlayerTurnLabel() {
        return playerTurnLabel;
    }

    public Label getAnnouncedTreasureLabel() {
        return announcedTreasureLabel;
    }

    public void displayDice(PlayerTurn player, Stage prime)
    {
        Label diceLabel;

        if(player.remainingMoves() == 0)
        {
            diceLabel = new Label("Press movement key to start other player's turn");
        }
        else
        {
            diceLabel = new Label("The number of remaining moves is : "+player.remainingMoves());
        }

        Scene scene = new Scene(diceLabel, 250, 100);
        prime.setScene(scene);
        prime.show();

    }


}

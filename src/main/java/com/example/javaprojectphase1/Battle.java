package com.example.javaprojectphase1;

import javafx.scene.layout.GridPane;

public class Battle {
    private Player player1;
    private Player player2;

    public Battle(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public void resolveBattle() {
        Player winner, loser;

        if (player1.getPower() >= player2.getPower()) {
            winner = player1;
            loser = player2;
        } else {
            winner = player2;
            loser = player1;
        }

        int moneyTransfer = ( (winner.getPower() - loser.getPower()) / (winner.getPower() + loser.getPower() + 1) ) * loser.getMoney();
        winner.addMoney(moneyTransfer);
        loser.removeMoney(moneyTransfer);

        winner.setPower(winner.getPower() - loser.getPower());
        loser.setPower(0);

        // Move the loser back to the starting position
        loser.setRow(0);
        loser.setCol(0);
        GridPane.setColumnIndex(loser, 0);
        GridPane.setRowIndex(loser, 0);
    }
}

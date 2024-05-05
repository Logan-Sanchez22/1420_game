package com.example.javaprojectphase1;

public class TurnManager {
    private boolean isPlayer1Turn;

    public TurnManager() {
        isPlayer1Turn = true; // Player 1 starts the game
    }

    public boolean isPlayer1Turn() {
        return isPlayer1Turn;
    }

    public void switchTurn() {
        isPlayer1Turn = !isPlayer1Turn;
    }
}

package com.example.javaprojectphase1;

import javafx.scene.control.Label;

import java.util.HashSet;
import java.util.Set;

public class PlayerTurn {
    private Set<String> visitedCells;
    private int remainingMoves;



    public PlayerTurn(int initialMoves) {
        visitedCells = new HashSet<>();
        remainingMoves = initialMoves;
    }

    public void addVisitedCell(int row, int col) {
        visitedCells.add(row + "," + col);
    }

    public boolean isVisited(int row, int col) {
        return visitedCells.contains(row + "," + col);
    }

    public void setRemainingMoves(int moves) {
        remainingMoves = moves;
    }

    public void decrementRemainingMoves() {
        remainingMoves--;
    }

    public boolean hasRemainingMoves() {
        return remainingMoves > 0;
    }

    public int remainingMoves()
    {
        return remainingMoves;
    }

}

package com.example.javaprojectphase1; // Make sure the package is the same

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.HashMap;
import java.util.Map;

public class Player extends Circle { // Mark the class as public
    private int row;
    private int col;
    private int score;
    private int money;
    private int power;

    private GridPane playerMap;

    private Map<String, Integer> inventory;
    static int count = 1;
    final int iD;

    public Player(Color color, double radius) {
        super(GameMap.CELL_SIZE / 2, GameMap.CELL_SIZE / 2, radius, color);
        row = 0;
        col = 0;
        score = 0;
        money = 1000; // initial money
        power = 100;  // initial power
        iD = count;
        count++;
        inventory = new HashMap<>();
    }


    public GridPane getPlayerMap() {
        return playerMap;
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getScore() {
        return score;
    }

    public void incrementScore() {
        score++;
    }

    public int getID()
    {
        return this.iD;
    }

    public int getMoney() {
        return money;
    }

    public void addMoney(int amount) {
        money += amount;
    }

    public void removeMoney(int amount) {
        if(amount >= this.money)
        {
            this.money = 0;
        }
        else {
            money -= amount;
        }

    }

    public int getPower() {
        return power;
    }

    public void addPower(int power) {
        this.power += power;
    }

    public void setPower(int power) {
        this.power = power;
    }
}


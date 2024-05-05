package com.example.javaprojectphase1;

import javafx.scene.paint.Color;

public class Weapon extends MapElement {
    private String name;
    private int power;

    public Weapon(int row, int col, String name, int power) {
        super(row, col, Color.RED);
        this.name = name;
        this.power = power;
    }

    public String getName() {
        return name;
    }

    public int getPower() {
        return power;
    }
}
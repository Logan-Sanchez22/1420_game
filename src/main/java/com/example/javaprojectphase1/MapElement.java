package com.example.javaprojectphase1;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class MapElement extends Rectangle {
    private int row;
    private int col;

    boolean beenVisted;

    public MapElement(int row, int col, Color color) {
        super(GameMap.CELL_SIZE, GameMap.CELL_SIZE, color);
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void beingVisted(){
        this.beenVisted = true;
    }

    public boolean statis()
    {
        return beenVisted;
    }

}
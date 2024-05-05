package com.example.javaprojectphase1;

import javafx.scene.paint.Color;

class Wall extends MapElement {
    public Wall(int row, int col) {
        super(row, col, Color.BLACK);
        this.setStroke(Color.BLACK);
    }
}

class Treasure extends MapElement {

    String  treaureName;
    public Treasure(int row, int col, QuestManager manager) {
        super(row, col, Color.GREEN);
        this.setStroke(Color.BLACK);
        setName(manager);
    }

    public void setName(QuestManager manager){
        treaureName = manager.getTreasure();
    }

    public String getName()
    {
        return treaureName;
    }

    public boolean thisOne(int row, int col)
    {
        boolean flag1 = false;
        boolean flag2 = false;
        if(getRow() == row)
        {
            flag1 = true;
        }
        if(getCol() == col)
        {
            flag2 = true;
        }

        if(flag1 && flag2 )
        {
            return true;
        }
        else
        {
            return false;
        }

    }

}

class Market extends MapElement {
    public Market(int row, int col) {
        super(row, col, Color.ORANGE);
        this.setStroke(Color.BLACK);
    }
}

class LostItem extends MapElement {
    public LostItem(int row, int col) {
        super(row, col, Color.SKYBLUE);
        this.setStroke(Color.BLACK);
    }
}

class Trap extends MapElement {
    public Trap(int row, int col) {
        super(row, col, Color.RED);
        this.setStroke(Color.BLACK);
    }
}
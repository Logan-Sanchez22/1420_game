package com.example.javaprojectphase1;

import javafx.scene.control.Label;

import java.util.Random;

public class DiceRoller {
    private static final int MAX_DICE_VALUE = 6;
    private static final Random random = new Random();



    public static int rollDice() {

        int roll;
        roll = random.nextInt(MAX_DICE_VALUE+1);
        if(roll == 0)
        {
            roll = 1;
        }

        return roll;

    }
}

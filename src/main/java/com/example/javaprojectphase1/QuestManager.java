package com.example.javaprojectphase1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class QuestManager {
    private static final List<String> TREASURES = Arrays.asList(
            "Diamond Ring", "Jewel-encrusted Sword", "Golden Goblet", "Crystal Goblets",
            "Wooden Bow", "Paladin's Shield", "Golden Key", "Dragon's Scroll"
    );

    private ArrayList<String> accsessed = new ArrayList<String>();

    private static final Random random = new Random();

    private String announcedTreasure;

    public QuestManager() {
        announceTreasure();
    }

    public String getAnnouncedTreasure() {
        return announcedTreasure;
    }

    public void announceTreasure() {
        announcedTreasure = TREASURES.get(random.nextInt(TREASURES.size()));
    }

    public String getTreasure(){

        String returner = TREASURES.get(random.nextInt(TREASURES.size()));
        boolean flag = false;
        int length = accsessed.size();

        while(flag)
        {
            for(int i=0; i<length; i++)
            {
                flag = returner.equalsIgnoreCase(accsessed.get(i));
            }

            if(flag)
            {
                returner = TREASURES.get(random.nextInt(TREASURES.size()));
            }

            else{
                accsessed.add(returner);
            }

        }

        return returner;

    }

    public boolean isGameEnd(){
        int length = accsessed.size();
        int length2 = TREASURES.size();
        if(length == length2)
        {
            return true;
        }
        else {
            return false;
        }
    }

    public void updateList(){

    }
}
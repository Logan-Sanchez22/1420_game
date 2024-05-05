package com.example.javaprojectphase1;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MarketHouse extends MapElement {
    private Map<String, Integer> inventory;
    private Map<String, Integer> prices;
    private Random random;

    public MarketHouse(int row, int col) {
        super(row, col, Color.ORANGE);
        random = new Random();
        inventory = new HashMap<>();
        prices = new HashMap<>();

        // Initialize the inventory and prices
        inventory.put("Sword", 5);
        inventory.put("Dagger", 10);
        inventory.put("Magic-staff", 1);
        inventory.put("Treasure Location", 8); // Add the treasure location item

        prices.put("Sword", 20);
        prices.put("Dagger", 5);
        prices.put("Magic-staff", 200);
        prices.put("Treasure Location", 100); // Set the price for Treasure Location
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public Map<String, Integer> getPrices() {
        return prices;
    }

    public boolean buyItem(String item, Player player) {
        if (inventory.containsKey(item) && inventory.get(item) > 0 && player.getMoney() >= prices.get(item)) {
            player.removeMoney(prices.get(item));
            inventory.put(item, inventory.get(item) - 1);
            if (item.equals("Treasure Location")) {
                revealRandomTreasure(player, GameMap.treasures);
            }
            return true;
        }
        return false;
    }

    private void revealRandomTreasure(Player player, ArrayList<Treasure> treasures) {
        if (!treasures.isEmpty()) {
            int randomIndex = random.nextInt(treasures.size());
            Treasure treasure = treasures.get(randomIndex);
            int row = treasure.getRow();
            int col = treasure.getCol();
            GameMap.revealTreasureLocation(player, row, col);
        }
    }

    public int sellItem(String item, Player player, int amount) {
        if (!player.getInventory().containsKey(item) || player.getInventory().get(item) < amount) {
            return 0;
        }

        int sellPrice = prices.getOrDefault(item, 0) * amount;
        player.addMoney(sellPrice);
        player.getInventory().put(item, player.getInventory().get(item) - amount);
        return sellPrice;
    }
}
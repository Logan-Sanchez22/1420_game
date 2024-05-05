package com.example.javaprojectphase1;

import javafx.application.Application;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class GameMap extends Application {



    private static final int SIZE = 10; // determine size of grid (10x10)
    public static final int CELL_SIZE = 50; // pixel size
    private static final double PLAYER_RADIUS = CELL_SIZE * 0.5;

    public static ArrayList<Treasure> treasures;
    private Player player1;
    private Player player2;
    private ArrayList<Wall> walls;
    private ArrayList<Market> markets;
    private ArrayList<LostItem> lostItems;
    private ArrayList<Trap> traps;
    private ArrayList<Weapon> weapons;

    private ArrayList<MarketHouse> marketHouses;

    private ScoreBoard scoreBoard;
    private Timeline gametimer;
    private PlayerTurn player1Turn;
    private PlayerTurn player2Turn;
    private long elapsedTime;
    private TurnManager turnManager;
    private QuestManager questManager;
    private GameUI gameUI;
    @Override
    public void start(Stage stage) {
        GridPane grid = new GridPane();
        Random rand = new Random();

        // Initialize QuestManager
        questManager = new QuestManager();

        initializeElements(rand);
        addElementsToGrid(grid, rand);

        // Add the castle in the middle
        Rectangle castle = new Rectangle(CELL_SIZE, CELL_SIZE, Color.YELLOW);
        castle.setStroke(Color.BLACK);
        grid.add(castle, SIZE / 2 - 1, SIZE / 2 - 1);

        // Add the players outside the map
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(grid); // where 'grid' is your GridPane instance
        stackPane.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: white;");

        // Extra 1x1 grid for spawn point
        Rectangle spawnPoint = new Rectangle(CELL_SIZE, CELL_SIZE, Color.LIGHTGRAY);
        spawnPoint.setStroke(Color.BLACK);
        grid.add(spawnPoint, SIZE, SIZE - 1); // Add to the extra grid cell to the right of the bottom right

        // Set player1 at the spawn point
        player1.setRow(SIZE - 1);
        player1.setCol(SIZE);
        GridPane.setColumnIndex(player1, SIZE);
        GridPane.setRowIndex(player1, SIZE - 1);
        grid.add(player1, SIZE, SIZE - 1);

        // Set player2 at the spawn point
        player2.setRow(SIZE - 1);
        player2.setCol(SIZE);
        GridPane.setColumnIndex(player2, SIZE);
        GridPane.setRowIndex(player2, SIZE - 1);
        grid.add(player2, SIZE, SIZE - 1);

        DiceRoller dice = new DiceRoller();

        // Initialize PlayerTurn for each player
        player1Turn = new PlayerTurn(dice.rollDice()); // Assuming the initial number of moves is 1 for player 1
        player2Turn = new PlayerTurn(dice.rollDice()); // Assuming the initial number of moves is 1 for player 2

        // Initialize TurnManager
        turnManager = new TurnManager();



        // Initialize ScoreBoard
        scoreBoard = new ScoreBoard();

        // Initialize GameUI
        gameUI = new GameUI();

        VBox vbox = new VBox(scoreBoard.elapsedTimeLabel(),  scoreBoard.getBlankLabel(), gameUI.getPlayerTurnLabel(), scoreBoard.getPlayer1ScoreLabel(),
                scoreBoard.getPLayer1MoneyLabel(), scoreBoard.getPlayer1PowerLabel(), scoreBoard.getBlank2(), scoreBoard.getPlayer2ScoreLabel(),
                scoreBoard.getPlayer2MoneyLabel(), scoreBoard.getPlayer2PowerLabel(), scoreBoard.getBlank3(),
                scoreBoard.playerRemainingMovesLabel(),  scoreBoard.getBlank4(),
                scoreBoard.getBuy1(), scoreBoard.getBuy2(), scoreBoard.getBuy3(), scoreBoard.getBuy4(),
                scoreBoard.getSell1(), scoreBoard.getSell2(), scoreBoard.getSell3());
        HBox hbox = new HBox(grid, vbox);


        Battle battler = new Battle(player1, player2);

        initializeGameTimer();

        Scene scene = new Scene(hbox);


        scene.setOnKeyPressed(e -> {
            Player currentPlayer = turnManager.isPlayer1Turn() ? player1 : player2;
            PlayerTurn currentTurn = turnManager.isPlayer1Turn() ? player1Turn : player2Turn;


            handleKeyPress(e.getCode(), currentPlayer, currentTurn, turnManager, questManager, gameUI, grid);

        });


        stage.setTitle("Travelling Salesman");
        stage.setScene(scene);
        stage.show();

        Stage secondary = new Stage();


        scene.setOnKeyPressed(e -> {
            Player currentPlayer = turnManager.isPlayer1Turn() ? player1 : player2;
            PlayerTurn currentTurn = turnManager.isPlayer1Turn() ? player1Turn : player2Turn;

            if(currentTurn.hasRemainingMoves() == false)
            {
                int tempID;
                tempID = currentPlayer.getID();

                if(tempID == 1)
                {
                    player1Turn = new PlayerTurn(dice.rollDice());
                }
                else {
                    player2Turn = new PlayerTurn(dice.rollDice());
                }

            }

            int rowP1 = player1.getRow();
            int colP1 = player1.getCol();
            int rowP2 = player2.getRow();
            int colP2 = player2.getCol();

            int isSpawn = 0;

            if(rowP1 == 0 && colP1 == 0)
            {
                isSpawn = 1;
            }

            if(rowP1 == 9 && colP1 == 9)
            {
                isSpawn = 1;
            }

            if(rowP1 == 9 && colP1 == 10)
            {
                isSpawn = 1;
            }

            if( (rowP1 == rowP2 && colP1 == colP2) && (isSpawn != 1) )
            {
                battler.resolveBattle();
            }

            gameUI.updatePlayerTurn(player1Turn.hasRemainingMoves());
            //gameUI.displayDice(currentTurn, secondary);

            //scoreBoard.updateBoard(player1, player2, stackPane);

            handleKeyPress(e.getCode(), currentPlayer, currentTurn, turnManager, questManager, gameUI, grid);

        });

        if(questManager.isGameEnd()) {
           // player1.addMoney(100);
            endGame(player1, player2);
        }


    }

    private void initializeGameTimer() {
        elapsedTime = 0;
        GameTimerHandler timerHandler = new GameTimerHandler();
        Timeline gameTimer = new Timeline(new KeyFrame(Duration.seconds(1), timerHandler));
        gameTimer.setCycleCount(Animation.INDEFINITE);
        gameTimer.play();
    }

    private class GameTimerHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            elapsedTime++;
            // Ensure scoreBoard is not null before updating
            if (scoreBoard != null) {
                scoreBoard.updateElapsedTime(elapsedTime);
            }
        }
    }

    private void initializeElements(Random rand) {
        walls = new ArrayList<>();
        treasures = new ArrayList<>();
        markets = new ArrayList<>();
        lostItems = new ArrayList<>();
        traps = new ArrayList<>();
        marketHouses = new ArrayList<>();
        weapons = new ArrayList<>();

        // Add some weapons
        weapons.add(new Weapon(rand.nextInt(SIZE), rand.nextInt(SIZE), "Sword", 10));
        weapons.add(new Weapon(rand.nextInt(SIZE), rand.nextInt(SIZE), "Bow", 8));
        weapons.add(new Weapon(rand.nextInt(SIZE), rand.nextInt(SIZE), "Axe", 12));




        int midpoint = SIZE / 2;
        int leftCol = rand.nextInt(midpoint - 2 );
        int rightCol = rand.nextInt(midpoint - 2) + midpoint + 1;

        // Add 5 Market Houses
        for (int i = 0; i < 5; i++) {
            int row = rand.nextInt(SIZE);
            int col = rand.nextInt(SIZE);
            marketHouses.add(new MarketHouse(row, col));
        }

        // Add 5 walls
        for (int i = 0; i < 5; i++) {
            int col = i < 2.5 ? rand.nextInt(midpoint - 2) : rand.nextInt(SIZE - midpoint - 2) + midpoint + 1;
            walls.add(new Wall(rand.nextInt(SIZE), col));
        }

        // Add 8 treasures
        for (int i = 0; i < 8; i++) {
            int col = i < 4 ? rand.nextInt(midpoint - 2) : rand.nextInt(SIZE - midpoint - 2) + midpoint + 1;
            treasures.add(new Treasure(rand.nextInt(SIZE), col, questManager));
        }


        // Add 13 lost items
        for (int i = 0; i < 13; i++) {
            int col = i < 6.5 ? rand.nextInt(midpoint - 2) : rand.nextInt(SIZE - midpoint - 2) + midpoint + 1;
            lostItems.add(new LostItem(rand.nextInt(SIZE), col));
        }

        // Add at least 1 trap (random number)
        int numberOfTraps = rand.nextInt(10) + 1;
        for (int i = 0; i < numberOfTraps; i++) {
            traps.add(new Trap(rand.nextInt(SIZE), rand.nextInt(SIZE)));
        }

        // Set player1 at the bottom right
        player1 = new Player(Color.BROWN, PLAYER_RADIUS);
        player1.setRow(SIZE - 1);
        player1.setCol(SIZE - 1);
        player1.setStroke(Color.BLACK);

        // Set player2 at the bottom right
        player2 = new Player(Color.PINK, PLAYER_RADIUS);
        player2.setRow(SIZE - 1);
        player2.setCol(SIZE - 1);
        player2.setStroke(Color.BLACK);
    }

    private void addElementsToGrid(GridPane grid, Random rand) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE, Color.LIGHTYELLOW);
                cell.setStroke(Color.BLACK);
                grid.add(cell, j, i);
            }
        }

        addElementsToGridHelper(grid, walls);
        addElementsToGridHelper(grid, treasures);
        addElementsToGridHelper(grid, lostItems);
        addElementsToGridHelper(grid, traps);
        addElementsToGridHelper(grid, marketHouses);
        addElementsToGridHelper(grid, weapons);
    }


    private MapElement getMapElementAtPosition(int row, int col, GridPane grid) {
        for (Node node : grid.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col && node instanceof MapElement) {
                return (MapElement) node;
            }
        }
        return null;
    }

    private void addElementsToGridHelper(GridPane grid, ArrayList<? extends MapElement> elements) {
        for (MapElement element : elements) {
            int row = element.getRow();
            int col = element.getCol();

            if (!isValidPosition(row, col)) {
                continue; // Skip invalid positions
            }

            if (!isOverlapping(grid, row, col)) {
                grid.add(element, col, row);
            }
        }
    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE;
    }

    private boolean isOverlapping(GridPane grid, int row, int col) {
        return grid.getChildren().stream()
                .filter(node -> GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col)
                .anyMatch(node -> node instanceof MapElement);
    }


    private PlayerTurn playerTurn;
    private boolean isWallCell(int row, int col, GridPane grid) {
        for (Wall wall : walls) {
            if (wall.getRow() == row && wall.getCol() == col) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidMove(Player player, int newRow, int newCol, PlayerTurn playerTurn, GridPane grid) {
        // Check if the new position is within the grid boundaries
        if (!isValidPosition(newRow, newCol)) {
            return false;
        }

        // Check if the new position is a wall
        if (isWallCell(newRow, newCol, grid)) {
            return false;
        }

        // Check if the player has already visited the new position in the current turn
        if (playerTurn.isVisited(newRow, newCol)) {
            return false;
        }

        // Check if the player has moved more cells than the number rolled on the die
        if (!playerTurn.hasRemainingMoves()) {
            return false;
        }

        // Add the new position to the visited cells
        playerTurn.addVisitedCell(newRow, newCol);

        // Decrement the remaining moves
        playerTurn.decrementRemainingMoves();

        return true;
    }




    private void handleKeyPress(KeyCode code, Player player, PlayerTurn playerTurn, TurnManager turnManager,
                                QuestManager questManager, GameUI gameUI, GridPane grid) {
        // Check if it's the player's turn and handle the key press
        if (turnManager.isPlayer1Turn() && player == player1) {
            handlePlayerMove(code, player, player1Turn, turnManager, questManager, gameUI, grid);
        } else if (!turnManager.isPlayer1Turn() && player == player2) {
            handlePlayerMove(code, player, player2Turn, turnManager, questManager, gameUI, grid);
        }
    }

    private void handlePlayerMove(KeyCode code, Player player, PlayerTurn playerTurn, TurnManager turnManager,
                                  QuestManager questManager, GameUI gameUI, GridPane grid) {
        int newRow = player.getRow();
        int newCol = player.getCol();
        switch (code) {
            case UP:
                newRow--;
                break;
            case DOWN:
                newRow++;
                break;
            case LEFT:
                newCol--;
                break;
            case RIGHT:
                newCol++;
                break;
            default:
                return;
        }

        if (isValidMove(player, newRow, newCol, playerTurn, grid)) {
            player.setRow(newRow);
            player.setCol(newCol);
            GridPane.setColumnIndex(player, newCol);
            GridPane.setRowIndex(player, newRow);

            // Check if the player has moved into a market house
            MapElement mapElement = getMapElementAtPosition(newRow, newCol, grid);
            if (mapElement instanceof MarketHouse) {
                handleMarketHouseInteraction(player, (MarketHouse) mapElement);
            }

            // After a valid move, switch the turn
            if(playerTurn.hasRemainingMoves() == false){
                turnManager.switchTurn();
            }

            questManager.announceTreasure();

            Player currentPlayer = turnManager.isPlayer1Turn() ? player1 : player2;

            if(turnManager.isPlayer1Turn()) {
                scoreBoard.updatePlayer1Money(currentPlayer.getMoney());
                scoreBoard.updatePlayer1Power(currentPlayer.getPower());
                scoreBoard.updatePlayer1Score(currentPlayer.getScore());
                scoreBoard.updatePlayerRemainingMoves(playerTurn.remainingMoves());
                scoreBoard.marketOptions();
            } else {
                scoreBoard.updatePlayer2Money(currentPlayer.getMoney());
                scoreBoard.updatePlayer2Power(currentPlayer.getPower());
                scoreBoard.updatePlayer2Score(currentPlayer.getScore());
                scoreBoard.updatePlayerRemainingMoves(playerTurn.remainingMoves());
                scoreBoard.marketOptions();
            }

            if (mapElement instanceof Weapon) {
                Weapon weapon = (Weapon) mapElement;
                player.addPower(weapon.getPower());
                showMessage("You picked up a " + weapon.getName() + " and gained " + weapon.getPower() + " power!");
            }

            if (mapElement instanceof LostItem) {

                if(mapElement.beenVisted)
                {
                    showMessage("The loot here has already been looted!");
                }
                else {
                    mapElement.beingVisted();
                    Random random = new Random();
                    int gem;
                    int value;
                    gem = random.nextInt(3);
                    if (gem == 1) {
                        value = random.nextInt(10) + 1;
                        player.addMoney(value);
                        showMessage("You found and picked up lost change! You gained " + value + " gold!");
                    } else if(gem == 2) {
                        value = random.nextInt(10) + 10;
                        player.addMoney(value);
                        showMessage("You found and picked up a precious gem! You gained " + value + " gold!");
                    } else {
                        value = random.nextInt(10) + random.nextInt(10) + random.nextInt(10) + 10;
                        player.addMoney(value);
                        showMessage("You found and opened a chest of gold! You gained " + value + " gold!");
                    }

                }

            }

            if (mapElement instanceof Trap) {
                Random random = new Random();
                showMessage("Oh no! You stepped onto a trap and have lost " +random.nextInt(50) + " gold while running away");
            }

            if (newRow == 4 && newCol == 4) {

                showMessage("Welcome to the Castle!!\nAre you wishing to sell treasure, adventurer?");
                showMessage("Enter yes or no");
                //assuming yes
                showMessage("Which treasure have you found?\nEnter the corresponding treasure number on the following list");
                showMessage("1.Diamond Ring\n2.Jewel-encrusted Sword\n3.Golden Goblet\n4.Crystal Goblets\n" +
                        "5.Wooden Bow\n6.Paladin's Shield\n7.Golden Key\n8.Dragon's Scroll\n");
            }

            if (mapElement instanceof Treasure) {
                int length = treasures.size();
                boolean thisOne;
                int index =0;
                Treasure ours;

                for (int i = 0; i < length; i++){
                    thisOne = treasures.get(i).thisOne(newRow, newCol);
                    if(thisOne)
                    {
                        index = i;
                    }

                }
                ours = treasures.get(index);

                showMessage("You have found " + ours.getName() + ". A legendary treasure! Go to the castle and claim to get the gold!");

            }


            // Update the UI with the current turn and announced treasure
            gameUI.updatePlayerTurn(turnManager.isPlayer1Turn());
            gameUI.updateAnnouncedTreasure(questManager.getAnnouncedTreasure());
        }
    }


    private void handleMarketHouseInteraction(Player player, MarketHouse marketHouse) {
        // Display the market house inventory and prices
        StringBuilder inventoryText = new StringBuilder("Market House Inventory:\n");
        for (Map.Entry<String, Integer> entry : marketHouse.getInventory().entrySet()) {
            String item = entry.getKey();
            int quantity = entry.getValue();
            int price = marketHouse.getPrices().get(item);
            inventoryText.append(item).append(": ").append(quantity).append(" (Price: ").append(price).append(")\n");
        }

        // Display the inventory in a popup window
        /*
        Alert inventoryAlert = new Alert(AlertType.INFORMATION, inventoryText.toString(), ButtonType.OK);
        inventoryAlert.setTitle("Market House");
        inventoryAlert.setHeaderText(null);
        inventoryAlert.showAndWait();
         */

        // Prompt the player to buy or sell items
        boolean validInput;
        do {
            validInput = false;
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Market House");
            dialog.setHeaderText(null);
            dialog.setContentText("Enter 'buy <item>' to buy an item or 'sell <item> <amount>' to sell an item.");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                String input = result.get();
                String[] inputParts = input.split(" ");
                if (inputParts.length >= 2) {
                    if (inputParts[0].equalsIgnoreCase("buy")) {
                        String item = inputParts[1];
                        if (marketHouse.buyItem(item, player)) {
                            player.getInventory().put(item, player.getInventory().getOrDefault(item, 0) + 1);
                            showMessage("Bought " + item + ".");
                            validInput = true;
                        } else {
                            showMessage("Could not buy " + item + ".");
                        }
                    } else if (inputParts[0].equalsIgnoreCase("sell") && inputParts.length >= 3) {
                        String item = inputParts[1];
                        int amount = Integer.parseInt(inputParts[2]);
                        int sellPrice = marketHouse.sellItem(item, player, amount);
                        if (sellPrice > 0) {
                            showMessage("Sold " + amount + " " + item + " for " + sellPrice + " gold.");
                            validInput = true;
                        } else {
                            showMessage("Could not sell " + item + ".");
                        }
                    }
                }
            } else {
                // User canceled the dialog, exit the loop
                break;
            }
        } while (!validInput);
    }

    private void showMessage(String message) {
        Alert alert = new Alert(AlertType.INFORMATION, message, ButtonType.OK);
        alert.setTitle("Market House");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public static void revealTreasureLocation(Player player, int row, int col) {
        // Assuming you have a field in the Player class to store their map
        GridPane playerMap = player.getPlayerMap();

        if (playerMap != null) {
            for (Node node : playerMap.getChildren()) {
                if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                    if (node instanceof MapElement) {
                        MapElement mapElement = (MapElement) node;
                        if (mapElement instanceof Treasure) {
                            node.setStyle("-fx-background-color: green;");
                            break;
                        }
                    }
                }
            }
        }
    }

    public void endGame(Player player1, Player player2){
        Player winner;
        if(player1.getMoney() > player2.getMoney())
        {
            winner = player1;
            showMessage("The game has ended! All treasures have been found! The player with the most gold wins!");
            showMessage("Congratulations to player1");
        }
        else if(player1.getMoney() == player2.getMoney())
        {
            showMessage("The game has ended! All treasures have been found! The player with the most gold wins!");
            showMessage("Both players have gotten the same gold! No player wins");
        }
        else {
            winner = player2;
            showMessage("The game has ended! All treasures have been found! The player with the most gold wins!");
            showMessage("Congratulations to player2");
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
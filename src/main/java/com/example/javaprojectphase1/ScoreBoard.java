package com.example.javaprojectphase1;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class ScoreBoard extends VBox {
    private Label playerTurnLabel;
    private Label player1ScoreLabel;
    private Label player2ScoreLabel;
    private Label player1MoneyLabel;
    private Label player2MoneyLabel;
    private Label player1PowerLabel;
    private Label player2PowerLabel;
    private Label elapsedTimeLabel;
    private Label currentQuestLabel;
    private Label foundTreasuresLabel;
    private Label playerRemainingMovesLabel;

    private Label marketBuyabel1;

    private Label marketBuyabel2;

    private Label marketBuyabel3;

    private Label marketBuyabel4;

    private Label marketSellabel1;

    private Label marketSellabel2;

    private Label marketSellabel3;

    private Label blank;
    private Label blank2;
    private Label blank3;

    private Label blank4;


    public ScoreBoard() {
        playerTurnLabel = new Label();
        player1ScoreLabel = new Label();
        player2ScoreLabel = new Label();
        player1MoneyLabel = new Label();
        player2MoneyLabel = new Label();
        player1PowerLabel = new Label();
        player2PowerLabel = new Label();
        elapsedTimeLabel = new Label();
        currentQuestLabel = new Label();
        foundTreasuresLabel = new Label();
        playerRemainingMovesLabel = new Label();
        marketBuyabel1 = new Label();
        marketBuyabel2 = new Label();
        marketBuyabel3 = new Label();
        marketBuyabel4 = new Label();
        marketSellabel1 = new Label();
        marketSellabel2 = new Label();
        marketSellabel3 = new Label();
        blank = new Label();
        blank2 = new Label();
        blank3 = new Label();
        blank4 = new Label();



        getChildren().addAll(
                playerTurnLabel,
                player1ScoreLabel,
                player2ScoreLabel,
                player1MoneyLabel,
                player2MoneyLabel,
                player1PowerLabel,
                player2PowerLabel,
                elapsedTimeLabel,
                currentQuestLabel,
                foundTreasuresLabel,
                playerRemainingMovesLabel,
                blank,
                marketBuyabel1,
                marketBuyabel2,
                marketBuyabel3,
                marketBuyabel4,
                marketSellabel1,
                marketSellabel2,
                marketSellabel3,
                blank2,
                blank3,
                blank4
        );

        blank.setText("");
        blank2.setText("");
        blank3.setText("");
        blank4.setText("");

        setSpacing(5);
        setAlignment(Pos.TOP_LEFT);
        setPadding(new Insets(10));
        setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public void updatePlayerTurn(boolean isPlayer1Turn) {
        playerTurnLabel.setText("Current Turn: " + (isPlayer1Turn ? "Player 1" : "Player 2"));
    }

    public void updatePlayer1Score(int score) {
        player1ScoreLabel.setText("Player 1 Score: " + score);
    }

    public void updatePlayer2Score(int score) {
        player2ScoreLabel.setText("Player 2 Score: " + score);
    }

    public void updatePlayer1Money(int money) {
        player1MoneyLabel.setText("Player 1 Money: " + money);
    }

    public void updatePlayer2Money(int money) {
        player2MoneyLabel.setText("Player 2 Money: " + money);
    }

    public void updatePlayer1Power(int power) {
        player1PowerLabel.setText("Player 1 Power: " + power);
    }

    public void updatePlayer2Power(int power) {
        player2PowerLabel.setText("Player 2 Power: " + power);
    }

    public void marketOptions() {
        marketBuyabel1.setText("Sword: A Sharp and fearsome sword. Made of mythril steel, gives a +10 to power. [cost: 20 gold]");
        marketBuyabel2.setText("Dagger: A very pointy dagger. Aim away when running, gives a +5 to power. [cost: 5 gold]");
        marketBuyabel3.setText("Staff of Ruin: Beware enemies. Brings ruin upon all, gives a +50 to power. [cost: 200 gold]");
        marketBuyabel4.setText("Treasure Location: A map of the world's great treaures, the greedy merchant only ever gives one at a time. Reveals a treasure on the map. [cost: 100 gold]");
        marketSellabel1.setText("Sell Sword. [gain: 15 gold]");
        marketSellabel2.setText("Sell Dagger. [gain: 0 gold]");
        marketSellabel3.setText("Sell Staff of Ruin. [cost: 170 gold]");
    }

    public void updatePlayerRemainingMoves(int moves) {
        playerRemainingMovesLabel.setText("The number of remaining moves is:  " + moves);
    }

    public void updateElapsedTime(long elapsedTime) {
        elapsedTimeLabel.setText("Elapsed Time: " + elapsedTime + " seconds");
    }

    public void updateCurrentQuest(String quest) {
        currentQuestLabel.setText("Current Quest: " + quest);
    }

    public void updateFoundTreasures(int foundTreasures) {
        foundTreasuresLabel.setText("Found Treasures: " + foundTreasures);
    }

    public Label getPlayer1ScoreLabel() {
        return player1ScoreLabel;
    }

    public Label getPLayer1MoneyLabel() {
        return player1MoneyLabel;
    }

    public Label getPlayer2ScoreLabel(){
        return player2ScoreLabel;
    }

    public Label getPlayer2MoneyLabel(){
        return player2MoneyLabel;
    }

    public Label getPlayer1PowerLabel(){
        return player1PowerLabel;
    }

    public Label getPlayer2PowerLabel(){
        return player2PowerLabel;
    }

    public Label getBlankLabel(){
        return blank;
    }

    public Label getBlank2(){
        return blank2;
    }

    public Label getBlank3(){
        return blank3;
    }

    public Label getBlank4(){
        return blank4;
    }

    public Label getBuy1(){
        return marketBuyabel1;
    }
    public Label getBuy2(){
        return marketBuyabel2;
    }
    public Label getBuy3(){
        return marketBuyabel3;
    }
    public Label getBuy4(){
        return marketBuyabel4;
    }

    public Label getSell1(){
        return marketSellabel1;
    }
    public Label getSell2(){
        return marketSellabel2;
    }
    public Label getSell3(){
        return marketSellabel3;
    }

    public Label playerRemainingMovesLabel(){
        return playerRemainingMovesLabel;
    }

    public Label elapsedTimeLabel(){
        return elapsedTimeLabel;
    }

    public void updateBoard(Player p1, Player p2, StackPane stack)
    {
        updatePlayer1Score(p1.getScore());
        updatePlayer2Score(p2.getScore());
        updatePlayer1Money(p1.getMoney());
        updatePlayer2Money(p2.getMoney());
        updatePlayer1Power(p1.getPower());
        updatePlayer2Power(p2.getPower());

        Scene p1s = new Scene(player1PowerLabel, 200, 200);



    }
}

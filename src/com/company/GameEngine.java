package com.company;

import java.util.ArrayList;
import java.util.List;

public class GameEngine {
    private List<Player> playerList;
    private int playerNum = 1;
    List<String> allNames = new ArrayList<>();
    private CardDeck deck;
    private List<List<CardType>> hands;

    public GameEngine(List<Player> playerListIn) {
        this.playerList = playerListIn;

        //compile names
        for (; playerNum <= playerList.size(); playerNum++) {
            allNames.add("Player " + playerNum);
        }
        //init players
        for (int i = 0; i < playerNum - 1; i++) {
            playerList.get(i).init(allNames.get(i), allNames);
        }

        //set new game for each player
        for (Player player : playerList) {
            player.newGame();
        }

    }

    public void playGame(){
        final int ROUNDS = 3;
        deck = new CardDeck();

        for(int i = 0; i < ROUNDS; i++) {
            //deal the initial hands
            for (Player player : playerList) {
                player.receiveHand(generateHand());
            }


        }
    }

    private List<CardType> generateHand() {
        int cardsPerHand = 12 - playerNum;
        List<CardType> hand = new ArrayList<>();

        for(int i = 0; i < cardsPerHand; i++) {
            hand.add(deck.takeCard());
        }
        return hand;
    }
}

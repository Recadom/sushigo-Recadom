package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameEngine {
    private List<Player> playerList;
    private List<String> allNames = new ArrayList<>();
    private CardDeck deck = new CardDeck();
    private List<List<CardType>> cardsPlayed;
    private Map<String, Integer> pointMap;
    private int cardsPerHand;

    public GameEngine(List<Player> playerListIn) {
        this.playerList = playerListIn;

        //compile names
        for (int i = 0; i < playerList.size(); i++) {
            allNames.add("Player " + (i+1));
        }

        //init players
        for (int i = 0; i < playerList.size(); i++) {
            playerList.get(i).init(allNames.get(i), allNames);
        }

        //set new game for each player
        for (Player player : playerList) {
            player.newGame();
        }

        cardsPerHand = 12 - playerList.size();

    }

    public void playGame(){
        final int ROUNDS = 3;
        deck = new CardDeck();
        deck.shuffle();
        int cardsPlayedCnt = 0;
        pointMap = new HashMap<>();

        for(int i = 0; i < ROUNDS; i++) {

            //deal the initial hands
            for (Player player : playerList) {
                player.receiveHand(generateHand());
            }

            //repeat until last card has been played
            while(cardsPlayedCnt < cardsPerHand * playerList.size()) {
                cardsPlayed = new ArrayList<>();

                //receive cards to play from players
                for (Player player : playerList) {
                    List<CardType> currentCardPlay = player.giveCardsPlayed();
                    cardsPlayed.add(currentCardPlay);
                    cardsPlayedCnt += currentCardPlay.size();
                }

                //tally current score
                for (Player player : playerList) {
                    pointMap.put(player.getName(), 10);
                }

                //send scores to each player
                for (Player player : playerList) {
                    player.endRound(pointMap);
                }
            }

        }
    }

    public List<CardType> generateHand() {
        List<CardType> hand = new ArrayList<>();

        for(int i = 0; i < cardsPerHand; i++) {
            CardType card = deck.takeCard();
            //System.out.println(card);
            hand.add(card);
            //System.out.println(deck.takeCard());
        }
        return hand;
    }

}

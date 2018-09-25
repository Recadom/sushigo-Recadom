package com.company;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameEngine {
    private List<Player> playerList;
    private List<String> allNames = new ArrayList<>();
    private CardDeck deck = new CardDeck();
    private ArrayList<List<CardType>> cardsPlayed;
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

        cardsPerHand = 12 - playerList.size(); //TODO add check for correct num of players

    }

    public void playGame(){
        final int ROUNDS = 1; //TODO change to 3
        deck = new CardDeck();
        deck.shuffle();
        int cardsPlayedCnt = 0;
        pointMap = new HashMap<>();
        cardsPlayed = new ArrayList<>();


        for(int i = 0; i < ROUNDS; i++) {

            //deal the initial hands
            for (Player player : playerList) {
                List<CardType> cards = generateHand();
                player.receiveHand(generateHand());
                cardsPlayed.add(new ArrayList<CardType>());
            }

            //repeat until last card has been played
            while(cardsPlayedCnt < cardsPerHand * playerList.size()) {
                //cardsPlayed = new ArrayList<>();

                //receive cards to play from players
                List<TurnResult> turnResults = new ArrayList<>();
                for (int playerNum = 0; playerNum < playerList.size(); playerNum++) {            //TODO make .size() a variable
                    Player player = playerList.get(playerNum);
                    List<CardType> currentCardPlay = player.giveCardsPlayed(); //TODO add check for ch st & amt of cards

                    cardsPlayed.get(playerNum).add(currentCardPlay.get(0)); //TODO make this take multiple cards
                    if (currentCardPlay.size() > 1) {
                        cardsPlayed.get(playerNum).add(currentCardPlay.get(1));
                    }

                    //System.out.println(currentCardPlay);
                    cardsPlayedCnt += currentCardPlay.size(); //TODO may need to change to Tablehand = 8 for chopsticks

                    //compile turn data
                    //List<CardType> playerTableHand = new ArrayList<>(); //TODO make this larger scope
                    //System.out.println(cardsPlayed.get(0).toString());

                    //TurnResult turn = new TurnResult(player.getName(),currentCardPlay, cardsPlayed.get(playerNum));
                    turnResults.add(new TurnResult(player.getName(),currentCardPlay, cardsPlayed.get(playerNum)));
                }

                //send turn data
                for (Player recPlayer : playerList) {
                    recPlayer.receiveTurnResults(turnResults);
                    //System.out.println(turnResults.toString());
                }

                //tally current score
                for (Player player : playerList) {
                    pointMap.put(player.getName(), 10);
                }


                //System.out.println(cardsPlayedCnt);
            }

            //send scores to each player
            for (Player player : playerList) {
                player.endRound(pointMap);
            }

            //send final scores
            for (Player player : playerList) {
                player.endGame(pointMap);
            }

        }
    }

    public List<CardType> generateHand() {
        List<CardType> hand = new ArrayList<>();

        for(int i = 0; i < cardsPerHand; i++) {
            CardType card = deck.takeCard();
            //System.out.println(card);
            hand.add(card);
        }
        return hand;
    }

}

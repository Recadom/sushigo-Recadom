package com.company;

import com.company.deck.CardDeck;
import com.company.deck.CardType;
import com.company.deck.CircularLinkedList;
import com.company.deck.Node;
//import com.company.deck.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameEngine {
    private List<Player> playerList;
    private List<String> allNames = new ArrayList<>(); //todo reduce scope
    private CardDeck deck = new CardDeck();
    private ArrayList<List<CardType>> cardsPlayed;
    private Map<String, Integer> pointMap;
    private int cardsPerHand, amountOfPlayers;

    public GameEngine(List<Player> playerListIn) {
        this.playerList = playerListIn;

        //compile names
        for (int i = 0; i < playerList.size(); i++) { //todo make sure players are aligned correctly
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

        //set up amount of players and size of hands
        amountOfPlayers = playerList.size();
        if (amountOfPlayers > 1 && amountOfPlayers < 6) {
            cardsPerHand = 12 - amountOfPlayers;
        } else {
            System.out.println("Improper amount of players. Defaulting to 4.");
            cardsPerHand = 8;
            amountOfPlayers = 4;
        }

    }

    public void playGame(){
        final int ROUNDS = 3; //TODO change to 3
        deck = new CardDeck();
        deck.shuffle();
        pointMap = new HashMap<>();
        CircularLinkedList playerHands;


        for (int round = 0; round < ROUNDS; round++) {
            cardsPlayed = new ArrayList<>();
            playerHands = new CircularLinkedList();

            //deal the initial hands
            for (int i = 0; i < amountOfPlayers; i++) {
                cardsPlayed.add(new ArrayList<>());
                playerHands.addNode(generateHand());
            }

            //Point the current hand of cards to the first player
            Node playerHand = playerHands.head;


            //repeat until last card has been played
            for(int cardsPlayedCnt = 0; cardsPlayedCnt < cardsPerHand * amountOfPlayers;) {

                List<TurnResult> turnResults = new ArrayList<>();

                //allow each player to take a turn
                for (int playerNum = 0; playerNum < amountOfPlayers; playerNum++) {
                    Player player = playerList.get(playerNum);

                    //send each player their cards  //todo make sure proper values are used
                    player.receiveHand(playerHand.data);

                    //receive the player's cards to be played
                    List<CardType> currentCardPlay = player.giveCardsPlayed(); //TODO add check for ch st & amt of cards

                    //remove cards used from hand
                    playerHand.data.remove(currentCardPlay.get(0));
                    if(currentCardPlay.size() > 1) {
                        playerHand.data.remove(currentCardPlay.get(1));
                    }

                    //add cards to table
                    if (currentCardPlay.size() == 1 || currentCardPlay.size() == 2) {
                        cardsPlayed.get(playerNum).addAll(currentCardPlay);
                    }

                    turnResults.add(new TurnResult(player.getName(),currentCardPlay, cardsPlayed.get(playerNum)));

                    //point the playerHand pointer to the next node; this cycles the player hands
                    playerHand = playerHand.next;

                    //count the cards left on the table
                    cardsPlayedCnt = 0;
                    for(List<CardType> cards : cardsPlayed) {
                        cardsPlayedCnt += cards.size();
                    }
                }

                //send turn data
                for (Player recPlayer : playerList) {
                    recPlayer.receiveTurnResults(turnResults);
                }

                //tally current score todo make sure this is the correct timing for this
                for (Player player : playerList) {
                    pointMap.put(player.getName(), 10);
                }
                playerHand = playerHand.next;

            }

            //send scores to each player
            for (Player player : playerList) {
                player.endRound(pointMap);
            }

        }

        //send final scores
        for (Player player : playerList) {
            player.endGame(pointMap);
        }
    }

    public List<CardType> generateHand() {
        List<CardType> hand = new ArrayList<>();

        for(int i = 0; i < cardsPerHand; i++) {
            CardType card = deck.takeCard();
            hand.add(card);
        }
        return hand;
    }

}

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
    private List<String> allNames = new ArrayList<>();
    private CardDeck deck = new CardDeck();
    private HashMap<String, List<CardType>> cardsOnTable;
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
        final int ROUNDS = 3;
        deck = new CardDeck();
        deck.shuffle();
        pointMap = new HashMap<>();
        CircularLinkedList playerHands;


        for (int round = 0; round < ROUNDS; round++) {
            cardsOnTable = new HashMap<>();
            playerHands = new CircularLinkedList();

            //deal the initial hands  int i = 0; i < amountOfPlayers; i++
            for (String player : allNames) {
                cardsOnTable.put(player, new ArrayList<>());
                playerHands.addNode(generateHand());
            }

            //Point the current hand of cards to the first player
            Node playerHand = playerHands.head;


            //repeat until last card has been played
            for(int cardsOnTableCnt = 0; cardsOnTableCnt < cardsPerHand * amountOfPlayers;) {

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
                        cardsOnTable.get(player.getName()).addAll(currentCardPlay);
                    }

                    turnResults.add(new TurnResult(player.getName(),currentCardPlay, cardsOnTable.get(player.getName())));

                    //point the playerHand pointer to the next node; this cycles the player hands
                    playerHand = playerHand.next;

                    //count the cards left on the table
                    cardsOnTableCnt = 0;
                    for(List<CardType> cards : cardsOnTable.values()) {
                        cardsOnTableCnt += cards.size();
                    }
                }

                //send turn data
                for (Player recPlayer : playerList) {
                    recPlayer.receiveTurnResults(turnResults);
                }

                //todo keep track of puddings
                //todo end game scoring engine, pass pudding + played cards
                //tally current score
                pointMap = Scoring.evaluateBoard(pointMap, cardsOnTable, allNames);

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

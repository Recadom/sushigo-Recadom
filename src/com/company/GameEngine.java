package com.company;

import com.company.deck.CardDeck;
import com.company.deck.CardType;
import com.company.deck.CircularLinkedList;
import com.company.deck.Node;
//import com.company.deck.Node;

import java.util.ArrayList;
import java.util.Arrays;
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
        cardsOnTable = new HashMap<>();
        for (String player : allNames) {
            cardsOnTable.put(player, new ArrayList<>());
        }


        for (int round = 0; round < ROUNDS; round++) {
            //cardsOnTable = new HashMap<>();
            playerHands = new CircularLinkedList();

            //deal the initial hands  int i = 0; i < amountOfPlayers; i++
            for (String player : allNames) {
                playerHands.addNode(generateHand());
            }

            //Point the current hand of cards to the first player
            Node playerHand = playerHands.head;


            //repeat until last card has been played
            for(int cardHandCnt = 32; cardHandCnt > 0;) {

                List<TurnResult> turnResults = new ArrayList<>();

                //allow each player to take a turn
                for (int playerNum = 0; playerNum < amountOfPlayers; playerNum++) {
                    Player player = playerList.get(playerNum);

                    //send each player their cards
                    player.receiveHand(playerHand.data);

                    //receive the player's cards to be played
                    List<CardType> currentCardPlay = player.giveCardsPlayed();

                    if(cardsOnTable.get(player.getName()).contains(CardType.Chopsticks)) {

                        //remove cards used from hand
                        playerHand.data.remove(currentCardPlay.get(0));
                        if(currentCardPlay.size() > 1) {
                            playerHand.data.remove(currentCardPlay.get(1));

                            //remove chopsticks and place into hand for next round
                            playerHand.data.add(CardType.Chopsticks);
                            cardsOnTable.get(player.getName()).remove(CardType.Chopsticks);
                        }
                        //add cards to table
                        if (currentCardPlay.size() == 1 || currentCardPlay.size() == 2) {
                            cardsOnTable.get(player.getName()).addAll(currentCardPlay);
                        }

                    } else {
                        if(currentCardPlay != null) {
                            playerHand.data.remove(currentCardPlay.get(0));
                            cardsOnTable.get(player.getName()).add(currentCardPlay.get(0));
                        }
                    }



                    turnResults.add(new TurnResult(player.getName(),currentCardPlay, cardsOnTable.get(player.getName())));

                    //point the playerHand pointer to the next node; this cycles the player hands
                    //could have used collections.reverse(), but this is cooler
                    playerHand = playerHand.next;

                    //count the cards left on the table
                    cardHandCnt = 0;
                    Node iterator = playerHands.head;
                    for(int i = 0; i < playerNum; i++) {
                        cardHandCnt += iterator.data.size();
                        iterator = iterator.next;
                    }

                }

                //send turn data
                for (Player recPlayer : playerList) {
                    recPlayer.receiveTurnResults(turnResults);
                }


                playerHand = playerHand.next;
            }

            playerHands.head = playerHands.reverseList(playerHands.head);

            //tally current score todo add board points at end of each round
            pointMap = Scoring.evaluateBoard(cardsOnTable, allNames, round);

            //remove all but pudding
            for (List<CardType> playerTable : cardsOnTable.values()) {
                playerTable.retainAll(Arrays.asList(CardType.Pudding));
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

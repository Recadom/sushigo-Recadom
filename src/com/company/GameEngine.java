package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * Class that runs the game
 */
public class GameEngine {
    private List<Player> playerList;
    private List<String> allNames = new ArrayList<>();
    private CardDeck deck = new CardDeck();
    private HashMap<String, List<CardType>> cardsOnTable;
    private Map<String, Integer> pointMap;
    private int cardsPerHand, amountOfPlayers;

    /**
     * Initializes the game values and variables
     * @param playerListIn list of all the players
     */
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

    /**
     * Method to play the game
     */
    public String playGame(){

        //initialize variables
        final int ROUNDS = 3;
        deck = new CardDeck();
        deck.shuffle();
        pointMap = new HashMap<>();
        for(String player : allNames) {
            pointMap.put(player, 0);
        }
        CircularLinkedList playerHands;
        cardsOnTable = new HashMap<>();
        for (String player : allNames) {
            cardsOnTable.put(player, new ArrayList<>());
        }

        //run the game for 3 rounds
        for (int round = 0; round < ROUNDS; round++) {

            playerHands = new CircularLinkedList();

            for (int i = 0; i < amountOfPlayers; i++) {
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

                    player.receiveHand(playerHand.data);
                    List<CardType> currentCardPlay = player.giveCardsPlayed();

                    //check for chopsticks
                    if(cardsOnTable.get(player.getName()).contains(CardType.Chopsticks)) {

                        //remove cards used from hand
                        if(currentCardPlay != null && !currentCardPlay.isEmpty()) {
                            playerHand.data.remove(currentCardPlay.get(0));
                        }
                        if(currentCardPlay != null && currentCardPlay.size() > 1) {
                            playerHand.data.remove(currentCardPlay.get(1));

                            //remove chopsticks and place into hand for next round
                            playerHand.data.add(CardType.Chopsticks);
                            cardsOnTable.get(player.getName()).remove(CardType.Chopsticks);
                        }
                        //add cards to table
                        if (currentCardPlay != null && (currentCardPlay.size() == 1 || currentCardPlay.size() == 2)) {
                            cardsOnTable.get(player.getName()).addAll(currentCardPlay);
                        }

                    } else {
                        if(currentCardPlay != null) {
                            playerHand.data.remove(currentCardPlay.get(0));
                            if(currentCardPlay.size() > 1) {
                                System.out.println(currentCardPlay.toString());
                                System.out.println("---------- " + cardsOnTable.get(player.getName()) + " ---------");
                                System.out.println("END ERROR\n\n\n\n\n");
                            }
                            cardsOnTable.get(player.getName()).add(currentCardPlay.get(0));
                        }
                    }

                    turnResults.add(new TurnResult(player.getName(),currentCardPlay, cardsOnTable.get(player.getName())));

                    //point the playerHand pointer to the next node; this cycles the player hands
                    //could have used collections.reverse() with an arrayList, but this is cooler
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

            //tally current score
            HashMap<String, Integer> roundPointMap = new HashMap<>(Scoring.evaluateBoard(cardsOnTable, allNames, round));
            for(String player : pointMap.keySet()) {
                pointMap.put(player, pointMap.get(player) + roundPointMap.get(player));
            }

            //System.out.println(pointMap + "\n");

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

        //compute winner if tiebreaker
        Map<Integer, List<String>> leaderboard= new TreeMap<>(Collections.reverseOrder());
        List<String> winners;

        //sort winners into a leaderboard
        for (String player : pointMap.keySet()) {
            winners = new ArrayList<>();
            int points = pointMap.get(player);
            winners.add(player);
            if(leaderboard.containsKey(points)) {
                for (String winner : leaderboard.get(points)) {
                    winners.add(winner);
                }
            }
            leaderboard.put(pointMap.get(player), winners);
        }

        //compare only the 1st place tie and check for most amount of pudding cards
        winners = leaderboard.get(((TreeMap<Integer, List<String>>) leaderboard).firstKey());


        int maxCnt = 0;
        String winner = winners.get(0);
        for (String player : winners) {
            int cnt = Collections.frequency(cardsOnTable.get(player), CardType.Pudding);
            if (cnt > maxCnt) {
                maxCnt = cnt;
                winner = player;
            }
    }
        return winner;
    }

    /**
     * Creates a hand for the cards based on a deck
     * @return a list containing a randomized hand
     */
    public List<CardType> generateHand() {
        List<CardType> hand = new ArrayList<>();

        for(int i = 0; i < cardsPerHand; i++) {
            CardType card = deck.takeCard();
            hand.add(card);
        }
        return hand;
    }

    /**
     * Return a map of the points
     * @return map of players to their points
     */
    public Map<String, Integer> getPointMap() {
        return pointMap;
    }
}

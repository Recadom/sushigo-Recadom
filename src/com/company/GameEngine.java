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
    private CircularLinkedList playerHands = new CircularLinkedList();

    private Map<String, Integer> pointMap;
    private int cardsPerHand;

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

        cardsPerHand = 12 - playerList.size(); //TODO add check for correct num of players

    }

    public void playGame(){
        final int ROUNDS = 3; //TODO change to 3
        deck = new CardDeck();
        deck.shuffle();
        int cardsPlayedCnt = 0;
        pointMap = new HashMap<>();
        cardsPlayed = new ArrayList<>();


        for(int i = 0; i < ROUNDS; i++) {

            //deal the initial hands
            for (Player player : playerList) {
                //List<CardType> cards = generateHand();
                //player.receiveHand(generateHand());
                cardsPlayed.add(new ArrayList<CardType>());
                playerHands.addNode(generateHand());
            }

            Node playerHand = playerHands.head;
            //repeat until last card has been played
            while(cardsPlayedCnt < cardsPerHand * playerList.size()) {

                //allow each player to take a turn
                List<TurnResult> turnResults = new ArrayList<>();
                for (int playerNum = 0; playerNum < playerList.size(); playerNum++) {            //TODO make .size() a variable


                    Player player = playerList.get(playerNum);


                    //send each player their cards  //todo make sure proper values are used
                    player.receiveHand(playerHand.data);


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


                    //System.out.println(currentCardPlay);
                    //cardsPlayedCnt += currentCardPlay.size(); //TODO may need to change to Tablehand = 8 for chopsticks

                    //compile turn data
                    //List<CardType> playerTableHand = new ArrayList<>(); //TODO make this larger scope
                    //System.out.println(cardsPlayed.get(0).toString());

                    //TurnResult turn = new TurnResult(player.getName(),currentCardPlay, cardsPlayed.get(playerNum));
                    turnResults.add(new TurnResult(player.getName(),currentCardPlay, cardsPlayed.get(playerNum)));
                    playerHand = playerHand.next;

                    cardsPlayedCnt = 0;
                    for(List<CardType> cards : cardsPlayed) {
                        cardsPlayedCnt += cards.size();
                    }
                }

                //send turn data
                for (Player recPlayer : playerList) {
                    recPlayer.receiveTurnResults(turnResults);
                    //System.out.println(turnResults.toString());
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
            //System.out.println(card);
            hand.add(card);
        }
        return hand;
    }

}

package com.company.players;

import com.company.CardType;
import com.company.Player;
import com.company.TurnResult;

import java.util.*;

public class UberBot implements Player {
    private String playerName;
    private List<String> allPlayerNames;
    private List<CardType> cards;
    List<List<TurnResult>> allTurns = new ArrayList<>();
    List<CardType> currentTable;
    int playerIndex = 0;
    int round = 0;
    List<TurnResult> prevTurn = new ArrayList<>();
    boolean gameOver;
    boolean wonGame;


    public void init(String name, List<String> allNames) {
        playerName = name;
        allPlayerNames = allNames;
    }

    /**
     * Called at the start of every new game.
     */
    public void newGame() {
        gameOver = false;
        wonGame = false;
        String playerName = new String();
        List<String> allPlayerNames = new ArrayList<>();
        List<CardType> cards = new LinkedList<>();
        currentTable = new ArrayList<>();

    }

    /**
     * Called at the start of this player's turn by the engine to give this player their hand of cards for this turn.
     * @param cards List of cards that represent the player's hand for this turn.
     */
    public void receiveHand(List<CardType> cards) {
        this.cards = cards;
        ++round;

    }

    /**
     * Submit the chosen cards to play
     * @return CardType(s) that player wishes to take from the hand. The size of this list should be one or two.
     */
    public List<CardType> giveCardsPlayed() {
        List<CardType> cardsPlayed = new ArrayList<>();
        cardsPlayed.add(cards.get(0));
        return cardsPlayed;
    }


    /**
     * Called at the end of a round. Gives this player a mapping of player names to the total points that each player
     * has at the end of the round.
     * @param pointMap Map of player names to points at the end of the round.
     */
    public void endRound(Map<String, Integer> pointMap) {
        round = 0;
    }

    /**
     * Called at the end of a turn once each player has been given a hand and has decided which card to play. This gives
     * the player the turn results which contain the actions of every player during that turn.
     * @param turnResults List of the turn results containing the actions of each player during that turn.
     */
    public void receiveTurnResults(List<TurnResult> turnResults) {
        allTurns.add(turnResults);

        for (int i = 0; i < turnResults.size(); i++) {
            TurnResult turn = turnResults.get(i);
            if (turn.getPlayerName().equals(playerName)) {
                playerIndex = i;
            }
        }

        prevTurn = turnResults;
        currentTable = new ArrayList<>(turnResults.get(playerIndex).getPlayerTableHand());
    }

    /**
     * Called at the end of every game. Gives the player a mapping of player names to final points that each player has
     * at the end of the game.
     * @param pointMap Map of player names to points at the end of the game.
     */
    public void endGame(Map<String, Integer> pointMap) {
        //compute winner if tiebreaker
        Map<Integer, List<String>> leaderboard = new TreeMap<>(Collections.reverseOrder());
        List<String> winners;
        //sort winners into a leaderboard
        for (String player : pointMap.keySet()) {
            winners = new ArrayList<>();
            int points = pointMap.get(player);
            winners.add(player);
            if (leaderboard.containsKey(points)) {
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
        for (int i = 0; i < winners.size(); i++) {
            String player = winners.get(i);
            int cnt = Collections.frequency(prevTurn.get(i).getPlayerTableHand(), CardType.Pudding);
            if (cnt > maxCnt) {
                maxCnt = cnt;
                winner = player;
            }
        }
        boolean gameOver = true;
        boolean wonGame = winner.equals(playerName);
    }

    /**
     * Returns the name of this player strategy  .
     */
    public String getName() {
        return playerName;
    }
}

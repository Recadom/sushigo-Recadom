package com.company.players;

import com.company.deck.CardType;
import com.company.Player;
import com.company.TurnResult;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomPlayer implements Player {
    private String playerName;
    private List<String> allPlayerNames;
    private List<CardType> cards;


    /**
     * Called once the player object has been instantiated. This gives the player object the names of all the
     * other players in the game. The allNames list should be ordered so that, for example, the player
     * at index 0 should be to the left of index 1, and the player at index 0 is also to the right of index 3.
     * @param name Name assigned to this player. This name will be used in all future references to this player.
     * @param allNames Names of the rest of the players in this game. These names will be used in the future
     *                 to reference players. This also includes the name of this player.
     */
    public void init(String name, List<String> allNames) {
        playerName = name;
        allPlayerNames = allNames;
    }

    /**
     * Called at the start of every new game.
     */
    public void newGame() {
        String playerName = new String();
        List<String> allPlayerNames = new ArrayList<>();
        List<CardType> cards = new LinkedList<>();
    }

    /**
     * Called at the start of this player's turn by the engine to give this player their hand of cards for this turn.
     * @param cards List of cards that represent the player's hand for this turn.
     */
    public void receiveHand(List<CardType> cards) {
        this.cards = cards;
    }

    /**
     * Called after the receiveHand function to request which card(s) the player chooses to play from their hand.
     * If the player has previously played chopsticks and has not used them yet, they are allowed to play two cards in
     * one turn and the engine should place the chopsticks back in the hand after the turn. This hand with the
     * chopsticks is then passed on for the next turn.
     * @return CardType(s) that player wishes to take from the hand. The size of this list should be one or two.
     */
    public List<CardType> giveCardsPlayed() {
        int index = 0;
        if (cards.size() > 1) {
            Random rand = new Random();
            index = rand.nextInt(cards.size());

        }
        List<CardType> cardsPlayed = new ArrayList<>();
        if (cards.size() == 0) {
            return null;
        }
        cardsPlayed.add(cards.get(index));
        return cardsPlayed;
    }

    /**
     * Called at the end of a round. Gives this player a mapping of player names to the total points that each player
     * has at the end of the round.
     * @param pointMap Map of player names to points at the end of the round.
     */
    public void endRound(Map<String, Integer> pointMap) {
    }

    /**
     * Called at the end of a turn once each player has been given a hand and has decided which card to play. This gives
     * the player the turn results which contain the actions of every player during that turn.
     * @param turnResults List of the turn results containing the actions of each player during that turn.
     */
    public void receiveTurnResults(List<TurnResult> turnResults) {
    }

    /**
     * Called at the end of every game. Gives the player a mapping of player names to final points that each player has
     * at the end of the game.
     * @param pointMap Map of player names to points at the end of the game.
     */
    public void endGame(Map<String, Integer> pointMap) {
    }

    /**
     * Returns the name of this player strategy  .
     */
    public String getName() {
        return playerName;
    }

}

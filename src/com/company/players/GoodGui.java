package com.company.players;

import com.company.deck.CardType;
import com.company.GraphicsEngine;
import com.company.Player;
import com.company.TurnResult;

import java.util.*;

public class GoodGui implements Player {
    private String playerName;
    private List<String> allPlayerNames;
    private TreeMap<Integer, CardType> cards;
    List<List<TurnResult>> allTurns = new ArrayList<>();
    List<CardType> currentTable;
    int playerIndex = 0;
    HashMap<CardType, Integer> cardValues = new HashMap<>();
    int round = 0;


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
        currentTable = new ArrayList<>();

        cardValues.put(CardType.Tempura, 25);
        cardValues.put(CardType.Sashimi, 20);
        cardValues.put(CardType.Dumpling, 0);
        cardValues.put(CardType.MakiRollOne, 20);
        cardValues.put(CardType.MakiRollTwo, 30);
        cardValues.put(CardType.MakiRollThree, 40);
        cardValues.put(CardType.EggNigiri, 10);
        cardValues.put(CardType.SalmonNigiri, 15);
        cardValues.put(CardType.SquidNigiri, 20);
        cardValues.put(CardType.Pudding, 10);
        cardValues.put(CardType.Wasabi, 91);
        cardValues.put(CardType.Chopsticks, 80);
    }

    /**
     * Called at the start of this player's turn by the engine to give this player their hand of cards for this turn.
     * @param cards List of cards that represent the player's hand for this turn.
     */
    public void receiveHand(List<CardType> cards) {
        round = (round + 1) % 4;

        if(currentTable.contains(CardType.SquidNigiri)) {
            currentTable.remove(CardType.Wasabi);
            currentTable.remove(CardType.SquidNigiri);
        }
        if(currentTable.contains(CardType.SalmonNigiri)) {
            currentTable.remove(CardType.Wasabi);
            currentTable.remove(CardType.SalmonNigiri);
        }
        if(currentTable.contains(CardType.EggNigiri)) {
            currentTable.remove(CardType.Wasabi);
            currentTable.remove(CardType.EggNigiri);
        }


        cardValues.put(CardType.Wasabi, 91 - round * 10);
        cardValues.put(CardType.Chopsticks, 90 - round * 10);
        cardValues.put(CardType.Sashimi, 20 - round * 2);

        if(currentTable.contains(CardType.Wasabi)) {
            cardValues.put(CardType.EggNigiri, 60);
            cardValues.put(CardType.SalmonNigiri, 70);
            cardValues.put(CardType.SquidNigiri, 90);
        }
        else {
            cardValues.put(CardType.SalmonNigiri, 20);
            cardValues.put(CardType.SquidNigiri, 30);
            cardValues.put(CardType.EggNigiri, 10);
        }

        if(currentTable.contains(CardType.Chopsticks)) {

        }

        if(currentTable.contains(CardType.Tempura)) {
            cardValues.put(CardType.Tempura, 50);
        }
        else {
            cardValues.put(CardType.Tempura, 25);
        }

        if(currentTable.contains(CardType.MakiRollOne) || currentTable.contains(CardType.MakiRollTwo) || currentTable.contains(CardType.MakiRollThree)) {
            cardValues.put(CardType.MakiRollOne, 10);
            cardValues.put(CardType.MakiRollTwo, 15);
            cardValues.put(CardType.MakiRollThree, 20);
        }

        this.cards = new TreeMap<Integer, CardType>();
        for (CardType card : cards) {
            this.cards.put(cardValues.get(card), card);
        }

    }

    /**
     * Called after the receiveHand function to request which card(s) the player chooses to play from their hand.
     * If the player has previously played chopsticks and has not used them yet, they are allowed to play two cards in
     * one turn and the engine should place the chopsticks back in the hand after the turn. This hand with the
     * chopsticks is then passed on for the next turn.
     * @return CardType(s) that player wishes to take from the hand. The size of this list should be one or two.
     */
    public List<CardType> giveCardsPlayed() {
        List<CardType> cardsPlayed = new ArrayList<>();
        //System.out.println(cards.entrySet());

        if (cards.entrySet().size() == 0) {
            return null;
        }

        cardsPlayed.add(cards.lastEntry().getValue());
        cards.remove(cards.lastKey());

        if (currentTable.contains(CardType.Chopsticks) && cards.entrySet().size() > 1) {
            cardsPlayed.add(cards.lastEntry().getValue());
            currentTable.remove(CardType.Chopsticks);
        }



        //System.out.println(cardsPlayed);
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
        //turnResults.add(new TurnResult(player.getName(),currentCardPlay, cardsOnTable.get(player.getName())));
        allTurns.add(turnResults);

        for (int i = 0; i < turnResults.size(); i++) {
            TurnResult turn = turnResults.get(i);
            if (turn.getPlayerName().equals(playerName)) {
                playerIndex = i;
            }
        }

        currentTable = new ArrayList<>(turnResults.get(playerIndex).getPlayerTableHand());
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

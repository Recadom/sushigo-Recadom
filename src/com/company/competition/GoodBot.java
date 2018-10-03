package com.company.competition;

import com.company.Player;
import com.company.TurnResult;
import com.company.CardType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class GoodBot implements Player {
    private String playerName;
    private List<String> allPlayerNames;
    private TreeMap<Integer, CardType> cards;
    List<List<TurnResult>> allTurns = new ArrayList<>();
    List<CardType> currentTable;
    ArrayList<CardType> possibleCards;
    int playerIndex = 0;
    HashMap<CardType, Integer> cardValues = new HashMap<>();
    int round = 0;
    int unusedWasabi = 0;


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
     * Called at the start of every new game. Prepares the variables.
     */
    public void newGame() {
        String playerName = new String();
        List<String> allPlayerNames = new ArrayList<>();
        List<CardType> cards = new LinkedList<>();
        possibleCards = new ArrayList<>();
        currentTable = new ArrayList<>();
        unusedWasabi = 0;

        //initialize a map of cards to their associated value
        cardValues.put(CardType.Tempura, 25);
        cardValues.put(CardType.Sashimi, 20);
        cardValues.put(CardType.Dumpling, 25);
        cardValues.put(CardType.MakiRollOne, 20);
        cardValues.put(CardType.MakiRollTwo, 30);
        cardValues.put(CardType.MakiRollThree, 40);
        cardValues.put(CardType.EggNigiri, 10);
        cardValues.put(CardType.SalmonNigiri, 15);
        cardValues.put(CardType.SquidNigiri, 20);
        cardValues.put(CardType.Pudding, 30);
        cardValues.put(CardType.Wasabi, 91);
        cardValues.put(CardType.Chopsticks, 80);
    }

    /**
     * Called at the start of this player's turn by the engine to give this player their hand of cards for this turn.
     * @param cards List of cards that represent the player's hand for this turn.
     */
    public void receiveHand(List<CardType> cards) {

        cardValues.put(CardType.Wasabi, 91 - round * 10);
        cardValues.put(CardType.Chopsticks, 90 - round * 10);
        cardValues.put(CardType.Sashimi, 20 - round * 2);

        //make wasabi nigiri pairs more likely
        if(unusedWasabi > 0) {
            cardValues.put(CardType.EggNigiri, 60);
            cardValues.put(CardType.SalmonNigiri, 70);
            cardValues.put(CardType.SquidNigiri, 90);
        }
        else {
            cardValues.put(CardType.SalmonNigiri, 20);
            cardValues.put(CardType.SquidNigiri, 30);
            cardValues.put(CardType.EggNigiri, 10);
        }

        if(currentTable == null) {
            return;
        }

        //make tempura pairs more likely
        if(currentTable.contains(CardType.Tempura)) {
            cardValues.put(CardType.Tempura, 50);
        }
        else {
            cardValues.put(CardType.Tempura, 25);
        }

        //reduce the value of maki rolls once some are already on the table
        if(currentTable.contains(CardType.MakiRollOne) || currentTable.contains(CardType.MakiRollTwo) || currentTable.contains(CardType.MakiRollThree)) {
            cardValues.put(CardType.MakiRollOne, 10);
            cardValues.put(CardType.MakiRollTwo, 15);
            cardValues.put(CardType.MakiRollThree, 20);
        }


        //set up a list of possible cards and lower wasabi value if there is no nigiri
        if(round < 4) {
            possibleCards.addAll(cards);
        } else {
            if(containsNigiri(possibleCards) == null) {
                cardValues.put(CardType.Wasabi, 0);
            }
        }

        //sort the values of the current hand into a treemap
        this.cards = new TreeMap<>();
        for (CardType card : cards) {
            this.cards.put(cardValues.get(card), card);
        }

        ++round;

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

        if (cards == null || cards.entrySet().size() == 0) {
            return null;
        }

        // Check for 2-card combinations
        if (currentTable.contains(CardType.Chopsticks) && cards.entrySet().size() > 2) {
            CardType nigiri = containsNigiri(cards.values());

            //if there is a nigiri-wasabi pair, use it
            if((cards.values().contains(CardType.Wasabi)) && nigiri != null) {
                cardsPlayed.add(CardType.Wasabi);
                cardsPlayed.add(nigiri);
                currentTable.remove(CardType.Chopsticks);
            }
            //if there is a tempura pair, use it
            else if(Collections.frequency(cards.values(), CardType.Tempura) / 2 > 0) {
                cardsPlayed.add(CardType.Tempura);
                cardsPlayed.add(CardType.Tempura);
            } else {
                //only play one card if there are no good pairs with chopsticks
                cardsPlayed.add(cards.lastEntry().getValue());
            }
        }
        //play the last two cards to use up chopsticks
        else if (cards.entrySet().size() == 2 && currentTable.contains(CardType.Chopsticks)) {
                cardsPlayed.add(cards.remove(cards.lastEntry().getKey()));
                cardsPlayed.add(cards.lastEntry().getValue());
                currentTable.remove(CardType.Chopsticks);
        }
        //play the highest value card if there are no chopsticks
        else {
            cardsPlayed.add(cards.lastEntry().getValue());
        }

        //calculate how many unused wasabi there are on the table
        if(cardsPlayed.contains(CardType.Wasabi) && containsNigiri(cardsPlayed) == null) {
            ++unusedWasabi;
        } else if(!cardsPlayed.contains(CardType.Wasabi) && containsNigiri(cardsPlayed) != null) {
            unusedWasabi = Math.max(--unusedWasabi, 0);
        }

        return cardsPlayed;
    }

    /**
     * Counts and finds the highest value nigiri in a collection of cards
     * @param cards a collection of cards
     * @return highest value nigiri card, null if none are in the collection
     */
    public CardType containsNigiri(Collection<CardType> cards) {
        if(cards.contains(CardType.SquidNigiri)) {
            return CardType.SquidNigiri;
        }
        if(cards.contains(CardType.SalmonNigiri)) {
            return CardType.SalmonNigiri;
        }
        if(cards.contains(CardType.EggNigiri)) {
            return CardType.EggNigiri;
        }
        return null;
    }


    /**
     * Called at the end of a round. Gives this player a mapping of player names to the total points that each player
     * has at the end of the round.
     * @param pointMap Map of player names to points at the end of the round.
     */
    public void endRound(Map<String, Integer> pointMap) {
        possibleCards = new ArrayList<>();
        currentTable = new ArrayList<>();
        round = 0;
    }

    /**
     * Called at the end of a turn once each player has been given a hand and has decided which card to play. This gives
     * the player the turn results which contain the actions of every player during that turn.
     * @param turnResults List of the turn results containing the actions of each player during that turn.
     */
    public void receiveTurnResults(List<TurnResult> turnResults) {
        allTurns.add(turnResults);

        //determine the index of the player
        for (int i = 0; i < turnResults.size(); i++) {
            TurnResult turn = turnResults.get(i);
            if (turn.getPlayerName().equals(playerName)) {
                playerIndex = i;
            }
        }

        //todo determine if playing against string ending in "Random Player" or other, change strat based on that

        //remove the played cards from the possible card list
        for(TurnResult result : turnResults) {
            if(result.getCardsPlayed() != null) {
                for (CardType card : result.getCardsPlayed()) {
                    if(possibleCards != null)
                        possibleCards.remove(card);
                }
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
        if(playerName == null) {
            return "GoodBot";
        }
        else
            return playerName;
        //return "GoodBot";
    }

    public HashMap<CardType, Integer> getCardValues() {
        return cardValues;
    }
}

package com.company.deck;

import java.util.Collections;
import java.util.Stack;


/**
 * Class that simulates a deck of cards
 */
public class CardDeck {
    private Stack<CardType> cards;

    /**
     * Sets up a deck with the specified amounts of cards
     */
    public CardDeck() {
        this.cards = new Stack<CardType>();

        addCard(CardType.Tempura, 14);
        addCard(CardType.Sashimi, 14);
        addCard(CardType.Dumpling, 14);
        addCard(CardType.MakiRollTwo, 12);
        addCard(CardType.MakiRollThree, 8);
        addCard(CardType.MakiRollOne, 6);
        addCard(CardType.SalmonNigiri, 10);
        addCard(CardType.SquidNigiri, 5);
        addCard(CardType.EggNigiri, 5);
        addCard(CardType.Pudding, 10);
        addCard(CardType.Wasabi, 6);
        addCard(CardType.Chopsticks, 4);
    }

    private void addCard(CardType name, int amt) {
        for (int i = 0; i < amt; i++) {
            this.cards.push(name);
        }
    }

    public void shuffle(){
        Collections.shuffle(this.cards);
    }

    public CardType takeCard() {
        return cards.pop();
    }
}

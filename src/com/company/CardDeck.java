package com.company;

import java.util.ArrayList;
import java.util.Collections;

public class CardDeck {
    private ArrayList<Card> cards;

    public CardDeck() {
        this.cards = new ArrayList<Card>();

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

        shuffle();
    }

    private void addCard(CardType name, int amt) {
        for (int i = 0; i < amt; i++) {
            this.cards.add(new Card(name));
        }
    }

    private void shuffle(){
        Collections.shuffle(this.cards);
    }
}

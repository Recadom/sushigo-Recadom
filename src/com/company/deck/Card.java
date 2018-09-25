package com.company.deck;

public class Card {
    private CardType name;

    public Card(CardType name){
        this.name = name;
    }

    public CardType getName() {
        return name;
    }
}

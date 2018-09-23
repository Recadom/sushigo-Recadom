package com.company;

import com.company.players.GUIPlayer;
import com.company.players.RandomPlayer;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GameEngineTest {
    //GameEngine gameEngine;

    /*@Before
    public void setup() {
        List<Player> playerList = new ArrayList<>();
        playerList.add(new RandomPlayer());
        playerList.add(new RandomPlayer());
        playerList.add(new RandomPlayer());
        playerList.add(new RandomPlayer());
        //playerList.add(new GUIPlayer()); // Having a single GUIPlayer will play the game with a GUI
        GameEngine gameEngine = new GameEngine(playerList);
    }*/

    @Test
    public void generateHand() {
        List<Player> playerList = new ArrayList<>();
        playerList.add(new RandomPlayer());
        playerList.add(new RandomPlayer());
        playerList.add(new RandomPlayer());
        playerList.add(new RandomPlayer());
        //playerList.add(new GUIPlayer()); // Having a single GUIPlayer will play the game with a GUI
        GameEngine gameEngine = new GameEngine(playerList);

        List<CardType> hand = new ArrayList<>();
        CardDeck deck = new CardDeck();
        for(int i = 0; i < 8; i++) {
            CardType card = deck.takeCard();
            hand.add(card);
        }
        assertEquals(hand, gameEngine.generateHand());
    }
}
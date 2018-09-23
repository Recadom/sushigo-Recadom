package com.company;

import java.util.ArrayList;
import java.util.List;

public class GameEngine {
    private List<Player> playerList;
    private List<List<Card>> hands;
    private int playerNum = 0;
    List<String> allNames = new ArrayList<>();
    private CardDeck deck = new CardDeck();

    public GameEngine(List<Player> playerListIn) {
        this.playerList = playerListIn;

        //compile names
        for (int i = 0; i < playerList.size(); i++) {
            allNames.add("Player " + ++playerNum);
        }
        //init players
        for (int i = 0; i < playerNum; i++) {
            playerList.get(i).init(allNames.get(i), allNames);
        }

    }

    public void playGame(int rounds){


        for(int i = 0; i < rounds; i++) {

        }
    }
}

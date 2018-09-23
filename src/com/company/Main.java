package com.company;

import com.company.players.GUIPlayer;
import com.company.players.RandomPlayer;

import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        List<Player> playerList = new ArrayList<>();
        playerList.add(new RandomPlayer());
        playerList.add(new RandomPlayer());
        playerList.add(new RandomPlayer());
        playerList.add(new GUIPlayer()); // Having a single GUIPlayer will play the game with a GUI

        GameEngine gameEngine = new GameEngine(playerList);
        gameEngine.playGame(1);
    }
}

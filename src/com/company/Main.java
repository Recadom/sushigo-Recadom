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
        playerList.add(new GUIPlayer());

        GameEngine gameEngine = new GameEngine(playerList);

        int[] winsPerPlayer = new int[4];

        for(int i = 0; i < 100; i++) {
            gameEngine.playGame();
        }

        System.out.println(winsPerPlayer);
    }
}

package com.company;
import com.company.competition.GoodBot;
import com.company.players.RandomPlayer;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Sushi Go! simulator.
 * Made by Martin Juskelis
 */
public class Main {
    public static void main(String[] args) {
        List<Player> playerList = new ArrayList<>();
        playerList.add(new GoodBot());
        //playerList.add(new UberBot());
        //playerList.add(new RandomPlayer());
        playerList.add(new RandomPlayer());
        playerList.add(new RandomPlayer());
        playerList.add(new RandomPlayer());
        //playerList.add(new GUIPlayer());

        //get map of players initialized
        GameEngine gameEngine = new GameEngine(playerList);
        Map<String, Integer> winsPerPlayer = new HashMap<>();
        for (Player player : playerList) {
            winsPerPlayer.put(player.getName(), 0);
        }

        //run 100 simulations
        final int SIMULATIONS = 10000;

        for(int i = 0; i < SIMULATIONS; i++) {
            String winner = gameEngine.playGame();
            winsPerPlayer.put(winner, winsPerPlayer.get(winner) + 1);
        }

        DecimalFormat df = new DecimalFormat("#.#");
        for(String player : winsPerPlayer.keySet()) {
            double winRate = (double) winsPerPlayer.get(player) / (double) SIMULATIONS * 100.0;
            System.out.println(player + ": " + df.format(winRate));
        }

    }
}



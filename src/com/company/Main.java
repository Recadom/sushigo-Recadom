package com.company;
import com.company.players.GUIPlayer;
import com.company.players.RandomPlayer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        playerList.add(new RandomPlayer());
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
        for(int i = 0; i < 100; i++) {
            gameEngine.playGame();
            Map<String, Integer> pointMap = gameEngine.getPointMap();
            String name = Collections.max(pointMap.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
            winsPerPlayer.put(name, winsPerPlayer.get(name) + 1);
        }

        System.out.println(winsPerPlayer);
    }
}

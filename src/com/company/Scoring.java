package com.company;

import com.company.deck.CardType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;

/**
 * Main class to score the points
 */
public class Scoring {
  private static Map<String, Integer> pointMap = new HashMap<>();
  private static HashMap<String, List<CardType>> cardsOnTable = new HashMap<>();
  private static final int[] DUMPLING_POINTS = {1, 3, 6, 10, 15};
  private static HashMap<CardType, Integer> nigiriPoints = new HashMap<>();

  /**
   * Calls all the functions to score
   * @param cardsOnTableIn the cards that are on the table
   * @param allNames the names of all the players
   * @return a map that contains each player's points
   */
  static Map<String, Integer> evaluateBoard(HashMap<String, List<CardType>> cardsOnTableIn, List<String> allNames, int round) {
    pointMap = new HashMap<>();
    cardsOnTable = cardsOnTableIn;
    nigiriPoints.put(CardType.EggNigiri, 1);
    nigiriPoints.put(CardType.SalmonNigiri, 2);
    nigiriPoints.put(CardType.SquidNigiri, 3);

    for (String player : allNames) {
      if(!pointMap.containsKey(player))
        pointMap.put(player, 0);

        countTempura(player);
        countSashimi(player);
        countDumplings(player);
    }

    countMaki(allNames);

    nigiriWasabi(allNames);

    //count puddings on the last round only (index starts at zero)
    if(round == 2) {
      countPudding(allNames);
    }

    return pointMap;
  }

  /**
   * Counts and scores the maki cards for each player
   * @param allNames
   */
  static void countMaki(List<String> allNames) {
    Map<Integer, List<String>> makiCounts= new TreeMap<>(Collections.reverseOrder());
    for (String player : allNames) {

      //count maki rolls
      int cnt = Collections.frequency(cardsOnTable.get(player), CardType.MakiRollOne);
      cnt += Collections.frequency(cardsOnTable.get(player), CardType.MakiRollTwo) * 2;
      cnt += Collections.frequency(cardsOnTable.get(player), CardType.MakiRollThree) * 3;

      //create treemap with sorted arraylist of players with the most maki rolls
      List<String> players = new ArrayList<String>();
      players.add(player);
      if(makiCounts.containsKey(cnt)) {
        for (String usedPlayer : makiCounts.get(cnt)) {
          players.add(usedPlayer);
        }
      }
      makiCounts.put(cnt, players);
    }

    Set keys = makiCounts.keySet();
    Iterator itr = keys.iterator();

    //award points
    for(int i = 1; i < 3 && itr.hasNext(); i++) {
      List<String> winners = makiCounts.get(itr.next());
      for (String player : winners) {
        pointMap.put(player, pointMap.get(player) + 6 / winners.size() / i);
      }
    }

  }

  /**
   * Adds up the tempura points for a certain player
   * @param player
   */
  static void countTempura(String player) {
    int cnt = Collections.frequency(cardsOnTable.get(player), CardType.Tempura);
    pointMap.put(player, pointMap.get(player) + cnt / 2 * 5);
  }


  /**
   * calculates wasabi and nigiri points for each player
   * @param allNames
   */
 static void nigiriWasabi(List<String> allNames) {
   Stack<CardType> wasabis = new Stack<>();
   for (String player : allNames) {
     int points = 0;
     for(CardType card : cardsOnTable.get(player)) {
        if (card == CardType.Wasabi) {
          wasabis.push(CardType.Wasabi);
        }
        else if (card == CardType.EggNigiri || card == CardType.SalmonNigiri || card == CardType.SquidNigiri) {
          if (wasabis.contains(CardType.Wasabi)) {
            points += nigiriPoints.get(card) * 3;
            wasabis.pop();
          }
          else {
            points += nigiriPoints.get(card);
          }
        }
     }
     pointMap.put(player, pointMap.get(player) + points);
   }
 }


  /**
   * Adds up the sashimi points for each player
   * @param player
   */
  static void countSashimi(String player) {
    int cnt = Collections.frequency(cardsOnTable.get(player), CardType.Sashimi);
    pointMap.put(player, pointMap.get(player) + cnt / 3 * 10);
  }

  static void countDumplings(String player) {
    int cnt = Collections.frequency(cardsOnTable.get(player), CardType.Dumpling);
    if (cnt > 0 && cnt <= 5) {
      pointMap.put(player, pointMap.get(player) + DUMPLING_POINTS[cnt - 1]);
    }
    else if (cnt > 5) {
      pointMap.put(player, pointMap.get(player) + DUMPLING_POINTS[4]);
    }
  }


  /**
   * Calculates the pudding point score for all the players
   * @param allNames
   */
  static void countPudding(List<String> allNames) {
    Map<Integer, List<String>> dumpCounts= new TreeMap<>(Collections.reverseOrder());
    for (String player : allNames) {

      //count dumplings
      int cnt = Collections.frequency(cardsOnTable.get(player), CardType.Pudding);

      //create treemap with sorted arraylist of players with the most maki rolls
      List<String> players = new ArrayList<String>();
      players.add(player);
      if(dumpCounts.containsKey(cnt)) {
        for (String usedPlayer : dumpCounts.get(cnt)) {
          players.add(usedPlayer);
        }
      }
      dumpCounts.put(cnt, players);
    }

    //award points
      List<String> winners = dumpCounts.get(((TreeMap<Integer, List<String>>) dumpCounts).firstKey());
      for (String player : winners) {
        pointMap.put(player, pointMap.get(player) + 6 / winners.size());
      }
      List<String> losers = dumpCounts.get(((TreeMap<Integer, List<String>>) dumpCounts).lastKey());
      for (String player : losers) {
        pointMap.put(player, pointMap.get(player) - 6 / losers.size());
      }


  }

}

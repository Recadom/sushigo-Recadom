package com.company;

import com.company.deck.CardType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class Scoring {
  private static Map<String, Integer> pointMap = new HashMap<>();
  private static HashMap<String, List<CardType>> cardsOnTable = new HashMap<>();
  private static final int[] DUMPLING_POINTS = {1, 3, 6, 10, 15};

  static Map<String, Integer> evaluateBoard(HashMap<String, List<CardType>> cardsOnTableIn, List<String> allNames) {
    pointMap = new HashMap<>();
    cardsOnTable = cardsOnTableIn;

    for (String player : allNames) {
      if(!pointMap.containsKey(player))
        pointMap.put(player, 0);

        countTempura(player);
        countSashimi(player);
        countDumplings(player);
    }

    countMaki(allNames);

    nigiriWasabi();

    countPudding();

    return pointMap;
  }

  static void countMaki(List<String> allNames) {
    Map<Integer, List<String>> makiCounts= new TreeMap<>(Collections.reverseOrder());
    for (String player : allNames) {
      int cnt = Collections.frequency(cardsOnTable.get(player), CardType.MakiRollOne);
      cnt += Collections.frequency(cardsOnTable.get(player), CardType.MakiRollTwo) * 2;
      cnt += Collections.frequency(cardsOnTable.get(player), CardType.MakiRollThree) * 3;

      List<String> players = new ArrayList<String>();
      players.add(player);
      if(makiCounts.containsKey(cnt))
        players.add(player);
      makiCounts.put(cnt, players);
    }
    //Map<Integer, String> reversedMakiCount = new TreeMap(Collections.reverseOrder());

    for (Map.Entry<Integer, List<String>> entry : makiCounts.entrySet()) {
      System.out.println(entry.getKey() + " > " + entry.getValue());
    }

  }
  /*Each player adds up the maki roll icons at the
top of all their maki roll cards. The player with
the most icons scores 6 points. If multiple players tie for the most, they split the 6 points
evenly (ignoring any remainder) and no second place points are awarded
The player with the second most icons scores 3 points. If multiple players tie for second
place, they split the points evenly (ignoring any remainder).*/

  static void countTempura(String player) {
    int cnt = Collections.frequency(cardsOnTable.get(player), CardType.Tempura);
    pointMap.put(player, pointMap.get(player) + cnt / 2 * 5);
  }
  /* A set of 2 tempura cards scores
5 points. A single tempura card is worth nothing. You
may score multiple sets of tempura in a round
*/

 static void nigiriWasabi() {

 }

  /*  A squid nigiri scores
3 points. If it is on top of a wasabi card it scores
9 points

A salmon nigiri scores 2 points.If it is on top of a wasabi card it scores 6 points.

An egg nigiri scores 1 point. If it is on top of a wasabi card it scores 3 points.
A wasabi card with no nigiri on it scores nothing

*/



  static void countSashimi(String player) {
    int cnt = Collections.frequency(cardsOnTable.get(player), CardType.Sashimi);
    pointMap.put(player, pointMap.get(player) + cnt / 3 * 10);
  }
  /*A set of 3 sashimi cards scores
10 points. A single sashimi card or a set of only 2 is worth
nothing. You may score multiple sets of sashimi
in a round, although this is very hard*/

  static void countDumplings(String player) {
    int cnt = Collections.frequency(cardsOnTable.get(player), CardType.Dumpling);
    if (cnt > 0 && cnt <= 5) {
      pointMap.put(player, pointMap.get(player) + DUMPLING_POINTS[cnt]);
    }
    else if (cnt > 5) {
      pointMap.put(player, pointMap.get(player) + DUMPLING_POINTS[5]);
    }
  }
  /*The more dumpling cards you have, the
more points you will score, as follows:
dumplings:   1  2  3  4  5+
Points       1  3  6  10 15
*/

  static void countPudding() {

  }
  /*The player with the most pudding cards
scores
6 points. If multiple players tie for
the most, they split the points evenly
(ignoring any remainder).

The player with the fewest pudding cards
(including players with none)
loses 6 points. If multiple players tie for the least, they split the
lost points evenly (ignoring any remainder).
*/


}

package com.company;

import com.company.deck.CardType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scoring {
  private static HashMap<String, Integer> puddingCnt = new HashMap<>();

  static Map<String, Integer> evaluateBoard(Map<String, Integer> pointMap, HashMap<String, List<CardType>> cardsOnTable, List<String> allNames) {
    for (String player : allNames) {
        pointMap.put(player, 10);
    }
    return pointMap;
  }

  //static void countMaki();
  /*Each player adds up the maki roll icons at the
top of all their maki roll cards. The player with
the most icons scores 6 points. If multiple players tie for the most, they split the 6 points
evenly (ignoring any remainder) and no second place points are awarded
The player with the second most icons scores 3 points. If multiple players tie for second
place, they split the points evenly (ignoring any remainder).*/

  //static void countTempura();
  /* A set of 2 tempura cards scores
5 points. A single tempura card is worth nothing. You
may score multiple sets of tempura in a round
*/

 //static void nigiriWasabi();

  /*  A squid nigiri scores
3 points. If it is on top of a wasabi card it scores
9 points

A salmon nigiri scores 2 points.If it is on top of a wasabi card it scores 6 points.

An egg nigiri scores 1 point. If it is on top of a wasabi card it scores 3 points.
A wasabi card with no nigiri on it scores nothing

*/



  //static void countSashimi();
  /*A set of 3 sashimi cards scores
10 points. A single sashimi card or a set of only 2 is worth
nothing. You may score multiple sets of sashimi
in a round, although this is very hard*/

  //static void countDumplings();
  /*The more dumpling cards you have, the
more points you will score, as follows:
dumplings:   1  2  3  4  5+
Points       1  3  6  10 15
*/

  //static void countPudding();
  /*The player with the most pudding cards
scores
6 points
. If multiple players tie for
the most, they split the points evenly
(ignoring any remainder).

The player with the fewest pudding cards
(including players with none)
loses 6 points. If multiple players tie for the least, they split the
lost points evenly (ignoring any remainder).
*/


}

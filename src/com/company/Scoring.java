package com.company;

import com.company.deck.CardType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scoring {
  static Map<String, Integer> evaluateBoard(Map<String, Integer> pointMap, HashMap<String, List<CardType>> cardsOnTable, List<String> allNames) {
    for (String player : allNames) {
        pointMap.put(player, 10);
    }
    return pointMap;
  }

}

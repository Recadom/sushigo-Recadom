package com.company;

import static org.junit.Assert.*;

import com.company.deck.CardDeck;
import com.company.deck.CardType;
import com.company.players.RandomPlayer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class ScoringTest {
  List<Player> playerList;
  private List<String> allNames;
  private CardDeck deck;
  private HashMap<String, List<CardType>> cardsOnTable;
  private Map<String, Integer> pointMap;
  private int cardsPerHand, amountOfPlayers;
  GameEngine gameEngine;

  @Before
  public void setUp() {
    deck = new CardDeck();
    allNames = new ArrayList<>();
    pointMap = new HashMap<>();
    playerList = new ArrayList<>();
    cardsOnTable = new HashMap<>();
    playerList.add(new RandomPlayer());
    playerList.add(new RandomPlayer());
    playerList.add(new RandomPlayer());
    playerList.add(new RandomPlayer());
    gameEngine = new GameEngine(playerList);

      for (Player player : playerList) {
        allNames.add(player.getName());
        pointMap.put(player.getName(), 1);
        //cardsOnTable.put(player.getName(), Arrays.asList(CardType.Wasabi));
      }


  }

  @Test
  public void evaluateBoardBasicGame() {
    cardsOnTable = new HashMap<>();
    for (Player player : playerList) {
      cardsOnTable.put(player.getName(), Arrays.asList(CardType.Wasabi));
    }
    assertEquals(pointMap, Scoring.evaluateBoard(cardsOnTable, allNames, 1));
  }

  @Test
  public void tempuraScoringTest() {
    cardsOnTable = new HashMap<>();
    for (Player player : playerList) {
      cardsOnTable.put(player.getName(), Arrays.asList(CardType.Tempura));
    }
    assertEquals(pointMap, Scoring.evaluateBoard(cardsOnTable, allNames, 1));
  }

  @Test
  public void MakiScoringTest() {
    cardsOnTable = new HashMap<>();
    for (Player player : playerList) {
      cardsOnTable.put(player.getName(), Arrays.asList(CardType.MakiRollOne));
    }
    assertEquals(pointMap, Scoring.evaluateBoard(cardsOnTable, allNames, 1));
  }

  @Test
  public void WasabiScoringTest() {
    cardsOnTable = new HashMap<>();
    pointMap.put("Player 4", 2);
    pointMap.put("Player 3", 2);
    pointMap.put("Player 2", 2);
    pointMap.put("Player 1", 2);
    for (Player player : playerList) {
      cardsOnTable.put(player.getName(), Arrays.asList(CardType.MakiRollOne));
      cardsOnTable.put(player.getName(), Arrays.asList(CardType.EggNigiri));
    }
    assertEquals(pointMap, Scoring.evaluateBoard(cardsOnTable, allNames, 1));
  }

  @Test
  public void PuddingScoringTest() {
    cardsOnTable = new HashMap<>();
    for (Player player : playerList) {
      cardsOnTable.put(player.getName(), Arrays.asList(CardType.Pudding));
    }
    assertEquals(pointMap, Scoring.evaluateBoard(cardsOnTable, allNames, 1));
  }

  @Test
  public void DumplingScoringTest() {
    cardsOnTable = new HashMap<>();
    for (Player player : playerList) {
      cardsOnTable.put(player.getName(), Arrays.asList(CardType.Pudding));
    }
    assertEquals(pointMap, Scoring.evaluateBoard(cardsOnTable, allNames, 1));
  }

  @Test
  public void countMaki() {
    Scoring.countMaki(allNames);
  }

  @Test
  public void countTempura() {
    Scoring.countTempura("Player 1");
  }

  @Test
  public void nigiriWasabi() {
    Scoring.nigiriWasabi(allNames);
  }

  @Test
  public void countDumplings() {
    Scoring.countDumplings("Player 1");
  }

  @Test
  public void countPudding() {
    Scoring.countPudding(allNames);
  }
}
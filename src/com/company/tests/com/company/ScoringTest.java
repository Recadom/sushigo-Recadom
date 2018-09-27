package com.company;

import static org.junit.Assert.*;

import com.company.players.RandomPlayer;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ScoringTest {
  List<Player> playerList = new ArrayList<>();

  @Before
  public void setUp() throws Exception {
    playerList.add(new RandomPlayer());
    playerList.add(new RandomPlayer());
    playerList.add(new RandomPlayer());
    playerList.add(new RandomPlayer());
  }

  @Test
  public void evaluateBoard() {
    //private List<String> allNames = new ArrayList<>();
    //assert(null, evaluateBoard();
  }

  @Test
  public void countMaki() {
  }

  @Test
  public void countTempura() {
  }

  @Test
  public void nigiriWasabi() {
  }

  @Test
  public void countSashimi() {
  }

  @Test
  public void countDumplings() {
  }

  @Test
  public void countPudding() {
  }
}
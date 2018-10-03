package com.company.competition;

import static org.junit.Assert.*;

import com.company.CardType;
import com.company.Player;
import com.company.TurnResult;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.smartcardio.Card;
import org.junit.Before;
import org.junit.Test;

public class GoodBotTest {
  public GoodBot testPlayer = new GoodBot();

  @Before
  public void setup() {
    GoodBot testPlayer = new GoodBot();
    testPlayer.init("GoodBot", Arrays.asList(testPlayer.getName()));
    testPlayer.newGame();
    testPlayer.receiveHand(Arrays.asList(CardType.Tempura));
  }




  @Test
  public void testCorrectValue() {
    List<CardType> cards = new ArrayList<>();
    cards.add(CardType.Chopsticks);
    cards.add(CardType.Chopsticks);
    testPlayer.receiveHand(cards);
    testPlayer.receiveTurnResults(Arrays.asList(new TurnResult(testPlayer.getName(), Arrays.asList(CardType.Chopsticks), cards)));
    assertEquals(90, testPlayer.getCardValues().get(CardType.Chopsticks).intValue());
  }

  @Test
  public void giveCardsPlayed() {
    List<CardType> cards = new ArrayList<>();
    testPlayer.newGame();
    cards.add(CardType.Chopsticks);
    testPlayer.receiveHand(cards);
    testPlayer.receiveTurnResults(Arrays.asList(new TurnResult(testPlayer.getName(), Arrays.asList(CardType.Chopsticks), cards)));
    assertEquals(CardType.Chopsticks, testPlayer.giveCardsPlayed().get(0));

  }

  @Test
  public void containsNigiri() {
    List<CardType> cards = new ArrayList<>();
    cards.add(CardType.EggNigiri);
    assertEquals(CardType.EggNigiri, testPlayer.containsNigiri(cards));
  }

  @Test
  public void containsNigiriNull() {
    List<CardType> cards = new ArrayList<>();
    cards.add(CardType.Tempura);
    assertEquals(null, testPlayer.containsNigiri(cards));
  }


  @Test
  public void getName() {
    assertEquals("GoodBot", testPlayer.getName());
  }
}
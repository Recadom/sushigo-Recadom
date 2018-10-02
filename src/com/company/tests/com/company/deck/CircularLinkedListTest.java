package com.company.deck;

import static org.junit.Assert.*;

import com.company.CardType;
import com.company.CircularLinkedList;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class CircularLinkedListTest {
  CircularLinkedList test = new CircularLinkedList();
  List<CardType> list = new ArrayList<>();

  @Before
  public void setUp() {
    list.add(CardType.MakiRollOne);

    test.addNode(list);
    test.addNode(list);
    test.addNode(list);
  }

  @Test
  public void addNodes() {
        //make sure it really is circular
    assertEquals(list, test.head.next.next.next.next.data = list);

  }

  @Test
  public void reverseList() {
    test.reverseList(test.head);
    assertEquals(list, test.tail.data);
  }
}
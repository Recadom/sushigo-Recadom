package com.company.deck;

import java.util.List;

public class Node {
  public List<CardType> data;
  public Node next;
  public Node(List<CardType> data){
    this.data = data;
  }
}

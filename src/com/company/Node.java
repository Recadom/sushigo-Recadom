package com.company;

import java.util.List;

public class Node {
  public List<CardType> data;
  public Node next;
  //public Node prev;
  public Node(List<CardType> data){
    this.data = data;
  }
}

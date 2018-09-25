package com.company;

import java.util.List;

public class CircularLinkedList {

  public int size = 0;
  public Node head = null;
  public Node tail = null;

  public void addNode(List<CardType> data){
    Node n = new Node(data);
    if (size == 0){
      head = n;
      tail = n;
      n.next = head;
      size++;
    } else {
      tail.next = n;
      tail = n;
      tail.next = head;
      size++;
    }
  }
}

class Node {
  List<CardType> data;
  Node next;
  public Node(List<CardType> data){
    this.data = data;
  }
}

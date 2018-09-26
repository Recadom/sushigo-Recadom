package com.company.deck;

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

      //n.prev = n;
      n.next = head;
      size++;
    } else {
      tail.next = n;
      //n.prev = tail;
      tail = n;
      tail.next = head;
      size++;
    }
  }

  public Node reverseList(Node head) {
    if (head == null || head.next == null || head.next == head)
      return head;

    Node p1 = head;
    Node p2 = p1.next;

    head.next = null;
    while (p2 != head) {
      Node t = p2.next;
      p2.next = p1;
      p1 = p2;
      p2 = t;
    }

    return p1;
  }
}


package model;

public class CharList<T> {
  private Node<T> head;
  private Node<T> tail;
  private Node<T> curr;
  private int size;

  private static class Node<T> {
    T data;
    Node<T> next;
    Node<T> prev;

    public Node(T data) {
      this.data = data;
      this.next = null;
      this.prev = null;
    }
  }

  public CharList() {
    this.head = null;
    this.tail = null;
    this.curr = null;
    this.size = 0;
  }

  public void add(T data) {
    Node<T> newNode = new Node<>(data);
    if (head == null) {
      head = tail = curr = newNode;
    } else {
      tail.next = newNode;
      newNode.prev = tail;
      tail = newNode;
    }
    size++;
  }

  public T get(int index) {
    if(index < 0 || index >= size) {
      throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }
    Node<T> curr = head;
    for (int i = 0; i < index; i++) {
      curr = curr.next;
    }
    return curr.data;
  }

  public int indexOf(T data) {
    Node<T> curr = head;
    for (int i = 0; i < size; i++) {
      if (curr.data.equals(data)) {
        return i;
      }
      curr = curr.next;
    }
    return -1;
  }

  public int size() {
    return size;
  }

  public void next() {
    if (curr != null && curr.next != null) {
      curr = curr.next;
    }
  }

  public void prev() {
    if(curr != null && curr.prev != null) {
      curr.next = curr;
      curr = curr.prev;
    }
  }

  public T curr() {
   try {
      return curr.data;
    } catch (NullPointerException e) {
      throw new IllegalStateException("Current node is null");
    }
  }
}

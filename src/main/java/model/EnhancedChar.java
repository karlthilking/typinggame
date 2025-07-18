package model;

import java.awt.*;

public class EnhancedChar<T> {
  private char character;
  private Color color;
  private int position;

  public EnhancedChar(char character, Color color, int position) {
    this.character = character;
    this.color = color;
    this.position = position;
  }

  public char getCharacter() {
    return character;
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color c) {
    this.color = c;
  }

  public int getPosition() {
    return position;
  }
}
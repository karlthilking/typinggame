package model;

import java.awt.*;

public class EnhancedChar<T> {
  private char character;
  private Color color;

  public EnhancedChar(char character, Color color) {
    this.character = character;
    this.color = color;
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
}
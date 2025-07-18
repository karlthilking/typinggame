package controller;

import java.awt.*;

import model.BodyImpl;
import model.EnhancedChar;
import view.GUI;

public class Controller {
  private final BodyImpl body;
  private final GUI view;

  public Controller(BodyImpl body, GUI view) {
    this.body = body;
    this.view = view;
  }

  public void handleTypedChar(char c) {
    EnhancedChar temp = new EnhancedChar(c, Color.BLACK);
    if (body.getChars().curr().getCharacter() == c) {
      body.getChars().curr().setColor(Color.GREEN);
      body.getChars().next();
    } else {
      body.getChars().curr().setColor(Color.RED);
      body.getChars().next();
    }

  }

  public void handleBackspace() {
    body.getChars().prev();
  }

  public void endGame() {
  }

}

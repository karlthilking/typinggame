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
    System.out.println("Handling char: " + c);

    EnhancedChar curr = body.getChars().curr();

    if(curr == null) {
      System.out.println("End of text reached");
      return;
    }
    if (curr.getCharacter() == c) {
      curr.setColor(Color.GREEN);
      body.getChars().next();
    } else {
      curr.setColor(Color.RED);
      body.getChars().next();
    }

    EnhancedChar updated = body.getChars().curr();

    if(updated != null) {
      view.updateDisplay(updated.getPosition());
    }
    else {
      System.out.println("End of text reached");
    }
  }

  public void handleBackspace() {
    body.getChars().prev();

    view.updateDisplay(body.getChars().curr().getPosition());
  }

  public void endGame() {
  }

}

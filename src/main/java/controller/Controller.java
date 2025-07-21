package controller;

import java.awt.*;
import java.awt.event.KeyEvent;

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
      curr.setColor(new Color(0, 180, 0));
      body.correctTyped();
      body.getChars().next();
    } else {
      curr.setColor(Color.RED);
      body.incorrectTyped();
      body.getChars().next();
    }

    EnhancedChar updated = body.getChars().curr();

    if(updated != null ) {
      view.updateDisplay(updated.getPosition());
    }
    else {
      System.out.println("End of text reached");
    }
  }

  public void handleBackspace() {
    EnhancedChar curr = body.getChars().curr();

    if(curr == null) {
      System.out.println("No character to delete");
      return;
    }

    int oldPosition = curr.getPosition();
    body.getChars().prev();

    EnhancedChar updated = body.getChars().curr();
    if(updated == null) {
      body.getChars().next();
      return;
    }

    EnhancedChar charToReset = body.getChars().get(oldPosition);
    if(charToReset.getColor() == (new Color(0, 180, 0))) {
      body.deletedCorrect();
    }
    charToReset.setColor(Color.BLACK);

    view.updateDisplay(updated.getPosition());
  }

  public void endGame() {
    double wpm = body.getWPM();
    double acc = body.getAccuracy();
    view.endOfGameDisplay(wpm, acc);
  }

}

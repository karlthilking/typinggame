package controller;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import model.BodyImpl;
import model.EnhancedChar;
import view.GUI;

import static java.nio.file.Files.readAllLines;

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

  public void endGame(String timeMode) {
    double mins = 0;
    switch (timeMode) {
      case "FIFTEEN_SECOND":
        mins = 0.25;
        break;
      case "THIRTY_SECOND":
        mins = 0.5;
        break;
      case "ONE_MINUTE":
        mins = 1;
        break;
      case "TWO_MINUTE":
        mins = 2;
        break;
    }
    String wpm = body.getWPM(mins);
    String acc = body.getAccuracy();
    view.endOfGameDisplay(wpm, acc);
  }

  public void addResults(String wpm, String timeMode) {
    String file = "";
    switch (timeMode) {
      case "FIFTEEN_SECOND":
        file = "15results.txt";
        break;
      case "THIRTY_SECOND":
        file = "30results.txt";
        break;
      case "ONE_MINUTE":
        file = "60results.txt";
        break;
      case "TWO_MINUTE":
        file = "120results.txt";
        break;
    }
    try {
      String wpmEntry = wpm + "\n";
      Files.write(Paths.get(file),
              wpmEntry.getBytes(),
              StandardOpenOption.CREATE,
              StandardOpenOption.APPEND);
    } catch (IOException e) {
      System.out.println("Error saving results: " + e.getMessage());
    }
  }

  public List<String> getTopResults() {
    try {
      List<String> fifteen = Files.readAllLines(Paths.get("15results.txt"));
      List<String> thirty = Files.readAllLines(Paths.get("30results.txt"));
      List<String> one = Files.readAllLines(Paths.get("60results.txt"));
      List<String> two = Files.readAllLines(Paths.get("120results.txt"));
      return body.sortResults(fifteen, thirty, one, two);
    } catch (IOException e) {
      System.out.println("Error reading results: " + e.getMessage());
      return List.of("0.0", "0.0", "0.0");
    }
  }

}

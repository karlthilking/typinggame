package controller;

import model.BodyImpl;
import view.GUI;

public class Controller {
  private final BodyImpl body;
  private final GUI view;

  public Controller(BodyImpl body, GUI view) {
    this.body = body;
    this.view = view;
  }

  public void go() {
    body.generateParagraph();
  }

  public void handleTypedChar(char c) {
    if(body.compareChar(c) == true) {
      view.
    }
    else {

    }
    body.pos += 1;
  }

  public void handleBackspace() {
    body.pos -= 1;
  }

}

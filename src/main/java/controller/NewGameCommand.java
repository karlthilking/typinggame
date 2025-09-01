package controller;

import model.Body;
import model.BodyImpl;
import view.GUI;

public class NewGameCommand implements Command {
    @Override
    public void execute(Body body, GUI gui) {
        gui = new GUI();
    }
}

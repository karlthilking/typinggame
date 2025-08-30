package controller;

import model.Body;
import model.BodyImpl;
import view.GUI;

public class RestartCommand implements Command {
    @Override
    public void execute(Body body, GUI gui) {
        body = new BodyImpl();
        gui.initialize();
        gui.build();
    }
}

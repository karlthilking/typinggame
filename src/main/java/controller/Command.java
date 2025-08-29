package controller;

import model.Body;
import view.GUI;

public interface Command {
    void execute(Body body, GUI gui);
}

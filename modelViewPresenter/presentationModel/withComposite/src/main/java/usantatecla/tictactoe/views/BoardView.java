package usantatecla.tictactoe.views;

import usantatecla.tictactoe.controllers.Controller;
import usantatecla.tictactoe.models.Coordinate;
import usantatecla.utils.Console;

public class BoardView {

    private Controller controller;

    BoardView(Controller controller) {
        this.controller = controller;
    }

    public void write() {
        Console.getInstance().writeln(Message.SEPARATOR.getMessage());
        for (int i = 0; i < Coordinate.DIMENSION; i++) {
            Console.getInstance().write(Message.VERTICAL_LINE_LEFT.getMessage());
            for (int j = 0; j < Coordinate.DIMENSION; j++) {
                new TokenView(this.controller.getToken(new Coordinate(i, j))).write();
                Console.getInstance().write(Message.VERTICAL_LINE_CENTERED.getMessage());
            }
            Console.getInstance().writeln(Message.VERTICAL_LINE_RIGHT.getMessage());
        }
        Console.getInstance().writeln(Message.SEPARATOR.getMessage());
    }

}

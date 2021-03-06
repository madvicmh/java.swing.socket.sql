package usantatecla.tictactoe;

import usantatecla.tictactoe.controllers.Logic;
import usantatecla.tictactoe.models.Game;
import usantatecla.tictactoe.views.View;

abstract class TicTacToe {

    private View view;
    private Logic logic;

    protected TicTacToe() {
        this.logic = new Logic(new Game());
        this.view = this.createView();
    }

    protected abstract View createView();

    protected void play() {
        this.view.interact(this.logic);
    }

}

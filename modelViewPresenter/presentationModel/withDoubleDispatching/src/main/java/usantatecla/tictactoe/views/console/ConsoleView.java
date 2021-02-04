package usantatecla.tictactoe.views.console;

import usantatecla.tictactoe.controllers.PlayController;
import usantatecla.tictactoe.controllers.ResumeController;
import usantatecla.tictactoe.controllers.StartController;
import usantatecla.tictactoe.views.View;

public class ConsoleView extends View {

    private StartView startView;
    private PlayView playView;
    private ResumeView resumeView;

    public ConsoleView() {
        this.startView = new StartView();
        this.playView = new PlayView();
        this.resumeView = new ResumeView();
    }

    public void visitStartController(StartController startController) {
        this.startView.interact(startController);
    }

    public void visitPlayController(PlayController playController) {
        this.playView.interact(playController);
    }

    public boolean visitResumeController(ResumeController resumeController) {
        return this.resumeView.interact(resumeController);
    }

}

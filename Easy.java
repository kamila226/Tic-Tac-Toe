package tictactoe;

public class Easy extends Medium {
    protected void playAI_User() {
        prepareGame();
        displayBoard();
        while(checkState() == GameState.STARTED) {
            makeUserMove();
            displayBoard();
            if (checkState() != GameState.STARTED) break;
            makeEasyMove();
            displayBoard();
        }
        printState();
    }
    protected void playAI_AI() {
        prepareGame();
        while(checkState() == GameState.STARTED) {
            makeEasyMove();
            displayBoard();
        }
        printState();
    }

    private void makeEasyMove() {
        System.out.println("Making move level \"easy\"");
        makeRandomMove();
    }
}

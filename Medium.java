package tictactoe;
import java.util.Random;
public class Medium extends Game {
    protected void playAI_User() {
        prepareGame();
        displayBoard();
        while(checkState() == GameState.STARTED) {
            makeUserMove();
            displayBoard();
            if (checkState() != GameState.STARTED) break;
            makeMediumMove();
            displayBoard();
        }
        printState();
    }

    protected void playAI_AI() {
        prepareGame();
        while(checkState() == GameState.STARTED) {
            makeMediumMove();
            displayBoard();
        }
        printState();
    }

    private void makeMediumMove() {
        System.out.println("Making move level \"medium\"");
        if (checkPossibility('O')) return;
        if (checkPossibility('X')) return;
        makeRandomMove();
    }

    protected void makeRandomMove () {
        Random rand = new Random();
        int row = rand.nextInt(3);
        int col = rand.nextInt(3);
        while (!isEmpty(row, col)) {
            row = rand.nextInt(3);
            col = rand.nextInt(3);
        }
        makeMove(row, col);
    }
    private boolean checkPossibility(char moveChar) {
        int charSum = 0;
        if (moveChar == 'X') charSum = 208;
        else if (moveChar == 'O') charSum = 190;
        for (int i = 0; i < 3; i++) {
            //horizontal
            if (board[i][0] + board[i][1] + board[i][2] == charSum) {
                for (int j = 0; j < 3; j++)
                    if (isEmpty(i, j)) {
                        makeMove(i, j);
                        return true;
                    }
            }
            //vertical
            if (board[0][i] + board[1][i] + board[2][i] == charSum) {
                for (int j = 0; j < 3; j++)
                    if (isEmpty(j, i)) {
                        makeMove(j, i);
                        return true;
                    }
            }
        }
        //diagonals
        if (board[0][0] + board[1][1] + board[2][2] == charSum) {
            for (int i = 0; i < 3; i++) {
                if (isEmpty(i, i)) {
                    makeMove(i, i);
                    return true;
                }
            }
        } else if (board[0][2] + board[1][1] + board[2][0] == charSum) {
            for (int i = 0; i < 3; i++) {
                if (isEmpty(i, 2 - i)) {
                    makeMove(i, 2-i);
                    return true;
                }
            }
        }
        return false;
    }
}
